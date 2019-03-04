package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.PersonDao;
import message.request.PersonRequest;
import message.response.PersonResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    Database db;
    PersonDao personDao;
    Person luke;
    Person anotherPerson;
    Person foundLuke;
    Person foundAnother;
    AuthTokenDao authTokenDao;
    AuthToken secret;
    AuthToken foundToken;
    PersonResponse expectedResponse;
    PersonService service;
    PersonRequest request;
    PersonResponse actual;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        personDao = new PersonDao(db);
        authTokenDao = new AuthTokenDao(db);
        luke = new Person("lukeludlow", "1", "luke", "ludlow", "m", "none", "none", "none");
        anotherPerson = new Person("p2", "2", "p", "2", "m", "none", "none", "none");
        secret = new AuthToken("xX_secret_Xx", "lukeludlow");
        foundLuke = null;
        foundAnother = null;
        foundToken = null;
        expectedResponse = null;
        service = new PersonService();
        request = new PersonRequest(luke.getPersonID(), secret.getAuthToken());
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    @DisplayName("get person success")
    void testGetPerson() throws Exception {
        try {
            db.connect();
            personDao.insert(luke);
            authTokenDao.insert(secret);
            foundLuke = personDao.find(luke.getPrimaryKey());
            foundToken = authTokenDao.find(secret.getPrimaryKey());
            db.closeResponseConnection(true);
            assertEquals(luke, foundLuke);
            assertEquals(secret, foundToken);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        expectedResponse = new PersonResponse(luke);
        actual = service.getPerson(request);
        assertEquals(expectedResponse, actual);
    }

    @Test
    @DisplayName("get person fail (person does not exist)")
    void testGetPersonFail() throws Exception {
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
                () -> actual = service.getPerson(request));
        assertTrue(exception.getMessage().contains("person not found"));
    }

    @Test
    @DisplayName("get person fail (invalid authtoken)")
    void testGetPersonFail2() throws Exception {
        try {
            db.connect();
            personDao.insert(luke);
            authTokenDao.insert(secret);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        request.setAuthToken("wrong_token");
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actual = service.getPerson(request));
        assertTrue(exception.getMessage().contains("invalid authtoken"));
    }

    @Test
    @DisplayName("get person fail (person belongs to another user")
    void testGetPersonFail3() throws Exception {
        try {
            db.connect();
            luke = new Person("wrong_descendant", "1", "luke", "ludlow", "m", "none", "none", "none");
            personDao.insert(luke);
            authTokenDao.insert(secret); // secret belongs to user lukeludlow
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw e;
        }
        ResponseException exception = assertThrows(ResponseException.class,
                () -> actual = service.getPerson(request));
        assertTrue(exception.getMessage().contains("not allowed to retrieve information that belongs to another user"));
    }

}
