package service;

import data.*;
import message.request.FamilyRequest;
import message.response.FamilyResponse;
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
    public FamilyResponse getFamily(FamilyRequest f) {
        Database db = new Database();
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        AuthToken found = null;
        try {
            found = authTokenDao.find(f.getAuthtoken());
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
            return null;
        }
        if (found == null) {
            return null;
        }
        String username = found.getUsername();
        PersonDao userDao = new PersonDao(db);
        List<Person> people = null;
        try {
            people = userDao.findMany(username);
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
            return null;
        }
        if (people.size() == 0) {
            return null;
        }
        return new FamilyResponse(people.toArray(new Person[0]));
    }
}

