package dao;

import model.AuthToken;
import java.sql.*;

/**
 * authtoken data access object
 * used to connect to database and do stuff with model objects
 */
public class AuthTokenDao {
    /** default constructor */
    public AuthTokenDao() {
    }
    /**
     * generate new token with random int value
     * also inserts it into database
     * @param username user (username) to which this token belongs to
     * @return new AuthToken
     */
    public AuthToken generateAuthToken(String username) {
        return new AuthToken();
    }
}
