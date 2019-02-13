package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.AuthToken;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthTokenDao {
    private data.Database db;

    public void insert(AuthToken a) throws DatabaseException {
        try {
            this.db.connect();
            String sql = "insert into auth_token " +
                    "(token, username) " +
                    "values (?,?)";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, a.getToken());
            statement.setString(2, a.getUsername());
            statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException ex) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while inserting authtoken into database");
        } catch (DatabaseException ex) {
            db.closeConnection(false);
            throw ex;
        }
    }

    public void insertMany(AuthToken[] tokens) throws DatabaseException {
        // TODO
    }

    public AuthToken findByToken(String token) throws DatabaseException {
        try {
            this.db.connect();
            String sql = "select * from auth_token where token = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, token);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                AuthToken a = new AuthToken(
                        rs.getString("token"),
                        rs.getString("username")
                );
                db.closeConnection(true);
                return a;
            } else {
                db.closeConnection(true);
            }
        } catch (SQLException ex) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while finding authtoken by token in database");
        } catch (DatabaseException ex) {
            db.closeConnection(false);
            throw ex;
        }
        return null;
    }

    public AuthToken[] findManyByToken(String tokens) throws DatabaseException {
        // TODO
        return null;
    }

    public AuthToken[] findManyByUser(String username) throws DatabaseException {
        try {
            this.db.connect();
            String sql = "select * from auth_token where username = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            ArrayList<AuthToken> tokens = new ArrayList<>();
            while (rs.next()) {
                AuthToken a = new AuthToken(
                        rs.getString("token"),
                        rs.getString("username")
                );
                tokens.add(a);
            }
            db.closeConnection(true);
            AuthToken[] response = new AuthToken[tokens.size()];
            response = tokens.toArray(response);
            return response;
        } catch (SQLException ex) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while finding authtoken by token");
        } catch (DatabaseException ex) {
            db.closeConnection(false);
            throw ex;
        }
    }

    public boolean deleteByToken(String token) throws DatabaseException {
        int deleteCount = 0;
        try {
            this.db.connect();
            String sql = "delete from auth_token where token = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, token);
            deleteCount = statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while deleting token");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
        return (deleteCount > 0) ? true : false;
    }

    public int deleteManyByToken(String[] tokens) throws DatabaseException {
        // TODO
        return 0;
    }

    public int deleteManyByUser(String username) throws DatabaseException {
        int deleteCount = 0;
        try {
            this.db.connect();
            String sql = "delete from auth_token where username = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            deleteCount = statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while deleting token");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
        return deleteCount;
    }

}
