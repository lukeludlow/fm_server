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
        System.out.println("in login service");
        Database db = new Database();
        UserDao userDao = new UserDao(db);
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        User found;
        AuthToken auth;
        LoginResponse response;
        try {
            found = userDao.find(request.getUserName());
            if (found != null && found.getPassword().equals(request.getPassword())) {
                response = new LoginResponse(found);
                auth = new AuthToken(found.getUserName());
                authTokenDao.insert(auth);
                response.setAuthToken(auth.getAuthToken());
                return response;
            }
        } catch (DatabaseException ex) {
            throw new ResponseException(ex.toString());
        }
        throw new ResponseException("incorrect username or password");
    }
}
