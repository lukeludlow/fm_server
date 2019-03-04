package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.EventDao;
import message.request.EventRequest;
import message.response.EventResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EventServiceTest {

    Database db;
    EventDao eventDao;
    Event birthday;
    Event anotherEvent;
    Event foundBirthday;
    Event foundAnother;
    AuthTokenDao authTokenDao;
    AuthToken secret;
    AuthToken foundToken;
    EventResponse expectedResponse;
    EventService service;
    EventRequest request;
    EventResponse actual;


    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        eventDao = new EventDao(db);
        birthday = new Event("lukeludlow","9", "1",40.0,-111.0,"usa","reno","birth",1999);
        anotherEvent = new Event("nunya",  "101", "nunya01",10.1,-10.1, "japan", "tokyo", "birth", 3019);
        foundBirthday = null;
        foundAnother = null;
        authTokenDao = new AuthTokenDao(db);
        secret = new AuthToken("xX_secret_Xx", "lukeludlow");
        foundToken = null;
        expectedResponse = null;
        service = new EventService();
        request = new EventRequest(birthday.getEventID(), secret.getAuthToken());
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    @DisplayName("get event success")
    void testGetEvent() throws Exception {
        try {
            db.connect();
            eventDao.insert(birthday);
            authTokenDao.insert(secret);
            foundBirthday = eventDao.find(birthday.getPrimaryKey());
            foundToken = authTokenDao.find(secret.getPrimaryKey());
            db.closeResponseConnection(true);
            assertEquals(birthday, foundBirthday);
            assertEquals(secret, foundToken);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        expectedResponse = new EventResponse(birthday);
        actual = service.getEvent(request);
        assertEquals(expectedResponse, actual);
    }

    @Test
    @DisplayName("get event fail (event does not exist)")
    void testGetEventFail() throws Exception {
        // insert authtoken so that request goes through
        try {
            db.connect();
            authTokenDao.insert(secret);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actual = service.getEvent(request));
        assertTrue(exception.getMessage().contains("event not found"));
    }

    @Test
    @DisplayName("get event fail (invalid authtoken)")
    void testGetEventFail2() throws Exception {
        try {
            db.connect();
            eventDao.insert(birthday);
            authTokenDao.insert(secret);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        request.setAuthToken("wrong_token");
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actual = service.getEvent(request));
        assertTrue(exception.getMessage().contains("invalid authtoken"));
    }

    @Test
    @DisplayName("get event fail (event belongs to another user")
    void testGetEventFail3() throws Exception {
        try {
            db.connect();
            birthday = new Event("wrong_descendant","9", "1",40.0,-111.0,"usa","reno","birth",1999);
            eventDao.insert(birthday);
            authTokenDao.insert(secret); // secret belongs to user lukeludlow
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actual = service.getEvent(request));
        assertTrue(exception.getMessage().contains("not allowed to retrieve information that belongs to another user"));
    }

}