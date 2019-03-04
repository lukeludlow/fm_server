package service;

import data.AuthTokenDao;
import data.Database;
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
    LoginRequest loginRequest;
    LoginResponse loginResponse;
    LoginService loginService;
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
        loginRequest = new LoginRequest("lukeludlow", "password");
        loginResponse = new LoginResponse("xX_secret_Xx", "lukeludlow", "99");
        loginService = new LoginService();
        actualResponse = null;
    }
    @AfterEach
    void tearDown() throws Exception {
        db.clearAll();
    }

    @Test
    @DisplayName("login success")
    void testLogin() throws Exception {
        userDao.insert(luke);
        actualResponse = loginService.login(loginRequest);
        actualResponse.setAuthToken("xX_secret_Xx"); // generated authtoken is always unique, so manually set it every test
        assertEquals(loginResponse, actualResponse);
    }

    @Test
    @DisplayName("login authtoken")
    void testAuthToken() throws Exception {
        userDao.insert(luke);
        actualResponse = loginService.login(loginRequest);
        assertNotNull(actualResponse.getAuthToken());
    }

    @Test
    @DisplayName("login fail (incorrect password)")
    void testLoginFail2() throws Exception {
        userDao.insert(luke);
        loginRequest.setPassword("wrong");
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actualResponse = loginService.login(loginRequest));
        assertTrue(exception.getMessage().contains("incorrect password"));
    }

    @Test
    @DisplayName("login fail (user does not exist)")
    void testInsertFail() {
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actualResponse = loginService.login(loginRequest));
        assertTrue(exception.getMessage().contains("user not found"));
    }

}