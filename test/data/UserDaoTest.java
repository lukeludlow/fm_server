package data;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {


    private Database db;
    private UserDao uDao;
    private User luke;
    private User anotherUser;
    private User findLuke;
    private User findAnother;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        uDao = new UserDao(db);
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
        uDao.insert(luke);
        findLuke = uDao.find(luke.getUsername());
        assertNotNull(findLuke);
        assertEquals(luke, findLuke);
    }
    @Test
    @DisplayName("insert user fail (insert same user twice)")
    void testInsertFail() {
        assertThrows(DatabaseException.class,
                () -> {
                    uDao.insert(luke);
                    uDao.insert(luke);
                });
    }
    @Test
    @DisplayName("insert user fail (empty primary key)")
    void testInsertFail2() {
        assertThrows(DatabaseException.class,
                () -> {
                    luke.setUsername(null);
                    uDao.insert(luke);
                });
    }

    @Test
    @DisplayName("find success")
    void testFind() throws Exception {
        uDao.insert(luke);
        findLuke = uDao.find(luke.getUsername());
        assertNotNull(findLuke);
        assertEquals(luke, findLuke);
    }
    @Test
    @DisplayName("find success (find multiple entries multiple times)")
    void testFind2() throws Exception {
        uDao.insert(luke);
        uDao.insert(anotherUser);
        findLuke = uDao.find(luke.getUsername());
        findAnother = uDao.find(anotherUser.getUsername());
        assertNotNull(findLuke);
        assertNotNull(anotherUser);
        assertEquals(luke, findLuke);
        assertEquals(anotherUser, findAnother);
        findLuke = uDao.find(luke.getUsername());
        findAnother = uDao.find(anotherUser.getUsername());
        assertNotNull(findLuke);
        assertNotNull(anotherUser);
        assertEquals(luke, findLuke);
        assertEquals(anotherUser, findAnother);
    }
    @Test
    @DisplayName("find fail (user has not been inserted)")
    void testFindFail() throws Exception {
        findLuke = uDao.find(luke.getUsername());
        assertNull(findLuke);
    }
    @Test
    @DisplayName("find fail (wrong username)")
    void testFindFail2() throws Exception {
        uDao.insert(luke);
        findLuke = uDao.find("xX_user_Xx");
        assertNull(findLuke);
    }


    @Test
    @DisplayName("delete success")
    public void testDelete() throws Exception {
        uDao.insert(luke);
        int deleteCount = uDao.delete(luke.getUsername());
        findLuke = uDao.find("lukeludlow");
        assertNull(findLuke);
        assertEquals(1, deleteCount);
    }

    @Test
    @DisplayName("delete fail (user does not exist")
    public void testDeleteFail() throws Exception {
        uDao.delete(luke.getUsername());
    }









}