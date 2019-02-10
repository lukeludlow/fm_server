package test.data;

import data.Database;
import data.DatabaseException;
import data.UserDao;
import model.User;
import java.sql.Connection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class UserDaoTest {
    Database db;
    User insertLuke;
    User anotherUser;
    UserDao uDao;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        uDao = new UserDao(db);
        insertLuke = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
        anotherUser = new User("abc", "password", "abc@abc.com","a","c","f","321");
    }
    @AfterEach
    public void tearDown() throws Exception {
        db.clearAll();
    }

    @Test
    @DisplayName("insert success")
    public void testInsert() throws Exception {
        User findLuke = null;
        try {
            db.connect();
            uDao.insert(insertLuke);
            findLuke = uDao.find(insertLuke.getUsername());
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            db.closeConnection(false);
        }
        assertNotNull(findLuke);
        assertEquals(insertLuke, findLuke);
    }

    @Test
    @DisplayName("insert 2 users")
    public void testInsert2() throws Exception {
        User findLuke = null;
        User findAnother = null;
        try {
            db.connect();
            uDao.insert(insertLuke);
            uDao.insert(anotherUser);
            findLuke = uDao.find(insertLuke.getUsername());
            findAnother = uDao.find(anotherUser.getUsername());
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            db.closeConnection(false);
        }
        assertNotNull(findLuke);
        assertNotNull(findAnother);
        assertEquals(insertLuke, findLuke);
        assertEquals(anotherUser, findAnother);
    }

    @Test
    @DisplayName("insert fail")
    public void testInsertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection connection = db.connect();
            uDao = new UserDao(db);
            uDao.insert(insertLuke);
            // username must be unique, so adding it again should fail
            uDao.insert(insertLuke);
            // shouldn't even hit this block
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        // check that we really did fail and hit the catch block
        assertFalse(didItWork);

        // bc database encountered an error, both changes should have been rolled back
        // so double check that it was not inserted
        User getUser = insertLuke;
        try {
            Connection connection = db.connect();
            uDao = new UserDao(db);
            // getUser should be now be null bc it wasn't found
            getUser = uDao.find(insertLuke.getUsername());
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            db.closeConnection(false);
        }
        assertNull(getUser);
    }


    @Test
    @DisplayName("find success")
    public void testFind() throws Exception {
        User findLuke = null;
        try {
            db.connect();
            uDao.insert(insertLuke);
            findLuke = uDao.find("lukeludlow");
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            db.closeConnection(false);
        }
        assertNotNull(findLuke);
        assertEquals(insertLuke, findLuke);
    }

    @Test
    @DisplayName("find fail")
    public void testFindFail() throws Exception {
        User findLuke = null;
        try {
            db.connect();
            findLuke = uDao.find("lukeludlow");
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            db.closeConnection(false);
        }
        assertNull(findLuke);
    }

    @Test
    @DisplayName("find fail 2")
    public void testFindFail2() throws Exception {
        User findLuke = null;
        try {
            db.connect();
            uDao.insert(insertLuke);
            findLuke = uDao.find("lukeludlow");
            db.closeConnection(true);
            db.clearAll();
            db.connect();
            findLuke = uDao.find("lukeludlow");
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            db.closeConnection(false);
        }
        assertNull(findLuke);
    }

    @Test
    @DisplayName("delete success")
    public void testDelete() throws Exception {
        User findLuke = null;
        boolean deleteSuccess = false;
        try {
            db.connect();
            uDao.insert(insertLuke);
            deleteSuccess = uDao.delete("lukeludlow");
            findLuke = uDao.find("lukeludlow");
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            db.closeConnection(false);
        }
        assertNull(findLuke);
        assertTrue(deleteSuccess);
    }

    @Test
    @DisplayName("delete fail")
    public void testDeleteFail() throws Exception {
        boolean deleteSuccess = false;
        try {
            db.connect();
            deleteSuccess = uDao.delete("lukeludlow");
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            db.closeConnection(false);
        }
        assertFalse(deleteSuccess);
    }



}
