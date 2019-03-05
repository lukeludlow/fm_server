package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.UserDao;
import message.request.RegisterRequest;
import message.response.RegisterResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegisterServiceTest {

    Database db;
    UserDao userDao;
    User luke;
    User foundUser;
    AuthTokenDao authTokenDao;
    AuthToken secret;
    AuthToken foundAuthToken;
    RegisterRequest request;
    RegisterResponse expectedResponse;
    RegisterService service;
    RegisterResponse actual;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        userDao = new UserDao(db);
        authTokenDao = new AuthTokenDao(db);
        luke = new User("lukeludlow", "hunter2", "luke@luke.com","luke","ludlow","m","99");
        secret = new AuthToken("xX_secret_Xx", "lukeludlow");
        foundUser = null;
        foundAuthToken = null;
        expectedResponse = new RegisterResponse("xX_secret_Xx", "lukeludlow", "99");
        actual = null;
        request = new RegisterRequest("lukeludlow", "hunter2", "ll@live.com", "luke", "ludlow", "m");
        service = new RegisterService();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (db.getConnection() != null) {
            db.closeResponseConnection(false);
        }
    }

    @Test
    @DisplayName("register success")
    void testRegister() throws Exception {
        actual = service.register(request);
        assertNotNull(actual.getAuthToken());
        assertNotNull(actual.getPersonID());
        // authtoken and ID are always random, set them so we can compare
        actual.setPersonID("99");
        actual.setAuthToken("xX_secret_Xx");
        assertEquals(expectedResponse, actual);
    }

    @Test
    @DisplayName("register fail (user already exists)")
    void testRegisterFail() throws Exception {
        try {
            db.connect();
            userDao.insert(luke);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actual = service.register(request));
        System.out.println(exception.getMessage());
        assertTrue(exception.getMessage().contains("username already taken."));
    }


}