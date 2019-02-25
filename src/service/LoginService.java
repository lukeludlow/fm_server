package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.UserDao;
import message.response.ResponseException;
import message.request.LoginRequest;
import message.response.LoginResponse;
import model.AuthToken;
import model.User;

/**
 * login service : web api method /user/login
 */
public class LoginService {
    public LoginResponse login(LoginRequest request) throws ResponseException {
        Database db = new Database();
        UserDao userDao = new UserDao(db);
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        User found;
        AuthToken auth;
        LoginResponse response;
        try {
            found = userDao.find(request.getUsername());
        } catch (DatabaseException e) {
            throw new ResponseException(e.toString());
        }
        if (found == null) {
            throw new ResponseException("user not found");
        }
        if (!found.getPassword().equals(request.getPassword())) {
            throw new ResponseException("incorrect password");
        }
        response = new LoginResponse(found);
        try {
            auth = new AuthToken(found.getUsername());
            authTokenDao.insert(auth);
            response.setAuthtoken(auth.getAuthtoken());
        } catch (DatabaseException e) {
            throw new ResponseException(e.toString());
        }
        return response;
    }
}
