package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.PersonDao;
import message.request.FamilyRequest;
import message.response.FamilyResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.Person;

import java.util.ArrayList;

/**
 * family service : web api method /person
 */
public class FamilyService {

    private Database db;
    private AuthTokenDao authTokenDao;
    private PersonDao userDao;
    private AuthToken foundToken;
    private String username;
    private ArrayList<Person> people;

    public FamilyService() {
        db = new Database();
        authTokenDao = new AuthTokenDao(db);
        userDao = new PersonDao(db);
        foundToken = null;
        username = null;
        people = null;
    }

    /**
     * returns ALL family members of the current user. the current user is
     * determined from the provided auth authToken.
     */
    public FamilyResponse getFamily(FamilyRequest request) throws ResponseException {
        find(request);
        checkValidity();
        return new FamilyResponse(people.toArray(new Person[0]));
    }

    // TODO fix duplicates
    private void find(FamilyRequest request) throws ResponseException {
        try {
            db.connect();
            foundToken = authTokenDao.find(request.getAuthToken());
            username = getTokenUser(foundToken);
            people = userDao.findMany(username);
            db.closeResponseConnection(true);
        } catch (DatabaseException | ResponseException e) {
            db.closeResponseConnection(false);
            throw new ResponseException(e);
        }
    }

    private String getTokenUser(AuthToken a) throws ResponseException {
        if (a == null) {
            throw new ResponseException("invalid authtoken");
        }
        return a.getUserName();
    }

    private void checkValidity() throws ResponseException {
        if (foundToken == null) {
            throw new ResponseException("invalid authtoken");
        }
        if (people.size() == 0) {
            throw new ResponseException("user has no connected people");
        }
    }


}

