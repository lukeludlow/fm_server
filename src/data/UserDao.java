package data;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;

/**
 * user database access object
 */
@Data
@NoArgsConstructor
public class UserDao {
    /** database connection */
    private Database db;

    /**
     * add user to database
     * @param u new user
     * @return true on success
     */
    public boolean add(User u) {
        return false;
    }

    /**
     * get user from database
     * @param username user account's username
     * @return user object. null if not found.
     */
    public User get(String username) {
        return null;
    }

    /**
     * delete user from database
     * @param username user account's username
     * @return true on success
     */
    public boolean delete(String username) {
        return false;
    }

    /**
     * delete user from database
     * @param u user object
     * @return true on success
     */
    public boolean delete(User u) {
        return false;
    }
}
