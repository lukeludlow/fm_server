package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.UserDao;
import message.request.LoginRequest;
import message.response.LoginResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.User;

/**
 * login service : web api method /user/login
 */
public class LoginService {

    private Database db;
    private UserDao userDao;
    private AuthTokenDao authTokenDao;
    private User user;
    private AuthToken authtoken;
    private LoginResponse response;

    public LoginService() {
        db = new Database();
        userDao = new UserDao(db);
        authTokenDao = new AuthTokenDao(db);
        user = null;
        authtoken = null;
        response = null;
    }

    public LoginResponse login(LoginRequest request) throws ResponseException {
        find(request);
        checkValidity(request);
        response = new LoginResponse();
        response.setUserName(user.getUserName());
        response.setPersonID(user.getPersonID());
        response.setAuthToken(createAuthToken(user.getUserName()));
        return response;
    }

    private void find(LoginRequest request) throws ResponseException {
        try {
            db.connect();
            user = userDao.find(request.getUserName());
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw new ResponseException(e);
        }
    }

    private String createAuthToken(String username) throws ResponseException {
        try {
            db.connect();
            authtoken = new AuthToken(username);
            authTokenDao.insert(authtoken);
            db.closeResponseConnection(true);
            return authtoken.getAuthToken();
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw new ResponseException(e);
        }
    }

    private void checkValidity(LoginRequest request) throws ResponseException {
        if (user == null) {
            throw new ResponseException("user not found");
        }
        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseException("incorrect password");
        }
    }

}
