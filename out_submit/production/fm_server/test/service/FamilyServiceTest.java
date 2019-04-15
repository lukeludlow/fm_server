package service;

import data.*;
import message.request.FamilyRequest;
import message.response.FamilyResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FamilyServiceTest {

    Database db;
    User lukeUser;
    Person luke;
    Person anotherPerson;
    Person foundLuke;
    Person foundAnother;
    UserDao userDao;
    PersonDao eventDao;
    AuthTokenDao authTokenDao;
    AuthToken secret;
    AuthToken foundToken;
    Person[] expectedData;
    FamilyService service;
    FamilyRequest request;
    FamilyResponse expectedResponse;
    FamilyResponse actual;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        userDao = new UserDao(db);
        eventDao = new PersonDao(db);
        authTokenDao = new AuthTokenDao(db);
        lukeUser = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
        luke = new Person("lukeludlow", "1", "luke", "ludlow", "m", "none", "none", "none");
        anotherPerson = new Person("lukeludlow",  "2", "p", "2", "m", "none", "none", "none");
        secret = new AuthToken("xX_secret_Xx", "lukeludlow");
        expectedData = new Person[]{
                luke,
                anotherPerson
        };
        service = new FamilyService();
        request = new FamilyRequest(secret.getAuthToken());
        expectedResponse = new FamilyResponse(expectedData);
        actual = null;
    }

    @AfterEach
    void tearDown() throws Exception {
        if (db.getConnection() != null) {
            db.closeResponseConnection(false);
        }
    }

    @Test
    @DisplayName("get all people success")
    void testFamily() throws Exception {
        try {
            db.connect();
            userDao.insert(lukeUser);
            eventDao.insert(luke);
            eventDao.insert(anotherPerson);
            authTokenDao.insert(secret);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        actual = service.getFamily(request);
        assertEquals(expectedResponse, actual);
    }

    @Test
    @DisplayName("get all people fail (invalid authtoken)")
    void testFamilyFail() throws Exception {
        try {
            db.connect();
            userDao.insert(lukeUser);
            eventDao.insert(luke);
            eventDao.insert(anotherPerson);
            authTokenDao.insert(secret);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        request.setAuthToken("wrong_token");
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actual = service.getFamily(request));
        assertTrue(exception.getMessage().contains("invalid authtoken"));
    }

}

