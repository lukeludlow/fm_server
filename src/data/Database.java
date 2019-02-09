package data;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Connection;

/**
 * connects to sqlite database file. all the data access objects utilize this database object.
 */
@Data
@NoArgsConstructor
public class Database {
    /** sql connection to sqlite database file */
    private Connection connection;
    private static final String DRIVER_NAME = "org.sqlite.JDBC";
    private static final String DB_NAME = "fms.sqlite";
    /** connect url. "driver + path/to/database". path is relative to project root. */
    private static final String DB_URL = "jdbc:sqlite:db/fms.sqlite";

    /**
     * open connection to the fms.sqlite database
     * @return new sql connection
     */
    public Connection connect() {
        return null;
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
    public boolean initAll() {
        return false;
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
    public boolean clearAll() {
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
