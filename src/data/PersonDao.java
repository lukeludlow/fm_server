package data;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.Person;

/**
 * person database access object
 */
@Data
@NoArgsConstructor
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
    public boolean add(Person p) {
        return false;
    }

    /**
     * find person from database
     *
     * @param personID person's unique id
     * @return person object. null if not found.
     */
    public Person get(String personID) {
        return null;
    }

    /**
     * delete person from database
     *
     * @param personID person's unique id
     * @return true on success
     */
    public boolean delete(String personID) {
        return false;
    }

    /**
     * delete person from database
     *
     * @param p person object
     * @return true on success
     */
    public boolean delete(Person p) {
        return false;
    }
}
