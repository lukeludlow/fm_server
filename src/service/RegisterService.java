package service;

import data.*;
import message.request.FillRequest;
import message.response.FillResponse;
import message.request.RegisterRequest;
import message.response.RegisterResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.Person;
import model.User;

import java.util.UUID;

/**
 * register service : web api method /user/register
 */
public class RegisterService {
    /**
     * creates a new user account, generates 4 generations of ancestor data for the new
     * user, logs the user in, and returns an auth authToken.
     */
    public RegisterResponse register(RegisterRequest request) throws ResponseException {
        Database db = new Database();
        UserDao userDao = new UserDao(db);
        PersonDao personDao = new PersonDao(db);
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        String personID = UUID.randomUUID().toString();
        User u = new User (request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstname(), request.getLastname(), request.getGender(), personID);
        Person p = new Person(personID, request.getUsername(), request.getFirstname(), request.getLastname(), request.getGender(), null, null, null);
        AuthToken a = new AuthToken(request.getUsername());
        try {
            userDao.insert(u);
            personDao.insert(p);
            authTokenDao.insert(a);
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
            return null;
        }

        // TODO actually do the fill service
        FillService fillService = new FillService();
        FillRequest fillRequest = new FillRequest(request.getUsername(), 4);
        FillResponse fillResponse = fillService.fill(fillRequest);

        return new RegisterResponse(a.getAuthtoken(), u.getUsername(), p.getPersonID());
    }
}
