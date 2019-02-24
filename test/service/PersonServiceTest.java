package service;

import data.AuthTokenDao;
import data.Database;
import data.PersonDao;
import message.request.PersonRequest;
import message.response.PersonResponse;
import model.AuthToken;
import model.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    @Test
    void testPersonService() throws Exception {
        Person luke = new Person("1", "lukeludlow", "luke", "ludlow", "m", "none", "none", "none");
        Person foundLuke = null;
        Database db = new Database();
        db.clearAll();
        PersonDao personDao = new PersonDao(db);
        personDao.insert(luke);
        PersonResponse response = new PersonResponse(luke);
        PersonService personService = new PersonService();
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        AuthToken secret = new AuthToken("xX_secret_Xx", "lukeludlow");
        authTokenDao.insert(secret);
        PersonRequest personRequest = new PersonRequest(luke.getPersonID(), secret.getAuthtoken());
        PersonResponse actualResponse = personService.getPerson(personRequest);
        assertNotNull(actualResponse);
        assertEquals(response, actualResponse);
        db.clearAll();
    }


}
