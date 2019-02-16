package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OldPersonDao {
    private Database db;

    public void insert(Person p) throws DatabaseException {
        try {
            this.db.connect();
            PreparedStatement s = prepareInsertStatement(p);
            s.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while inserting person into database");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    public PreparedStatement prepareInsertStatement(Person p) throws SQLException {
        String sql = "insert into people " +
                "(person_id, descendant, firstname, lastname, gender, father_id, mother_id, spouse_id) " +
                "values (?,?,?,?,?,?,?,?)";
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        statement.setString(1, p.getPersonID());
        statement.setString(2, p.getDescendant());
        statement.setString(3, p.getFirstname());
        statement.setString(4, p.getLastname());
        statement.setString(5, p.getGender());
        statement.setString(6, p.getFatherID());
        statement.setString(7, p.getMotherID());
        statement.setString(8, p.getSpouseID());
        return statement;
    }

    public Person find(String personID) throws DatabaseException {
        try {
            this.db.connect();
            String sql = "select * from people where person_id = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, personID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Person p = new Person(
                        rs.getString("person_id"),
                        rs.getString("descendant"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("gender"),
                        rs.getString("father_id"),
                        rs.getString("mother_id"),
                        rs.getString("spouse_id")

                );
                db.closeConnection(true);
                return p;
            } else {
                db.closeConnection(true);
            }
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while finding person");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
        return null;
    }

    public int delete(String personID) throws DatabaseException {
        int deleteCount = 0;
        try {
            this.db.connect();
            String sql = "delete from people where person_id = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, personID);
            deleteCount = statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while deleting person");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
        return deleteCount;
    }

}
