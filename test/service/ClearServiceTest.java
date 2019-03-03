package service;

import data.*;
import message.response.ClearResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    ClearResponse clearResponse;
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
        clearResponse = new ClearResponse("clear succeeded.");
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
    void testClear() throws Exception {
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
            assertNotNull(foundUser);
            assertNotNull(foundPerson);
            assertNotNull(foundEvent);
            assertNotNull(foundToken);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }

        actualResponse = clearService.clear();
        assertNotNull(actualResponse);
        assertEquals(clearResponse, actualResponse);

        // make sure everything really did get deleted
        try {
            db.connect();
            foundUser = userDao.find(lukeUser.getPrimaryKey());
            foundPerson = personDao.find(lukePerson.getPrimaryKey());
            foundEvent = eventDao.find(lukeBirthday.getPrimaryKey());
            foundToken = authTokenDao.find(lukeAuth.getPrimaryKey());
            assertNull(foundUser);
            assertNull(foundPerson);
            assertNull(foundEvent);
            assertNull(foundToken);
            db.closeResponseConnection(true);
        } catch (DatabaseException | ResponseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
    }

}