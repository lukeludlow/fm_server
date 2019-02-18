package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.UserDao;
import message.ErrorResponse;
import message.LoginRequest;
import message.LoginResponse;
import model.AuthToken;
import model.User;

/**
 * login service : web api method /user/login
 */
public class LoginService {
    // TODO return error response, not just null
    public LoginResponse login(LoginRequest request) {
        Database db = new Database();
        UserDao userDao = new UserDao(db);
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        User found = null;
        try {
            found = userDao.find(request.getUserName());
            if (found != null && found.getPassword().equals(request.getPassword())) {
                LoginResponse response = new LoginResponse(found);
                AuthToken auth = new AuthToken(found.getUserName());
                authTokenDao.insert(auth);
                response.setAuthToken(auth.getAuthToken());
                return response;
            }
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }
}
