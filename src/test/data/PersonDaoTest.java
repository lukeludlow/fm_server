package test.data;

import data.Database;
import data.DatabaseException;
import data.PersonDao;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class PersonDaoTest {
    Person luke;
    Person findLuke;
    Database db;
    PersonDao pDao;

    @BeforeEach
    public void setUp() throws Exception {
        luke = new Person("1", "lukeludlow", "luke", "ludlow", "m", "none", "none", "none");
        findLuke = null;
        db = new Database();
        pDao = new PersonDao(db);
    }
    @AfterEach
    public void tearDown() throws Exception {
        db.clearAll();
    }

    @Test
    @DisplayName("insert success")
    public void testInsert() throws Exception {
        try {
            pDao.insert(luke);
            findLuke = pDao.find("1");
        } catch (DatabaseException e) {
            System.err.println(e);
        }
        assertNotNull(findLuke);
        assertEquals(luke, findLuke);
    }

    @Test
    @DisplayName("insert fail")
    @SuppressWarnings("Duplicates")
    public void testInsertFail() throws Exception {
        boolean insertSuccess = true;
        try {
            pDao.insert(luke);
            // person must be unique, so adding it again should fail
            pDao.insert(luke);
        } catch (DatabaseException e) {
            insertSuccess = false;
            // this is supposed to fail so don't print the exception
            //System.err.println(e);
        }
        assertFalse(insertSuccess);
        try {
            // insert commits transaction immediately, so the original luke will still be found
            findLuke = pDao.find(luke.getPersonID());
        } catch (DatabaseException e) {
            System.err.println(e);
        }
        assertEquals(luke, findLuke);
    }

    @Test
    @DisplayName("find success")
    public void testFind() throws Exception {
        Person p2= new Person("2", "p2", "p", "2", "m", "none", "none", "none");
        Person p3= new Person("3", "p3", "p", "3", "m", "none", "none", "none");
        Person findp2 = null;
        Person findp3 = null;
        try {
            pDao.insert(luke);
            findLuke = pDao.find("1");
            pDao.insert(p2);
            findp2 = pDao.find(p2.getPersonID());
            pDao.insert(p3);
            findp3 = pDao.find(p3.getPersonID());
        } catch (DatabaseException e) {
            System.out.println(e);
        }
        assertNotNull(findLuke);
        assertNotNull(p2);
        assertNotNull(p3);
        assertEquals(luke, findLuke);
        assertEquals(p2, findp2);
        assertEquals(p3, findp3);
    }

    @Test
    @DisplayName("find fail")
    public void testFindFail() throws Exception {
        try {
            findLuke = pDao.find("1");
        } catch (DatabaseException e) {
            System.err.println(e);
        }
        assertNull(findLuke);
    }

    @Test
    @DisplayName("delete success")
    public void testDelete() throws Exception {
        boolean deleteSuccess = false;
        try {
            pDao.insert(luke);
            deleteSuccess = pDao.delete("1");
            findLuke = pDao.find("1");
        } catch (DatabaseException e) {
            System.err.println(e);
        }
        assertNull(findLuke);
        assertTrue(deleteSuccess);
    }

    @Test
    @DisplayName("delete fail")
    public void testDeleteFail() throws Exception {
        boolean deleteSuccess = false;
        try {
            deleteSuccess = pDao.delete("1");
        } catch (DatabaseException e) {
            System.err.println(e);
        }
        assertFalse(deleteSuccess);
    }

}
