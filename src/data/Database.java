package data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * connects to sqlite database file. all the data access objects utilize this database object.
 */
@Data
@NoArgsConstructor
public class Database {
    /** sql connection to sqlite database file */
    private Connection connection;
    /** connect url. "driver + path/to/database". path is relative to project root. */
    private static final String URL = "jdbc:sqlite:db/fms.sqlite";
    private static final String DRIVER = "org.sqlite.JDBC";

    // set up database driver
    static {
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * open connection to the fms.sqlite database
     *
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

    /**
     * either commit or rollback and then close connection
     *
     * @param commit commit transaction or nah
     * @throws DatabaseException if things break
     */
    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                connection.commit();
            } else {
                connection.rollback();
            }
            connection.close();
            connection = null;
        }
        catch (SQLException e) {
            //e.printStackTrace();
            throw new DatabaseException("unable to close database connection");
        }
        catch (NullPointerException e) {
            //e.printStackTrace();
            throw new DatabaseException("tried to close a null connection");
        }
    }

    /**
     * import given json dataset
     *
     * @return true on success
     */
    public boolean importData() {
        return false;
    }

    /**
     * create all tables (if they exist)
     */
    public void initAll() {
        try {
            initUsers();
            initPeople();
            initEvents();
            initAuthTokens();
        }
        catch (DatabaseException e) {
            System.err.println("init tables failed!");
            System.err.println(e);
        }
    }

    /**
     * create table if not exists user
     */
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

    /**
     * create table if not exists person
     */
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

    /**
     * create table if not exists event
     */
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

    /**
     * create table if not exists authtoken
     */
    public void initAuthTokens() throws DatabaseException {
        connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "create table if not exists auth_token " +
                    "(" +
                    "token      varchar(255)  not null, " +
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

    /**
     * delete all info from all tables
     */
    public void clearAll() throws DatabaseException {

        connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "delete from user";
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

    /**
     * drop table if exists user
     */
    public boolean clearUsers() {
        return false;
    }

    /**
     * drop table if exists person
     */
    public boolean clearPeople() {
        return false;
    }

    /**
     * drop table if exists event
     */
    public boolean clearEvents() {
        return false;
    }

    /**
     * drop table if exists authtoken
     */
    public boolean clearAuthTokens() {
        return false;
    }
}
