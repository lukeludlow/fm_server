package data;

import model.AuthToken;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                "(authToken, userName) " +
                "values (?,?)";
        this.findSql = "select * from auth_token where authToken = ?";
        this.findManySql = "select * from auth_token where userName = ?";
        this.deleteSql = "delete from auth_token where authToken = ?";
        this.deleteManySql = "delete from auth_token where userName = ?";
    }
    @Override
    public AuthToken getObject(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new AuthToken(
                    rs.getString("authToken"),
                    rs.getString("userName")
            );
        }
        return null;
    }
}
