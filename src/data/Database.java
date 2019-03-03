package data;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Data
@NoArgsConstructor
public class Database {

    private Connection connection;
    private static final String URL = "jdbc:sqlite:db/fms.sqlite";
    private static final String DRIVER = "org.sqlite.JDBC";

    // set up database driver
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // open connection to the fms.sqlite database
    public void connect() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(URL);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("unable to open connection to database. " + e.getMessage());
        }
    }

    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                connection.commit();
            } else {
                connection.rollback();
            }
            connection.close();
            connection = null;
        } catch (SQLException e) {
            throw new DatabaseException("unable to close database connection. " + e.getMessage());
        } catch (NullPointerException e) {
            throw new DatabaseException("tried to close a null connection. " + e.getMessage());
        }
    }

    public void clearAll() throws DatabaseException {
        connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "delete from user";
            statement.execute(sql);
            sql = "delete from people";
            statement.execute(sql);
            sql = "delete from event";
            statement.execute(sql);
            sql = "delete from auth_token";
            statement.execute(sql);
            closeConnection(true);
        } catch (SQLException e) {
            closeConnection(false);
            throw new DatabaseException("sql error encountered while clearing tables. " + e.getMessage());
        }
    }

    private void initAll() throws DatabaseException {
        connect();
        try {
            initUsers();
            initPeople();
            initEvents();
            initAuthTokens();
            closeConnection(true);
        } catch (DatabaseException e) {
            closeConnection(false);
            throw new DatabaseException("init tables failed. " + e.getMessage());
        }
    }

    private void initUsers() throws DatabaseException {
        try {
            Statement statement = connection.createStatement();
            String sql = "create table if not exists user " +
                    "(" +
                    "username   varchar(255)  not null primary key, " +
                    "password   varchar(255)  not null, " +
                    "email      varchar(255)  not null, " +
                    "firstname  varchar(255)  not null, " +
                    "lastname   varchar(255)  not null, " +
                    "gender     varchar(255)  not null, " +
                    "person_id  varchar(255)  not null  " +
                    ");";
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DatabaseException("sql error encountered while creating user table");
        }
    }

    private void initPeople() throws DatabaseException {
        try {
            Statement statement = connection.createStatement();
            String sql = "create table if not exists people " +
                    "(" +
                    "person_id  varchar(255)  not null primary key, " +
                    "descendant varchar(255), " +
                    "firstname  varchar(255)  not null, " +
                    "lastname   varchar(255)  not null, " +
                    "gender     varchar(255)  not null, " +
                    "father_id  varchar(255), " +
                    "mother_id  varchar(255), " +
                    "spouse_id  varchar(255) " +
                    ");";
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DatabaseException("sql error encountered while creating people table");
        }
    }

    private void initEvents() throws DatabaseException {
        try {
            Statement statement = connection.createStatement();
            String sql = "create table if not exists event " +
                    "(" +
                    "event_id   varchar(255)  not null primary key, " +
                    "descendant varchar(255), " +
                    "person_id  varchar(255)  not null, " +
                    "latitude   varchar(255)  not null, " +
                    "longitude  varchar(255)  not null, " +
                    "country    varchar(255)  not null, " +
                    "city       varchar(255)  not null, " +
                    "event_type varchar(255)  not null, " +
                    "year       integer       not null  " +
                    ");";
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DatabaseException("sql error encountered while creating event table");
        }
    }

    private void initAuthTokens() throws DatabaseException {
        try {
            Statement statement = connection.createStatement();
            String sql = "create table if not exists auth_token " +
                    "(" +
                    "token      varchar(255)  not null primary key, " +
                    "username   varchar(255)  not null  " +
                    ");";
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DatabaseException("sql error encountered while creating authToken table");
        }
    }

    public void clearAuthTokens() throws DatabaseException {
        connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "delete from auth_token";
            statement.execute(sql);
            closeConnection(true);
        } catch (SQLException e) {
            closeConnection(false);
            throw new DatabaseException("sql error encountered while clearing authtokens. " + e.getMessage());
        }
    }

}
