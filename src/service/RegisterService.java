package service;

import data.*;
import message.request.FillRequest;
import message.request.RegisterRequest;
import message.response.FillResponse;
import message.response.RegisterResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.User;

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
        String personID = ""; // personID is created during the fill service
        User u = new User (request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstname(), request.getLastname(), request.getGender(), personID);
        AuthToken a = new AuthToken(request.getUsername());
        User found;
        try {
            found = userDao.find(request.getUsername());
            if (found != null) {
                throw new ResponseException("username already taken by another user.");
            }
            userDao.insert(u);
            authTokenDao.insert(a);
        } catch (DatabaseException e) {
            throw new ResponseException(e.getMessage());
        }

        FillService fillService = new FillService();
        FillRequest fillRequest = new FillRequest(request.getUsername(), 4); // generate 4 generations of ancestor data for the new user
        FillResponse fillResponse = fillService.fill(fillRequest);

        // get user's person ID. we gotta go back and get it bc fill generates a brand new person object
        try {
            found = userDao.find(request.getUsername());
        } catch (DatabaseException e) {
            throw new ResponseException(e.getMessage());
        }
        personID = found.getPersonID();
        return new RegisterResponse(a.getAuthtoken(), u.getUsername(), personID);
    }
}
