package test.data;

import data.Database;
import data.DatabaseException;
import data.OldEventDao;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class OldEventDaoTest {
    Event birthday;
    Event findBirthday;
    Database db;
    OldEventDao eDao;

    @BeforeEach
    public void setUp() throws Exception {
        birthday = new Event("9","lukeludlow","1",40.0,-111.0,"usa","reno","birth",1999);
        findBirthday = null;
        db = new Database();
        eDao = new OldEventDao(db);
    }
    @AfterEach
    public void tearDown() throws Exception {
        db.clearAll();
    }

    @Test
    @DisplayName("insert success")
    public void testInsert() throws Exception {
        try {
            eDao.insert(birthday);
            findBirthday = eDao.find(birthday.getEventID());
        } catch (DatabaseException ex) {
            System.err.println(ex);
        }
        assertNotNull(findBirthday);
        assertEquals(birthday, findBirthday);
    }

    @Test
    @DisplayName("insert fail")
    @SuppressWarnings("Duplicates")
    public void testInsertFail() throws Exception {
        boolean insertSuccess = true;
        try {
            eDao.insert(birthday);
            eDao.insert(birthday);
        } catch (DatabaseException e) {
            insertSuccess = false;
        }
        assertFalse(insertSuccess);
        try {
            findBirthday = eDao.find(birthday.getEventID());
        } catch (DatabaseException e) {
            System.err.println(e);
        }
        assertEquals(birthday, findBirthday);
    }

    @Test
    @DisplayName("find success")
    @SuppressWarnings("Duplicates")
    public void testFind() throws Exception {
        Event e2= new Event("101", "nunya", "nunya01",10.1,-10.1, "japan", "tokyo", "birth", 3019);
        Event e3= new Event("102", "business", "business01",10.2,-10.2, "america", "new york", "party",2020);
        Event finde2 = null;
        Event finde3 = null;
        try {
            eDao.insert(birthday);
            findBirthday = eDao.find(birthday.getEventID());
            eDao.insert(e2);
            finde2 = eDao.find(e2.getEventID());
            eDao.insert(e3);
            finde3 = eDao.find(e3.getEventID());
        } catch (DatabaseException e) {
            System.out.println(e);
        }
        assertNotNull(findBirthday);
        assertNotNull(finde2);
        assertNotNull(finde3);
        assertEquals(birthday, findBirthday);
        assertEquals(e2, finde2);
        assertEquals(e3, finde3);
    }

    @Test
    @DisplayName("find fail")
    public void testFindFail() throws Exception {
        try {
            findBirthday = eDao.find(birthday.getEventID());
        } catch (DatabaseException e) {
            System.err.println(e);
        }
        assertNull(findBirthday);
    }

    @Test
    @DisplayName("delete success")
    public void testDelete() throws Exception {
        boolean deleteSuccess = false;
        try {
            eDao.insert(birthday);
            deleteSuccess = eDao.delete(birthday.getEventID());
            findBirthday = eDao.find(birthday.getEventID());
        } catch (DatabaseException e) {
            System.err.println(e);
        }
        assertNull(findBirthday);
        assertTrue(deleteSuccess);
    }

    @Test
    @DisplayName("delete fail")
    public void testDeleteFail() throws Exception {
        boolean deleteSuccess = false;
        try {
            deleteSuccess = eDao.delete(birthday.getEventID());
        } catch (DatabaseException e) {
            System.err.println(e);
        }
        assertFalse(deleteSuccess);
    }

}
