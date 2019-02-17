package data;

import model.Person;
import model.Person;
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
    }

    @Test
    @DisplayName("insert person")
    void testInsert() throws Exception {
        personDao.insert(luke);
        findLuke = personDao.find(luke.getPersonID());
        assertNotNull(findLuke);
        assertEquals(luke, findLuke);
    }
    @Test
    @DisplayName("insert person fail (insert same person twice)")
    void testInsertFail() {
        assertThrows(DatabaseException.class,
                () -> {
                    personDao.insert(luke);
                    personDao.insert(luke);
                });
    }
    @Test
    @DisplayName("insert person fail (empty primary key)")
    void testInsertFail2() {
        assertThrows(DatabaseException.class,
                () -> {
                    luke.setPersonID(null);
                    personDao.insert(luke);
                });
    }

    @Test
    @DisplayName("find success")
    void testFind() throws Exception {
        personDao.insert(luke);
        findLuke = personDao.find(luke.getPersonID());
        assertNotNull(findLuke);
        assertEquals(luke, findLuke);
    }
    @Test
    @DisplayName("find success (find multiple entries multiple times)")
    void testFind2() throws Exception {
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
    }
    @Test
    @DisplayName("find fail (person has not been inserted)")
    void testFindFail() throws Exception {
        findLuke = personDao.find(luke.getPersonID());
        assertNull(findLuke);
    }
    @Test
    @DisplayName("find fail (wrong personID)")
    void testFindFail2() throws Exception {
        personDao.insert(luke);
        findLuke = personDao.find("xX_person_Xx");
        assertNull(findLuke);
    }
    @Test
    @DisplayName("delete success")
    void testDelete() throws Exception {
        personDao.insert(luke);
        findLuke = personDao.find(luke.getPersonID());
        assertNotNull(findLuke);
        int deleteCount = personDao.delete(luke.getPersonID());
        findLuke = personDao.find(luke.getPersonID());
        assertNull(findLuke);
        assertEquals(1, deleteCount);
    }
    @Test
    @DisplayName("delete fail (person does not exist")
    void testDeleteFail() throws Exception {
        personDao.delete(luke.getPersonID());
    }
    @Test
    @DisplayName("find many (all people belonging to a descendant/user)")
    void testFindMany() throws Exception {
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
    }
    @Test
    @DisplayName("find many fail")
    void testFindManyFail() throws Exception {
        List<Person> people = personDao.findMany(luke.getDescendant());
        assertEquals(0, people.size());
    }
    @Test
    @DisplayName("delete many (delete all people belonging to a descendant/user)")
    void testDeleteMany() throws Exception {
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
    }
}