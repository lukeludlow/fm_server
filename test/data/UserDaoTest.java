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
    }

    @Test
    @DisplayName("insert user")
    void testInsert() throws Exception {
        userDao.insert(luke);
        findLuke = userDao.find(luke.getUserName());
        assertNotNull(findLuke);
        assertEquals(luke, findLuke);
    }
    @Test
    @DisplayName("insert user fail (insert same user twice)")
    void testInsertFail() {
        assertThrows(DatabaseException.class,
                () -> {
                    userDao.insert(luke);
                    userDao.insert(luke);
                });
    }
    @Test
    @DisplayName("insert user fail (empty primary key)")
    void testInsertFail2() {
        assertThrows(DatabaseException.class,
                () -> {
                    luke.setUserName(null);
                    userDao.insert(luke);
                });
    }

    @Test
    @DisplayName("find success")
    void testFind() throws Exception {
        userDao.insert(luke);
        findLuke = userDao.find(luke.getUserName());
        assertNotNull(findLuke);
        assertEquals(luke, findLuke);
    }
    @Test
    @DisplayName("find success (find multiple entries multiple times)")
    void testFind2() throws Exception {
        userDao.insert(luke);
        userDao.insert(anotherUser);
        findLuke = userDao.find(luke.getUserName());
        findAnother = userDao.find(anotherUser.getUserName());
        assertNotNull(findLuke);
        assertNotNull(anotherUser);
        assertEquals(luke, findLuke);
        assertEquals(anotherUser, findAnother);
        findLuke = userDao.find(luke.getUserName());
        findAnother = userDao.find(anotherUser.getUserName());
        assertNotNull(findLuke);
        assertNotNull(anotherUser);
        assertEquals(luke, findLuke);
        assertEquals(anotherUser, findAnother);
    }
    @Test
    @DisplayName("find fail (user has not been inserted)")
    void testFindFail() throws Exception {
        findLuke = userDao.find(luke.getUserName());
        assertNull(findLuke);
    }
    @Test
    @DisplayName("find fail (wrong userName)")
    void testFindFail2() throws Exception {
        userDao.insert(luke);
        findLuke = userDao.find("xX_user_Xx");
        assertNull(findLuke);
    }

    @Test
    @DisplayName("delete success")
    void testDelete() throws Exception {
        userDao.insert(luke);
        findLuke = userDao.find(luke.getUserName());
        assertNotNull(findLuke);
        int deleteCount = userDao.delete(luke.getUserName());
        findLuke = userDao.find(luke.getUserName());
        assertNull(findLuke);
        assertEquals(1, deleteCount);
    }
    @Test
    @DisplayName("delete fail (user does not exist")
    void testDeleteFail() throws Exception {
        int deleteCount = userDao.delete(luke.getUserName());
        assertEquals(0, deleteCount);
    }

}