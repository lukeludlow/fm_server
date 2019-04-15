package data;

import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
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

    @Test
    @DisplayName("clear all success")
    void testClearAll() throws Exception {
        try {
            db.clearAll();
            db.connect();
            UserDao userDao = new UserDao(db);
            PersonDao personDao = new PersonDao(db);
            EventDao eventDao = new EventDao(db);
            AuthTokenDao authTokenDao = new AuthTokenDao(db);
            User lukeUser = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
            Person lukePerson = new Person("lukeludlow", "1", "luke", "ludlow", "m", "none", "none", "none");
            Event birthday = new Event("lukeludlow","9","1",40.0,-111.0,"usa","reno","birth",1999);
            AuthToken bitcoin = new AuthToken("69", "lukeludlow");
            userDao.insert(lukeUser);
            personDao.insert(lukePerson);
            eventDao.insert(birthday);
            authTokenDao.insert(bitcoin);
            User foundUser;
            Person foundPerson;
            Event foundEvent;
            AuthToken foundToken;
            foundUser = userDao.find(lukeUser.getPrimaryKey());
            foundPerson = personDao.find(lukePerson.getPrimaryKey());
            foundEvent = eventDao.find(birthday.getPrimaryKey());
            foundToken = authTokenDao.find(bitcoin.getPrimaryKey());
            db.closeConnection(true);
            assertEquals(lukeUser, foundUser);
            assertEquals(lukePerson, foundPerson);
            assertEquals(birthday, foundEvent);
            assertEquals(bitcoin, foundToken);

            db.clearAll();
            db.connect();
            foundUser = userDao.find(lukeUser.getPrimaryKey());
            foundPerson = personDao.find(lukePerson.getPrimaryKey());
            foundEvent = eventDao.find(birthday.getPrimaryKey());
            foundToken = authTokenDao.find(bitcoin.getPrimaryKey());
            db.closeConnection(true);
            assertNull(foundUser);
            assertNull(foundPerson);
            assertNull(foundEvent);
            assertNull(foundToken);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("clear all success 2")
    void testClearAll2() throws Exception {
        try {
            db.clearAll();
            db.connect();
            UserDao userDao = new UserDao(db);
            PersonDao personDao = new PersonDao(db);
            EventDao eventDao = new EventDao(db);
            AuthTokenDao authTokenDao = new AuthTokenDao(db);
            User foundUser = userDao.find("x");
            Person foundPerson = personDao.find("x");
            Event foundEvent = eventDao.find("x");
            AuthToken foundToken = authTokenDao.find("x");
            db.closeConnection(true);
            assertNull(foundUser);
            assertNull(foundPerson);
            assertNull(foundEvent);
            assertNull(foundToken);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("clear all authtokens success")
    void testClearTokens() throws Exception {
        try {
            db.clearAuthTokens();
            db.connect();
            AuthTokenDao authTokenDao = new AuthTokenDao(db);
            AuthToken found = authTokenDao.find("tokenthatdoesnotexist");
            assertNull(found);
            db.closeConnection(true);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("clear all authtokens success 2")
    void testClearTokens2() throws Exception {
        try {
            db.clearAuthTokens();
            db.connect();
            AuthTokenDao authTokenDao = new AuthTokenDao(db);
            AuthToken authToken = new AuthToken("secret", "lukeludlow");
            authTokenDao.insert(authToken);
            AuthToken found = authTokenDao.find("secret");
            assertEquals(authToken, found);
            db.closeConnection(true);
            db.clearAuthTokens();
            db.connect();
            found = authTokenDao.find("secret");
            assertNull(found);
            db.closeConnection(true);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

}

