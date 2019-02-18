package data;

import model.Event;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    @DisplayName("insert getEvent")
    void testInsert() throws Exception {
        eventDao.insert(birthday);
        findBirthday = eventDao.find(birthday.getEventID());
        assertNotNull(findBirthday);
        assertEquals(birthday, findBirthday);
    }
    @Test
    @DisplayName("insert getEvent fail (insert same getEvent twice)")
    void testInsertFail() {
        assertThrows(DatabaseException.class,
                () -> {
                    eventDao.insert(birthday);
                    eventDao.insert(birthday);
                });
    }
    @Test
    @DisplayName("insert getEvent fail (empty primary key)")
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
    @DisplayName("find fail (getEvent has not been inserted)")
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
    @DisplayName("delete fail (getEvent does not exist")
    void testDeleteFail() throws Exception {
        eventDao.delete(birthday.getEventID());
    }
    @Test
    @DisplayName("find many (all events belonging to a descendant/user)")
    void testFindMany() throws Exception {
        Event e2 = new Event("101", "lukeludlow", "nunya01",10.1,-10.1, "japan", "tokyo", "birth", 3019);
        Event e3 = new Event("102", "lukeludlow", "nunya02",10.2,-10.2, "japan2", "tokyo", "birth", 3012);
        eventDao.insert(birthday);
        eventDao.insert(e2);
        eventDao.insert(e3);
        List<Event> events = eventDao.findMany(birthday.getDescendant());
        assertEquals(3, events.size());
        assertEquals(birthday, events.get(0));
        assertEquals(e2, events.get(1));
        assertEquals(e3, events.get(2));
    }
    @Test
    @DisplayName("find many fail")
    void testFindManyFail() throws Exception {
        List<Event> events = eventDao.findMany(birthday.getDescendant());
        assertEquals(0, events.size());
    }
    @Test
    @DisplayName("delete many (delete all events belonging to a descendant/user)")
    void testDeleteMany() throws Exception {
        Event e2 = new Event("101", "lukeludlow", "nunya01",10.1,-10.1, "japan", "tokyo", "birth", 3019);
        Event e3 = new Event("102", "lukeludlow", "nunya02",10.2,-10.2, "japan2", "tokyo", "birth", 3012);
        eventDao.insert(birthday);
        eventDao.insert(e2);
        eventDao.insert(e3);
        List<Event> events = eventDao.findMany(birthday.getDescendant());
        assertEquals(3, events.size());
        assertEquals(birthday, events.get(0));
        assertEquals(e2, events.get(1));
        assertEquals(e3, events.get(2));
        int deleteCount = eventDao.deleteMany(birthday.getDescendant());
        events = eventDao.findMany(birthday.getDescendant());
        assertEquals(3, deleteCount);
        assertEquals(0, events.size());
    }
}