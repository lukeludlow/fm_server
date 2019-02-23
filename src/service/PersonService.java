package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.PersonDao;
import message.response.PersonResponse;
import message.request.PersonRequest;
import model.AuthToken;
import model.Person;

/**
 * person service : web api method /person/[personID]
 */
public class PersonService {
    /**
     * returns the single person object with the specified id.
     */
    public PersonResponse getPerson(PersonRequest request) {
        Database db = new Database();
        PersonDao personDao = new PersonDao(db);
        Person found = null;
        try {
            // first, make sure authtoken is valid
            AuthTokenDao authTokenDao = new AuthTokenDao(db);
            AuthToken foundToken = authTokenDao.find(request.getAuthToken());
            if (foundToken == null) {
                // TODO return error response
                return null;
            }
            found = personDao.find(request.getPersonID());
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
        }
        if (found != null) {
            return new PersonResponse(found);
        }
        return null;
    }
}
