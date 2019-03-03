package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    Database db;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
    }

    @Test
    @DisplayName("connect success")
    void testConnect() throws Exception {
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
    void testConnectFail() throws Exception {
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
    void testCloseConnection() throws Exception {
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
    void testCloseConnectionFail() throws Exception {
        boolean closeSuccess = false;
        try {
            db.closeConnection(true);
            closeSuccess = true;
        } catch (DatabaseException e) {
            closeSuccess = false;
        }
        assertFalse(closeSuccess);
    }

//    // TODO test clear all positive and negative
//    // @Test
//    // testClearAll
//
//    // TODO
//    @Test
//    @DisplayName("clear all authtokens")
//    void testClearTokens() throws Exception {
//        try {
//            db.clearAuthTokens();
//        } catch (DatabaseException e) {
//            throw e;
//        }
//    }
//
//    // TODO
//    @Test
//    @DisplayName("clear all authtokens fail (bad connection)")
//    void testClearTokensFail() throws Exception {
//        DatabaseException exception = assertThrows(DatabaseException.class,
//                () -> db.clearAuthTokens());
//        System.out.println(exception.getMessage());
//        assertTrue(exception.getMessage().contains("unable to clear authtokens. dao tried to operate on closed connection."));
//    }

}

