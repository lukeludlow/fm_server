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

    private Database db;
    private UserDao userDao;
    private AuthTokenDao authTokenDao;
    private String personID; // personID is created during the fill service
    private User user;
    private AuthToken authtoken;
    private User foundUser;

    public RegisterService() {
        db = new Database();
        userDao = new UserDao(db);
        authTokenDao = new AuthTokenDao(db);
        personID = ""; // personID is created during the fill service
        user = null;
        authtoken = null;
        foundUser = null;
    }

    /**
     * creates a new user account, generates 4 generations of ancestor data for the new
     * user, logs the user in, and returns an auth authToken.
     */
    public RegisterResponse register(RegisterRequest request) throws ResponseException {
        registerHelper(request);
        generateAncestorData(request.getUserName());
        return new RegisterResponse(authtoken.getAuthToken(), user.getUserName(), findNewPersonID(user.getUserName()));
    }

    private String findNewPersonID(String username) throws ResponseException {
        // get user's person ID. we gotta go back and get it bc fill generates a brand new person object
        User found;
        try {
            db.connect();
            found = userDao.find(username);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw new ResponseException(e);
        }
        return found.getPersonID();
    }

    private void generateAncestorData(String username) throws ResponseException {
        final int DEFAULT_GENERATIONS = 4;
        FillService fillService = new FillService();
        FillRequest fillRequest = new FillRequest(username, DEFAULT_GENERATIONS); // generate 4 generations of ancestor data for the new user
        FillResponse fillResponse = fillService.fill(fillRequest);
    }

    private void registerHelper(RegisterRequest request) throws ResponseException {
        try {
            db.connect();
            checkIfUserExists(request.getUserName());
            createAndInsertUser(request);
            createAndInsertToken(request);
            db.closeResponseConnection(true);
        } catch (DatabaseException | ResponseException e) {
            db.closeResponseConnection(false);
            throw new ResponseException(e);
        }
    }

    private void checkIfUserExists(String username) throws ResponseException, DatabaseException {
        foundUser = userDao.find(username);
        if (foundUser != null) {
            throw new ResponseException("username already taken by another user.");
        }
    }

    private void createAndInsertUser(RegisterRequest request) throws DatabaseException {
        user = new User(request.getUserName(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender(), personID);
        userDao.insert(user);
    }

    private void createAndInsertToken(RegisterRequest request) throws DatabaseException {
        authtoken = new AuthToken(request.getUserName());
        authTokenDao.insert(authtoken);
    }

}
