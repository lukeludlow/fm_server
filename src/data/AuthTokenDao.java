package data;

import model.AuthToken;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTokenDao extends Dao<AuthToken> {
    public AuthTokenDao() {
        super(AuthToken.class);
        this.setSqlStatements();
    }
    public AuthTokenDao(Database db) {
        super(AuthToken.class);
        this.db = db;
        this.setSqlStatements();
    }
    private void setSqlStatements() {
        this.insertSql = "insert into auth_token " +
                "(token, username) " +
                "values (?,?)";
        this.findSql = "select * from auth_token where token = ?";
        this.findManySql = "select * from auth_token where username = ?";
        this.deleteSql = "delete from auth_token where token = ?";
        this.deleteManySql = "delete from auth_token where username = ?";
    }
    @Override
    public AuthToken getObject(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new AuthToken(
                    rs.getString("token"),
                    rs.getString("username")
            );
        }
        return null;
    }
}
