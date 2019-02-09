package data;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.AuthToken;

/**
 * authtoken database access object
 */
@Data
@NoArgsConstructor
public class AuthTokenDao {
    /** database connection */
    private Database db;

    /**
     * add token to database
     * @param a new authtoken
     * @return true on success
     */
    public boolean add(AuthToken a) {
        return false;
    }

    /**
     * get token from database
     * @param token token key
     * @return authtoken object. null if not found.
     */
    public AuthToken getByToken(String token) {
        return null;
    }
    /**
     * get all tokens belonging to this user
     * @param username username
     * @return array of authtoken objects. null if not found.
     */
    public AuthToken[] getByUser(String username) {
        return null;
    }

    /**
     * delete token from database
     * @param a authtoken object
     * @return true on success
     */
    public boolean delete(AuthToken a) {
        return false;
    }

    /**
     * delete token from database
     * @param token token key
     * @return true on success
     */
    public boolean deleteByToken(String token) {
        return false;
    }

    /**
     * delete all tokens belonging to this user
     * @param username username
     * @return true on success
     */
    public boolean deleteByUser(String username) {
        return false;
    }
}
