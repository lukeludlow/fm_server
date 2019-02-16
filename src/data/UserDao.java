package data;

import model.User;

public class UserDao extends Dao<User> {
    public UserDao() {
        super(User.class);
        this.setSqlStatements();
    }
    public UserDao(Database db) {
        super(User.class, db);
        this.setSqlStatements();
    }
    private void setSqlStatements() {
        this.insertSql = "insert into user " +
                "(username, password, email, firstname, lastname, gender, person_id) " +
                "values (?,?,?,?,?,?,?)";
        this.findSql = "select * from user where username = ?;";
        this.deleteSql = "delete from user where username = ?;";
    }
}
