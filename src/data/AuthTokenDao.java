package data;

import model.AuthToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthTokenDao extends Dao<AuthToken> {

    public AuthTokenDao(Database db) {
        super(AuthToken.class, db);
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
    protected AuthToken getObject(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new AuthToken(
                    rs.getString("token"),
                    rs.getString("username")
            );
        }
        return null;
    }

    public void clearAuthTokens() throws DatabaseException {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "delete from auth_token";
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DatabaseException("sql error encountered while clearing authtokens. " + e.getMessage());
        } catch (NullPointerException e) {
            throw new DatabaseException("unable to clear authtokens. dao tried to operate on closed connection. " + e.getMessage());
        }
    }

}
