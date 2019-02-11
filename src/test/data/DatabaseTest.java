package test.data;

import data.Database;
import data.DatabaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    @DisplayName("connect success")
    public void testConnect() throws Exception {
        Database db = new Database();
        try {
            db.connect();
            assertNotNull(db.getConnection());
            db.closeConnection(true);
        } catch (DatabaseException e) {
            db.closeConnection(false);
        }
    }

    @Test
    @DisplayName("connect fail")
    public void testConnectFail() throws Exception {
        boolean connectSuccess = true;
        try {
            // wrong url
            DriverManager.getConnection("db/fms.sqlite");
        } catch (SQLException e) {
            connectSuccess = false;
        }
        assertFalse(connectSuccess);
    }

    @Test
    @DisplayName("closeConnection success")
    public void testCloseConnection() throws Exception {
        Database db = new Database();
        boolean closeSuccess = true;
        try {
            db.connect();
            db.closeConnection(true);
        } catch (DatabaseException e) {
            closeSuccess = false;
            db.closeConnection(false);
        }
        assertTrue(closeSuccess);
    }

    @Test
    @DisplayName("closeConnection fail")
    public void testCloseConnectionFail() throws Exception {
        Database db = new Database();
        boolean closeSuccess = true;
        try {
            db.closeConnection(true);
        } catch (DatabaseException e) {
            closeSuccess = false;
        }
        assertFalse(closeSuccess);
    }

}
