package data;

import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends Dao<User> {
    public UserDao() {
        super(User.class);
        this.setSqlStatements();
    }
    public UserDao(Database db) {
        super(User.class);
        this.db = db;
        this.setSqlStatements();
    }
    private void setSqlStatements() {
        this.insertSql = "insert into user " +
                "(username, password, email, firstname, lastname, gender, person_id) " +
                "values (?,?,?,?,?,?,?)";
        this.findSql = "select * from user where username = ?";
        this.deleteSql = "delete from user where username = ?";
    }
    @Override
    public User getObject(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("gender"),
                    rs.getString("person_id")
            );
        }
        return null;
    }
}
