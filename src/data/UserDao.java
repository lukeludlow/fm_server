package data;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public boolean add(User u) throws DatabaseException {
        boolean commit = true;
        try {
            String sql = "insert into user " +
                    "(username, password, email, firstname, lastname, gender, person_id) " +
                    "values (?,?,?,?,?,?,?)";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, u.getUsername());
            statement.setString(2, u.getPassword());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getFirstname());
            statement.setString(5, u.getLastname());
            statement.setString(6, u.getGender());
            statement.setString(7, u.getPersonID());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            commit = false;
            throw new DatabaseException("sql error encountered while inserting user into database");
        }
        return commit;
    }

    /**
     * get user from database
     * @param username user account's username
     * @return user object. null if not found.
     */
    public User get(String username) throws DatabaseException {
        try {
            String sql = "select * from user where username = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User u = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("gender"),
                        rs.getString("person_id")
                );
                return u;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("sql error encountered while getting event");
        }
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
