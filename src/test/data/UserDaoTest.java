package data;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    Database db;
    UserDao uDao;
    User luke;
    User anotherUser;
    User findLuke;
    User findAnother;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        uDao = new UserDao(db);
        luke = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
        anotherUser = new User("abc", "password", "abc@abc.com","a","c","f","321");
        findLuke = null;
        findAnother = null;
    }
    @AfterEach
    public void tearDown() throws Exception {
        db.clearAll();
    }



}