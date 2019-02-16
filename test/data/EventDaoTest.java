package data;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventDaoTest {

    private Database db;
    private EventDao eventDao;
    private Event birthday;
    private Event anotherEvent;
    private Event findBirthday;
    private Event findAnother;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        eventDao = new EventDao(db);
        birthday = new Event("9","lukeludlow","1",40.0,-111.0,"usa","reno","birth",1999);
        anotherEvent = new Event("101", "nunya", "nunya01",10.1,-10.1, "japan", "tokyo", "birth", 3019);
        findBirthday = null;
        findAnother = null;
    }
    @AfterEach
    void tearDown() throws Exception {
        db.clearAll();
    }

    @Test
    @DisplayName("insert event")
    void testInsert() throws Exception {
        eventDao.insert(birthday);
        findBirthday = eventDao.find(birthday.getEventID());
        assertNotNull(findBirthday);
        assertEquals(birthday, findBirthday);
    }
    @Test
    @DisplayName("insert event fail (insert same event twice)")
    void testInsertFail() {
        assertThrows(DatabaseException.class,
                () -> {
                    eventDao.insert(birthday);
                    eventDao.insert(birthday);
                });
    }
    @Test
    @DisplayName("insert event fail (empty primary key)")
    void testInsertFail2() {
        assertThrows(DatabaseException.class,
                () -> {
                    birthday.setEventID(null);
                    eventDao.insert(birthday);
                });
    }

    @Test
    @DisplayName("find success")
    void testFind() throws Exception {
        eventDao.insert(birthday);
        findBirthday = eventDao.find(birthday.getEventID());
        assertNotNull(findBirthday);
        assertEquals(birthday, findBirthday);
    }
    @Test
    @DisplayName("find success (find multiple entries multiple times)")
    void testFind2() throws Exception {
        eventDao.insert(birthday);
        eventDao.insert(anotherEvent);
        findBirthday = eventDao.find(birthday.getEventID());
        findAnother = eventDao.find(anotherEvent.getEventID());
        assertNotNull(findBirthday);
        assertNotNull(anotherEvent);
        assertEquals(birthday, findBirthday);
        assertEquals(anotherEvent, findAnother);
        findBirthday = eventDao.find(birthday.getEventID());
        findAnother = eventDao.find(anotherEvent.getEventID());
        assertNotNull(findBirthday);
        assertNotNull(anotherEvent);
        assertEquals(birthday, findBirthday);
        assertEquals(anotherEvent, findAnother);
    }
    @Test
    @DisplayName("find fail (event has not been inserted)")
    void testFindFail() throws Exception {
        findBirthday = eventDao.find(birthday.getEventID());
        assertNull(findBirthday);
    }
    @Test
    @DisplayName("find fail (wrong eventID)")
    void testFindFail2() throws Exception {
        eventDao.insert(birthday);
        findBirthday = eventDao.find("xX_event_Xx");
        assertNull(findBirthday);
    }

    @Test
    @DisplayName("delete success")
    void testDelete() throws Exception {
        eventDao.insert(birthday);
        findBirthday = eventDao.find(birthday.getEventID());
        assertNotNull(findBirthday);
        int deleteCount = eventDao.delete(birthday.getEventID());
        findBirthday = eventDao.find(birthday.getEventID());
        assertNull(findBirthday);
        assertEquals(1, deleteCount);
    }
    @Test
    @DisplayName("delete fail (event does not exist")
    void testDeleteFail() throws Exception {
        eventDao.delete(birthday.getEventID());
    }

}