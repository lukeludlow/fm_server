package data;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserDao {
    /**
     * database connection
     */
    private Database db;

    /**
     * insert user into database
     *
     * @param u new user
     * @return true on success
     */
    public boolean insert(User u) throws DatabaseException {
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
        } catch (SQLException e) {
            commit = false;
            throw new DatabaseException("sql error encountered while inserting user into database");
        }
        return commit;
    }

    /**
     * find user from database
     *
     * @param username user account's username
     * @return user object. null if not found.
     */
    public User find(String username) throws DatabaseException {
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("sql error encountered while finding user");
        }
        return null;
    }

    /**
     * delete user from database
     *
     * @param username user account's username
     * @return true if the entry is found and deleted
     */
    public boolean delete(String username) throws DatabaseException {
        int deleteCount = 0;
        try {
            String sql = "delete from user where username = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            deleteCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("sql error encountered while deleting user");
        }
        return (deleteCount > 0) ? true : false;
    }
}
