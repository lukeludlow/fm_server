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
    private static final String DRIVER = "org.sqlite.JDBC";
    /** connect url. "driver + path/to/database". path is relative to project root. */
    private static final String URL = "jdbc:sqlite:db/fms.sqlite";


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
     * @return new sql connection
     */
    public Connection connect() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(URL);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("unable to open connection to database");
        }
        return connection;
    }

    /**
     * either commit or rollback and then close connection
     * @param commit commit transaction or nah
     * @throws DatabaseException if things break
     */
    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                connection.commit();
            }
            else {
                connection.rollback();
            }
            connection.close();
            connection = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("unable to close database connection");
        }
    }

    /**
     * import given json dataset
     * @return true on success
     */
    public boolean importData() {
        return false;
    }

    /**
     * create all tables (if they exist)
     * @return true on success
     */
    public void initAll() throws DatabaseException {
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
                    ")";
            statement.execute(sql);
            // if we made it this far, we're safe and can commit
            closeConnection(true);
        }
        catch (DatabaseException e) {
            closeConnection(false);
            throw e;
        }
        catch (SQLException e) {
            closeConnection(false);
            throw new DatabaseException("sql error encountered while creating tables");
        }
    }

    /**
     * create table if not exists user
     * @return true on success
     */
    public boolean initUsers() {
        return false;
    }
    /**
     * create table if not exists person
     * @return true on success
     */
    public boolean initPeople() {
        return false;
    }
    /**
     * create table if not exists event
     * @return true on success
     */
    public boolean initEvents() {
        return false;
    }
    /**
     * create table if not exists authtoken
     * @return true on success
     */
    public boolean initAuthTokens() {
        return false;
    }

    /**
     * drop all tables (if they exist)
     * @return true on success
     */
    public boolean clearAll() throws DatabaseException {
        connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "delete from user";
            statement.execute(sql);
            closeConnection(true);
        }
        catch (DatabaseException e) {
            closeConnection(false);
            throw e;
        }
        catch (SQLException e) {
            closeConnection(false);
            throw new DatabaseException("sql error encountered while clearing tables");
        }
        return false;
    }

    /**
     * drop table if exists user
     * @return true on success
     */
    public boolean clearUsers() {
        return false;
    }
    /**
     * drop table if exists person
     * @return true on success
     */
    public boolean clearPeople() {
        return false;
    }
    /**
     * drop table if exists event
     * @return true on success
     */
    public boolean clearEvents() {
        return false;
    }
    /**
     * drop table if exists authtoken
     * @return true on success
     */
    public boolean clearAuthTokens() {
        return false;
    }
}
