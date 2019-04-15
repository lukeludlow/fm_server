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

/**
 * person service : web api method /person/[personID]
 */
public class PersonService {

    private Database db;
    private PersonDao personDao;
    private AuthTokenDao authTokenDao;
    private Person person;
    private AuthToken foundToken;

    public PersonService() {
        db = new Database();
        personDao = new PersonDao(db);
        authTokenDao = new AuthTokenDao(db);
        person = null;
        foundToken = null;
    }

    public PersonResponse getPerson(PersonRequest request) throws ResponseException {
        find(request);
        checkValidity();
        return new PersonResponse(person);
    }

    private void find(PersonRequest request) throws ResponseException {
        try {
            db.connect();
            foundToken = authTokenDao.find(request.getAuthToken());
            person = personDao.find(request.getPersonID());
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw new ResponseException(e);
        }
    }

    private void checkValidity() throws ResponseException {
        if (foundToken == null) {
            throw new ResponseException("invalid authtoken");
        }
        if (person == null) {
            throw new ResponseException("person not found");
        }
        if (!person.getDescendant().equals(foundToken.getUserName())) {
            throw new ResponseException("not allowed to retrieve information that belongs to another user");
        }
    }

}
