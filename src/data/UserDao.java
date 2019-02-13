package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {
    private Database db;

    public void insert(User u) throws DatabaseException {
        try {
            this.db.connect();
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
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while inserting user into database");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    public void insertMany(User[] users) throws DatabaseException {
        // TODO
    }

    public User find(String username) throws DatabaseException {
        try {
            this.db.connect();
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
                db.closeConnection(true);
                return u;
            } else {
                db.closeConnection(true);
            }
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while finding user");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
        return null;
    }

    public User[] findMany(String[] usernames) throws DatabaseException {
        // TODO
        return null;
    }

    public boolean delete(String username) throws DatabaseException {
        int deleteCount = 0;
        try {
            this.db.connect();
            String sql = "delete from user where username = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            deleteCount = statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while deleting user");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
        return (deleteCount > 0) ? true : false;
    }

    public int deleteMany(String[] usernames) throws DatabaseException {
        // TODO
        return 0;
    }
}
