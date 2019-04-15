package service;

import data.*;
import message.request.HistoryRequest;
import message.response.HistoryResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HistoryServiceTest {

    Database db;
    User luke;
    Event birthday;
    Event anotherEvent;
    Event foundBirthday;
    Event foundAnother;
    UserDao userDao;
    EventDao eventDao;
    AuthTokenDao authTokenDao;
    AuthToken secret;
    Event[] expectedData;
    HistoryService service;
    HistoryRequest request;
    HistoryResponse expectedResponse;
    HistoryResponse actual;


    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        luke = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
        birthday = new Event("lukeludlow","9", "1",40.0,-111.0,"usa","reno","birth",1999);
        anotherEvent = new Event("lukeludlow", "101", "nunya01",10.1,-10.1, "japan", "tokyo", "birth", 3019);
        userDao = new UserDao(db);
        eventDao = new EventDao(db);
        authTokenDao = new AuthTokenDao(db);
        secret = new AuthToken("xX_secret_Xx", "lukeludlow");
        expectedData = new Event[]{
            birthday,
            anotherEvent
        };
        service = new HistoryService();
        request = new HistoryRequest(secret.getAuthToken());
        expectedResponse = new HistoryResponse(expectedData);
        actual = null;
    }

    @AfterEach
    void tearDown() throws Exception {
        if (db.getConnection() != null) {
            db.closeResponseConnection(false);
        }
    }

    @Test
    @DisplayName("get all events success")
    void testHistory() throws Exception {
        try {
            db.connect();
            userDao.insert(luke);
            eventDao.insert(birthday);
            eventDao.insert(anotherEvent);
            authTokenDao.insert(secret);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        actual = service.getHistory(request);
        assertEquals(expectedResponse, actual);
    }

    @Test
    @DisplayName("get all events fail (invalid authtoken)")
    void testHistoryFail() throws Exception {
        try {
            db.connect();
            userDao.insert(luke);
            eventDao.insert(birthday);
            eventDao.insert(anotherEvent);
            authTokenDao.insert(secret);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        request.setAuthToken("wrong_token");
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actual = service.getHistory(request));
        assertTrue(exception.getMessage().contains("invalid authtoken"));
    }



}
