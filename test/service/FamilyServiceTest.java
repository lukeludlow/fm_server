package service;

import data.AuthTokenDao;
import data.Database;
import data.PersonDao;
import message.request.FamilyRequest;
import message.response.FamilyResponse;
import model.AuthToken;
import model.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FamilyServiceTest {

    @Test
    void testFamilyService() throws Exception {
        Person luke = new Person("1", "lukeludlow", "luke", "ludlow", "m", "none", "none", "none");
        Person anotherPerson = new Person("2", "lukeludlow", "p", "2", "m", "none", "none", "none");
        Database db = new Database();
        db.clearAll();
        PersonDao eventDao = new PersonDao(db);
        eventDao.insert(luke);
        eventDao.insert(anotherPerson);
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        AuthToken secret = new AuthToken("xX_secret_Xx", "lukeludlow");
        authTokenDao.insert(secret);
        Person[] expectedData = {
                luke,
                anotherPerson
        };
        FamilyResponse expectedResponse = new FamilyResponse(expectedData);
        FamilyService familyService = new FamilyService();
        FamilyRequest familyRequest = new FamilyRequest(secret.getAuthtoken());
        FamilyResponse actualResponse = familyService.getFamily(familyRequest);
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        db.clearAll();
    }


}

