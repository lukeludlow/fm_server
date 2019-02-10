package test.data;


import data.Database;
import data.DatabaseException;
import data.UserDao;
import model.User;
import java.sql.Connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class UserDaoTest {
    Database db;
    User insertUser;
    User foundUser;
    UserDao userDao;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        insertUser = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
    }

    @Test
    @DisplayName("successful insertion")
    public void insertPass() throws Exception {
        db = new Database();
        db.connect();
        db.clearAll();
        userDao = new UserDao(db);
        try {
            Connection c = db.connect();
            userDao.add(insertUser);
            foundUser = userDao.get(insertUser.getUsername());
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            db.closeConnection(false);
        }
        assertNotNull(foundUser);
        assertEquals(insertUser, foundUser);
    }
}
