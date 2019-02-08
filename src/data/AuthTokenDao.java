package data;

import org.sqlite.
import java.sql.*;

import model.AuthToken;

/**
 * authtoken data access object
 * used to connect to database and do stuff with model objects
 */
public class AuthTokenDao {
    private sqlite.DB
    public void add(AuthToken a) {

    }
    public AuthToken getByToken(String token) {
        return null;
    }
    public AuthToken getByUser(String username) {
        return null;
    }
    public void clearAuthTokens() {

    }

}
