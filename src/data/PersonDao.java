package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * person database access object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDao {
    /**
     * database connection
     */
    private Database db;

    /**
     * insert person to database
     *
     * @param p new person
     * @return true on success
     */
    public boolean insert(Person p) throws DatabaseException {
        boolean commit = true;
        try {
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
            statement.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DatabaseException("sql error encountered while inserting person into database");
        }
        return commit;
    }

    /**
     * find person from database
     *
     * @param personID person's unique id
     * @return person object. null if not found.
     */
    public Person find(String personID) throws DatabaseException {
        try {
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
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("sql error encountered while finding person");
        }
        return null;
    }

    /**
     * delete person from database
     *
     * @param personID person's unique id
     * @return true on success
     */
    public boolean delete(String personID) throws DatabaseException {
        int deleteCount = 0;
        try {
            String sql = "delete from people where person_id = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, personID);
            deleteCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("sql error encountered while deleting person");
        }
        return (deleteCount > 0) ? true : false;
    }
}
