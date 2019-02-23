package service;

import data.*;
import message.request.FillRequest;
import message.response.FillResponse;
import message.request.RegisterRequest;
import message.response.RegisterResponse;
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
    public RegisterResponse register(RegisterRequest request) {
        Database db = new Database();
        UserDao userDao = new UserDao(db);
        PersonDao personDao = new PersonDao(db);
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        String personID = UUID.randomUUID().toString();
        User u = new User (request.getUserName(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender(), personID);
        Person p = new Person(personID, request.getUserName(), request.getFirstName(), request.getLastName(), request.getGender(), null, null, null);
        AuthToken a = new AuthToken(request.getUserName());
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
        FillRequest fillRequest = new FillRequest(request.getUserName(), 4);
        FillResponse fillResponse = fillService.fill(fillRequest);

        return new RegisterResponse(a.getAuthToken(), u.getUserName(), p.getPersonID());
    }
}
