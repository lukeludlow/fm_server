//package test.data;
//
//import data.Database;
//import data.DatabaseException;
//import data.OldUserDao;
//import model.User;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import static org.junit.Assert.*;
//
//public class OldUserDaoTest {
//    Database db;
//    OldUserDao uDao;
//    User luke;
//    User anotherUser;
//    User findLuke;
//    User findAnother;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        db = new Database();
//        uDao = new OldUserDao(db);
//        luke = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
//        anotherUser = new User("abc", "password", "abc@abc.com","a","c","f","321");
//        findLuke = null;
//        findAnother = null;
//    }
//    @AfterEach
//    public void tearDown() throws Exception {
//        db.clearAll();
//    }
//
//    @Test
//    @DisplayName("insert success")
//    public void testInsert() throws Exception {
//        try {
//            uDao.insert(luke);
//            findLuke = uDao.find(luke.getUsername());
//        } catch (DatabaseException e) {
//            System.err.println(e);
//        }
//        assertNotNull(findLuke);
//        assertEquals(luke, findLuke);
//    }
//
//    @Test
//    @DisplayName("insert 2 users")
//    public void testInsert2() throws Exception {
//        try {
//            uDao.insert(luke);
//            uDao.insert(anotherUser);
//            findLuke = uDao.find(luke.getUsername());
//            findAnother = uDao.find(anotherUser.getUsername());
//        } catch (DatabaseException e) {
//            System.err.println(e);
//        }
//        assertNotNull(findLuke);
//        assertNotNull(findAnother);
//        assertEquals(luke, findLuke);
//        assertEquals(anotherUser, findAnother);
//    }
//
//    @Test
//    @DisplayName("insert fail")
//    @SuppressWarnings("Duplicates")
//    public void testInsertFail() throws Exception {
//        boolean insertSuccess = true;
//        try {
//            uDao.insert(luke);
//            // user must be unique, so adding it again should fail
//            uDao.insert(luke);
//        } catch (DatabaseException e) {
//            insertSuccess = false;
//            // this is supposed to fail so don't print the exception
//            //System.err.println(e);
//        }
//        assertFalse(insertSuccess);
//        try {
//            // insert commits transaction immediately, so the original luke will still be found
//            findLuke = uDao.find(luke.getUsername());
//        } catch (DatabaseException e) {
//            System.err.println(e);
//        }
//        assertEquals(luke, findLuke);
//    }
//
//    @Test
//    @DisplayName("find success")
//    public void testFind() throws Exception {
//        try {
//            uDao.insert(luke);
//            findLuke = uDao.find("lukeludlow");
//        } catch (DatabaseException e) {
//            System.err.println(e);
//        }
//        assertNotNull(findLuke);
//        assertEquals(luke, findLuke);
//    }
//
//    @Test
//    @DisplayName("find fail")
//    public void testFindFail() throws Exception {
//        try {
//            findLuke = uDao.find("lukeludlow");
//        } catch (DatabaseException e) {
//            System.err.println(e);
//        }
//        assertNull(findLuke);
//    }
//
//    @Test
//    @DisplayName("find fail 2")
//    public void testFindFail2() throws Exception {
//        try {
//            uDao.insert(luke);
//            findLuke = uDao.find("lukeludlow");
//            db.clearAll();
//            findLuke = uDao.find("lukeludlow");
//        } catch (DatabaseException e) {
//            System.err.println(e);
//        }
//        assertNull(findLuke);
//    }
//
//    /*
//    @Test
//    @DisplayName("delete success")
//    public void testDelete() throws Exception {
//        boolean deleteSuccess = false;
//        try {
//            uDao.insert(luke);
//            deleteSuccess = uDao.delete("lukeludlow");
//            findLuke = uDao.find("lukeludlow");
//        } catch (DatabaseException e) {
//            System.err.println(e);
//        }
//        assertNull(findLuke);
//        assertTrue(deleteSuccess);
//    }
//
//    @Test
//    @DisplayName("delete fail")
//    public void testDeleteFail() throws Exception {
//        boolean deleteSuccess = false;
//        try {
//            deleteSuccess = uDao.delete("lukeludlow");
//        } catch (DatabaseException e) {
//            System.err.println(e);
//        }
//        assertFalse(deleteSuccess);
//    }
//    */
//
//}
