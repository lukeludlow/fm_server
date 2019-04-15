package service;

import data.*;
import message.response.ClearResponse;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    User lukeUser;
    Person lukePerson;
    Event lukeBirthday;
    AuthToken lukeAuth;

    User foundUser;
    Person foundPerson;
    Event foundEvent;
    AuthToken foundToken;

    Database db;
    UserDao userDao;
    PersonDao personDao;
    EventDao eventDao;
    AuthTokenDao authTokenDao;

    ClearService clearService;
    ClearResponse expectedResponse;
    ClearResponse actualResponse;

    @BeforeEach
    void setUp() throws Exception {
        lukeUser = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
        lukePerson = new Person("1", "lukeludlow", "luke", "ludlow", "m", "none", "none", "none");
        lukeBirthday = new Event("9","lukeludlow","1",40.0,-111.0,"usa","reno","birth",1999);
        lukeAuth = new AuthToken("69", "lukeludlow");
        db = new Database();
        db.clearAll();
        userDao = new UserDao(db);
        personDao = new PersonDao(db);
        eventDao = new EventDao(db);
        authTokenDao = new AuthTokenDao(db);
        clearService = new ClearService();
        expectedResponse = new ClearResponse("Clear succeeded.");
        actualResponse = null;
    }
    @AfterEach
    void tearDown() throws Exception {
        db.clearAll();
        if (db.getConnection() != null) {
            db.closeResponseConnection(false);
        }
    }

    @Test
    @DisplayName("clear success")
    void testClear() throws Exception {

        // insert one of everything
        try {
            db.connect();
            userDao.insert(lukeUser);
            personDao.insert(lukePerson);
            eventDao.insert(lukeBirthday);
            authTokenDao.insert(lukeAuth);
            foundUser = userDao.find(lukeUser.getPrimaryKey());
            foundPerson = personDao.find(lukePerson.getPrimaryKey());
            foundEvent = eventDao.find(lukeBirthday.getPrimaryKey());
            foundToken = authTokenDao.find(lukeAuth.getPrimaryKey());
            db.closeResponseConnection(true);
            assertNotNull(foundUser);
            assertNotNull(foundPerson);
            assertNotNull(foundEvent);
            assertNotNull(foundToken);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }

        actualResponse = clearService.clear();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        // make sure everything really did get deleted
        try {
            db.connect();
            foundUser = userDao.find(lukeUser.getPrimaryKey());
            foundPerson = personDao.find(lukePerson.getPrimaryKey());
            foundEvent = eventDao.find(lukeBirthday.getPrimaryKey());
            foundToken = authTokenDao.find(lukeAuth.getPrimaryKey());
            db.closeResponseConnection(true);
            assertNull(foundUser);
            assertNull(foundPerson);
            assertNull(foundEvent);
            assertNull(foundToken);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }

    }

    @Test
    @DisplayName("clear success 2 (database is already empty)")
    void testClear2() throws Exception {

        // make sure database is clear at beginning
        try {
            db.clearAll();
            db.connect();
            foundUser = userDao.find(lukeUser.getPrimaryKey());
            foundPerson = personDao.find(lukePerson.getPrimaryKey());
            foundEvent = eventDao.find(lukeBirthday.getPrimaryKey());
            foundToken = authTokenDao.find(lukeAuth.getPrimaryKey());
            db.closeResponseConnection(true);
            assertNull(foundUser);
            assertNull(foundPerson);
            assertNull(foundEvent);
            assertNull(foundToken);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }

        actualResponse = clearService.clear();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        // make sure database is still empty
        try {
            db.connect();
            foundUser = userDao.find(lukeUser.getPrimaryKey());
            foundPerson = personDao.find(lukePerson.getPrimaryKey());
            foundEvent = eventDao.find(lukeBirthday.getPrimaryKey());
            foundToken = authTokenDao.find(lukeAuth.getPrimaryKey());
            db.closeResponseConnection(true);
            assertNull(foundUser);
            assertNull(foundPerson);
            assertNull(foundEvent);
            assertNull(foundToken);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }

    }

}