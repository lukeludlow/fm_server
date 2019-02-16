package data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import model.User;
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
    void testInsert() {
        try {
            uDao.insert(luke);
            findLuke = uDao.find(luke.getUsername());
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
        }
        assertNotNull(findLuke);
        assertEquals(luke, findLuke);
    }



}
