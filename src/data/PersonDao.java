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
     * @param p new person
     */
    public void insert(Person p) throws DatabaseException {
        try {
            this.db.connect();
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
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while inserting person into database");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    /**
     * insert many people into database
     * @param people list of new people
     */
    public void insertMany(Person[] people) throws DatabaseException {
        // TODO
    }

    /**
     * find person from database
     * @param personID person's unique id
     * @return person object. null if not found.
     */
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

    /**
     * find many people in the database
     * @param IDs list of people to find
     * @return list of found people. length is the same as input length, entries can be null if not found
     */
    public Person[] findMany(String[] IDs) throws DatabaseException {
        // TODO
        return null;
    }

    /**
     * delete person from database
     * @param personID person's unique id
     * @return true if the entry is found and deleted
     */
    public boolean delete(String personID) throws DatabaseException {
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
        return (deleteCount > 0) ? true : false;
    }

    /**
     * delete many people from database
     * @param IDs list of people to delete
     * @return number of people deleted
     */
    public int deleteMany(String[] IDs) throws DatabaseException {
        // TODO
        return 0;
    }

}
