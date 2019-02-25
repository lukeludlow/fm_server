package service;

import data.*;
import message.request.FamilyRequest;
import message.response.FamilyResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.Person;

import java.util.List;

/**
 * family service : web api method /person
 */
public class FamilyService {
    /**
     * returns ALL family members of the current user. the current user is
     * determined from the provided auth authToken.
     */
    @SuppressWarnings("Duplicates")  // TODO FIXME
    public FamilyResponse getFamily(FamilyRequest f) throws ResponseException {
        Database db = new Database();
        AuthTokenDao authtokenDao = new AuthTokenDao(db);
        PersonDao userDao = new PersonDao(db);
        AuthToken found = null;
        String username = null;
        List<Person> people = null;
        try {
            found = authtokenDao.find(f.getAuthtoken());
        } catch (DatabaseException e) {
            throw new ResponseException(e.toString());
        }
        if (found == null) {
            throw new ResponseException("invalid authtoken");
        }
        username = found.getUsername();
        try {
            people = userDao.findMany(username);
        } catch (DatabaseException e) {
            throw new ResponseException(e.toString());
        }
        if (people.size() == 0) {
            throw new ResponseException("user has no connected people");
        }
        return new FamilyResponse(people.toArray(new Person[0]));
    }
}

