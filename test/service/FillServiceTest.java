package service;

import data.*;
import message.request.FillRequest;
import message.response.FillResponse;
import message.response.ResponseException;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FillServiceTest {

    Database db;
    UserDao userDao;
    PersonDao personDao;
    EventDao eventDao;
    User luke;
    User foundUser;
    Person foundPerson;
    Event foundEvent;
    FillRequest request;
    FillResponse response;
    FillResponse expectedResponse;
    FillService service;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        userDao = new UserDao(db);
        db.clearAll();
        luke = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
        service = new FillService();
    }
    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testFill0() throws Exception {
        insertLukeUser();
        request = new FillRequest("lukeludlow", 0);
        response = service.fill(request);
        String expected = "Successfully added 1 persons and 1 events to the database.";
        assertEquals(expected, response.getMessage());
    }

    @Test
    void testFill4() throws Exception {
        insertLukeUser();
        request = new FillRequest("lukeludlow", 4);
        response = service.fill(request);
        String expected = "Successfully added 31 persons and 91 events to the database.";
        assertEquals(expected, response.getMessage());
    }

    @Test
    void testFillInvalidUsername() throws Exception {
        request = new FillRequest("wrong", 1);
        ResponseException exception = assertThrows(ResponseException.class,
                () -> response = service.fill(request));
        assertTrue(exception.getMessage().contains("user not found"));
    }

    @Test
    void testFillBadGenerationsParam() throws Exception {
        insertLukeUser();
        request = new FillRequest("lukeludlow", -20);
        response = service.fill(request);
        // invalid generations param just gets changed to the default value (4).
        String expected = "Successfully added 31 persons and 91 events to the database.";
        assertEquals(expected, response.getMessage());
    }

    void insertLukeUser() throws Exception {
        try {
            db.connect();
            userDao.insert(luke);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
    }

}