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
    /**
     * returns the single person object with the specified id.
     */
    public PersonResponse getPerson(PersonRequest request) throws ResponseException {
        Database db = new Database();
        PersonDao personDao = new PersonDao(db);
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        Person found = null;
        AuthToken foundToken = null;
        try {
            foundToken = authTokenDao.find(request.getAuthtoken());
            if (foundToken == null) {
                throw new ResponseException("invalid authtoken");
            }
            found = personDao.find(request.getPersonID());
        } catch (DatabaseException e) {
            throw new ResponseException(e.toString());
        }
        if (found ==  null) {
            throw new ResponseException("person not found");
        }
        return new PersonResponse(found);
    }
}
