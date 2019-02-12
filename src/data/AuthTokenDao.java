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

/**
 * authtoken database access object
 */
public class AuthTokenDao {
    /**
     * database connection
     */
    private data.Database db;

    /**
     * insert authtoken into database
     * @param a new authtoken
     */
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

    /**
     * insert many authtokens into database
     * @param tokens list of new authtokens
     */
    public void insertMany(AuthToken[] tokens) throws DatabaseException {
        // TODO
    }

    /**
     * find authtoken from database
     * @param token unique token
     * @return authtoken object. null if not found.
     */
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

    /**
     * find many tokens in the database
     * @param tokens list of tokens to find
     * @return list of found authtokens. length is same as input length, entries can be null if not found
     */
    public AuthToken[] findManyByToken(String tokens) throws DatabaseException {
        // TODO
        return null;
    }

    /**
     * find all the tokens that belong to a user
     * @param username user that owns authtokens
     * @return list of found authtokens
     */
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

    /**
     * delete token from database
     * @param token unique token to delete
     * @return true if the entry is found and deleted
     */
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

    /**
     * delete many tokens from database
     * @param tokens list of tokens to delete
     * @return number of tokens deleted
     */
    public int deleteManyByToken(String[] tokens) throws DatabaseException {
        // TODO
        return 0;
    }

    /**
     * delete all the tokens that belong to a user
     * @param username user that owns authtokens
     * @return number of tokens deleted
     */
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
