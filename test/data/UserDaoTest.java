package data;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private Database db;
    private UserDao userDao;
    private User luke;
    private User anotherUser;
    private User findLuke;
    private User findAnother;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        userDao = new UserDao(db);
        luke = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
        anotherUser = new User("abc", "password", "abc@abc.com","a","c","f","321");
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
    @DisplayName("insert user")
    void testInsert() throws Exception {
        db.connect();
        try {
            userDao.insert(luke);
            findLuke = userDao.find(luke.getUsername());
            db.closeConnection(true);
            assertNotNull(findLuke);
            assertEquals(luke, findLuke);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("insert user fail (insert same user twice)")
    void testInsertFail() throws Exception {
        db.connect();
        assertThrows(DatabaseException.class,
                () -> {
                    userDao.insert(luke);
                    userDao.insert(luke);
                });
        db.closeConnection(false);
    }

    @Test
    @DisplayName("insert user fail (empty primary key)")
    void testInsertFail2() throws Exception {
        db.connect();
        assertThrows(DatabaseException.class,
                () -> {
                    luke.setUsername(null);
                    userDao.insert(luke);
                });
        db.closeConnection(false);
    }

    @Test
    @DisplayName("find success")
    void testFind() throws Exception {
        db.connect();
        try {
            userDao.insert(luke);
            findLuke = userDao.find(luke.getUsername());
            db.closeConnection(true);
            assertNotNull(findLuke);
            assertEquals(luke, findLuke);
        } catch (DatabaseException e ) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("find success (find multiple entries multiple times)")
    void testFind2() throws Exception {
        db.connect();
        try {
            userDao.insert(luke);
            userDao.insert(anotherUser);
            findLuke = userDao.find(luke.getUsername());
            findAnother = userDao.find(anotherUser.getUsername());
            assertNotNull(findLuke);
            assertNotNull(anotherUser);
            assertEquals(luke, findLuke);
            assertEquals(anotherUser, findAnother);
            findLuke = userDao.find(luke.getUsername());
            findAnother = userDao.find(anotherUser.getUsername());
            assertNotNull(findLuke);
            assertNotNull(anotherUser);
            assertEquals(luke, findLuke);
            assertEquals(anotherUser, findAnother);
            db.closeConnection(true);
        } catch (DatabaseException e) {
            db.closeConnection(true);
            throw e;
        }
    }

    @Test
    @DisplayName("find fail (user has not been inserted)")
    void testFindFail() throws Exception {
        db.connect();
        try {
            findLuke = userDao.find(luke.getUsername());
            db.closeConnection(true);
            assertNull(findLuke);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("find fail (wrong userName)")
    void testFindFail2() throws Exception {
        db.connect();
        try {
            userDao.insert(luke);
            findLuke = userDao.find("xX_user_Xx");
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
            userDao.insert(luke);
            findLuke = userDao.find(luke.getUsername());
            assertNotNull(findLuke);
            int deleteCount = userDao.delete(luke.getUsername());
            findLuke = userDao.find(luke.getUsername());
            assertNull(findLuke);
            assertEquals(1, deleteCount);
            db.closeConnection(true);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    @Test
    @DisplayName("delete fail (user does not exist")
    void testDeleteFail() throws Exception {
        int deleteCount;
        db.connect();
        try {
            deleteCount = userDao.delete(luke.getUsername());
            db.closeConnection(true);
            assertEquals(0, deleteCount);
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

}