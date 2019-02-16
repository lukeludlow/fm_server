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

    /**
     * open connection to the fms.sqlite database
     * @return new sql connection
     */
    public Connection connect() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(URL);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("unable to open connection to database");
        }
        return connection;
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
            //e.printStackTrace();
            throw new DatabaseException("unable to close database connection");
        } catch (NullPointerException e) {
            //e.printStackTrace();
            throw new DatabaseException("tried to close a null connection");
        }
    }

    public boolean importData() {
        // TODO
        return false;
    }

    public void initAll() {
        try {
            initUsers();
            initPeople();
            initEvents();
            initAuthTokens();
        } catch (DatabaseException ex) {
            System.err.println("init tables failed!");
            System.err.println(ex);
        }
    }

    public void initUsers() throws DatabaseException {
        connect();
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
            // if we made it this far, we're safe and can commit
            closeConnection(true);
        } catch (DatabaseException e) {
            closeConnection(false);
            throw e;
        } catch (SQLException e) {
            closeConnection(false);
            throw new DatabaseException("sql error encountered while creating user table");
        }
    }

    public void initPeople() throws DatabaseException {
        connect();
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
            // if we made it this far, we're safe and can commit
            closeConnection(true);
        } catch (DatabaseException e) {
            closeConnection(false);
            throw e;
        } catch (SQLException e) {
            closeConnection(false);
            throw new DatabaseException("sql error encountered while creating people table");
        }
    }

    public void initEvents() throws DatabaseException {
        connect();
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
            // if we made it this far, we're safe and can commit
            closeConnection(true);
        } catch (DatabaseException e) {
            closeConnection(false);
            throw e;
        } catch (SQLException e) {
            closeConnection(false);
            throw new DatabaseException("sql error encountered while creating event table");
        }
    }

    public void initAuthTokens() throws DatabaseException {
        connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "create table if not exists auth_token " +
                    "(" +
                    "token      varchar(255)  not null primary key, " +
                    "username   varchar(255)  not null  " +
                    ");";
            statement.execute(sql);
            // if we made it this far, we're safe and can commit
            closeConnection(true);
        } catch (DatabaseException e) {
            closeConnection(false);
            throw e;
        } catch (SQLException e) {
            closeConnection(false);
            throw new DatabaseException("sql error encountered while creating authtoken table");
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
        } catch (DatabaseException e) {
            closeConnection(false);
            throw e;
        } catch (SQLException e) {
            closeConnection(false);
            throw new DatabaseException("sql error encountered while clearing tables");
        }
    }

    public boolean clearUsers() {
        return false;
    }

    public boolean clearPeople() {
        return false;
    }

    public boolean clearEvents() {
        return false;
    }

    public boolean clearAuthTokens() {
        return false;
    }
}
