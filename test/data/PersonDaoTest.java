package data;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {

    private Database db;
    private PersonDao personDao;
    private Person luke;
    private Person anotherPerson;
    private Person findLuke;
    private Person findAnother;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        personDao = new PersonDao(db);
        luke = new Person("1", "lukeludlow", "luke", "ludlow", "m", "none", "none", "none");
        anotherPerson = new Person("2", "p2", "p", "2", "m", "none", "none", "none");
        findLuke = null;
        findAnother = null;
    }

    @AfterEach
    void tearDown() throws Exception {
        db.clearAll();
        if (db.getConnection() != null) {
            db.closeConnection(false);
        }
    }

    @Test
    @DisplayName("insert person")
    void testInsert() throws Exception {
        db.connect();
        try {
            personDao.insert(luke);
            findLuke = personDao.find(luke.getPersonID());
            db.closeConnection(true);
            assertNotNull(findLuke);
            assertEquals(luke, findLuke);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("insert person fail (insert same person twice)")
    void testInsertFail() throws Exception {
        db.connect();
        assertThrows(DatabaseException.class,
                () -> {
                    personDao.insert(luke);
                    personDao.insert(luke);
                });
        db.closeConnection(false);
    }

    @Test
    @DisplayName("insert person fail (empty primary key)")
    void testInsertFail2() throws Exception {
        db.connect();
        assertThrows(DatabaseException.class,
                () -> {
                    luke.setPersonID(null);
                    personDao.insert(luke);
                });
        db.closeConnection(false);
    }

    @Test
    @DisplayName("find success")
    void testFind() throws Exception {
        db.connect();
        try {
            personDao.insert(luke);
            findLuke = personDao.find(luke.getPersonID());
            db.closeConnection(true);
            assertNotNull(findLuke);
            assertEquals(luke, findLuke);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("find success (find multiple entries multiple times)")
    void testFind2() throws Exception {
        db.connect();
        try {
            personDao.insert(luke);
            personDao.insert(anotherPerson);
            findLuke = personDao.find(luke.getPersonID());
            findAnother = personDao.find(anotherPerson.getPersonID());
            assertNotNull(findLuke);
            assertNotNull(anotherPerson);
            assertEquals(luke, findLuke);
            assertEquals(anotherPerson, findAnother);
            findLuke = personDao.find(luke.getPersonID());
            findAnother = personDao.find(anotherPerson.getPersonID());
            assertNotNull(findLuke);
            assertNotNull(anotherPerson);
            assertEquals(luke, findLuke);
            assertEquals(anotherPerson, findAnother);
            db.closeConnection(true);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("find fail (person has not been inserted)")
    void testFindFail() throws Exception {
        db.connect();
        try {
            findLuke = personDao.find(luke.getPersonID());
            db.closeConnection(true);
            assertNull(findLuke);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("find fail (wrong personID)")
    void testFindFail2() throws Exception {
        db.connect();
        try {
            personDao.insert(luke);
            findLuke = personDao.find("xX_person_Xx");
            db.closeConnection(true);
            assertNull(findLuke);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("delete success")
    void testDelete() throws Exception {
        db.connect();
        try {
            personDao.insert(luke);
            findLuke = personDao.find(luke.getPersonID());
            assertNotNull(findLuke);
            int deleteCount = personDao.delete(luke.getPersonID());
            findLuke = personDao.find(luke.getPersonID());
            assertNull(findLuke);
            assertEquals(1, deleteCount);
            db.closeConnection(true);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("delete fail (person does not exist")
    void testDeleteFail() throws Exception {
        db.connect();
        try {
            int deleteCount = personDao.delete(luke.getPersonID());
            db.closeConnection(true);
            assertEquals(0, deleteCount);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("find many (all people belonging to a descendant/user)")
    void testFindMany() throws Exception {
        db.connect();
        try {
            Person p2 = new Person("2", "lukeludlow", "p", "2", "m", "none", "none", "none");
            Person p3 = new Person("3", "lukeludlow", "p", "3", "m", "none", "none", "none");
            personDao.insert(luke);
            personDao.insert(p2);
            personDao.insert(p3);
            List<Person> people = personDao.findMany(luke.getDescendant());
            assertEquals(3, people.size());
            assertEquals(luke, people.get(0));
            assertEquals(p2, people.get(1));
            assertEquals(p3, people.get(2));
            db.closeConnection(true);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("find many fail")
    void testFindManyFail() throws Exception {
        db.connect();
        try {
            List<Person> people = personDao.findMany(luke.getDescendant());
            assertEquals(0, people.size());
            db.closeConnection(false);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("delete many (delete all people belonging to a descendant/user)")
    void testDeleteMany() throws Exception {
        db.connect();
        try {
            Person p2 = new Person("2", "lukeludlow", "p", "2", "m", "none", "none", "none");
            Person p3 = new Person("3", "lukeludlow", "p", "3", "m", "none", "none", "none");
            personDao.insert(luke);
            personDao.insert(p2);
            personDao.insert(p3);
            List<Person> people = personDao.findMany(luke.getDescendant());
            assertEquals(3, people.size());
            assertEquals(luke, people.get(0));
            assertEquals(p2, people.get(1));
            assertEquals(p3, people.get(2));
            int deleteCount = personDao.deleteMany(luke.getDescendant());
            people = personDao.findMany(luke.getDescendant());
            assertEquals(3, deleteCount);
            assertEquals(0, people.size());
            db.closeConnection(true);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

}