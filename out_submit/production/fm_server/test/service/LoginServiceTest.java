package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.UserDao;
import message.request.LoginRequest;
import message.response.LoginResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    Database db;
    UserDao userDao;
    User luke;
    User foundUser;
    AuthTokenDao authTokenDao;
    AuthToken secret;
    AuthToken foundAuthToken;
    LoginRequest request;
    LoginResponse expectedResponse;
    LoginService service;
    LoginResponse actualResponse;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        userDao = new UserDao(db);
        authTokenDao = new AuthTokenDao(db);
        luke = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","99");
        secret = new AuthToken("xX_secret_Xx", "lukeludlow");
        foundUser = null;
        foundAuthToken = null;
        request = new LoginRequest("lukeludlow", "password");
        expectedResponse = new LoginResponse("xX_secret_Xx", "lukeludlow", "99");
        service = new LoginService();
        actualResponse = null;
    }

    @AfterEach
    void tearDown() throws Exception {
        db.clearAll();
    }

    @Test
    @DisplayName("login success")
    void testLogin() throws Exception {
        try {
            db.connect();
            userDao.insert(luke);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        actualResponse = service.login(request);
        actualResponse.setAuthToken("xX_secret_Xx"); // generated authtoken is always unique, so manually set it for comparison
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("login success (make sure random authtoken is generated)")
    void testAuthToken() throws Exception {
        try {
            db.connect();
            userDao.insert(luke);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        actualResponse = service.login(request);
        assertNotNull(actualResponse.getAuthToken());
    }

    @Test
    @DisplayName("login fail (incorrect password)")
    void testLoginFail2() throws Exception {
        try {
            db.connect();
            userDao.insert(luke);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        request.setPassword("wrong_password");
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actualResponse = service.login(request));
        assertTrue(exception.getMessage().contains("incorrect password"));
    }

    @Test
    @DisplayName("login fail (user does not exist)")
    void testInsertFail() {
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actualResponse = service.login(request));
        assertTrue(exception.getMessage().contains("user not found"));
    }

}