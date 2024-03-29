package service;

import communication.Encoder;
import message.request.LoadRequest;
import message.response.LoadResponse;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoadServiceTest {

//    Database db;
    User[] loadUsers;
    Person[] loadPeople;
    Event[] loadEvents;
    LoadRequest request;
    LoadResponse expectedResponse;
    LoadResponse actual;
    LoadService service;

    @BeforeEach
    void setUp() throws Exception {
//        db = new Database();
        loadUsers = new User[]{
                new User("lukeludlow", "hunter2", "ll@live.com", "luke", "ludlow", "m", "99"),
                new User("treyway", "scumgang2", "tekashi69@gmail.com", "tekashi", "sixnine", "m", "69")
        };
        loadPeople = new Person[]{
                new Person("lukeludlow", "99", "luke", "ludlow", "m", "199", "299", "66"),
                new Person("treyway", "69", "tekashi", "sixnine", "m", "169", "269", "6969")
        };
        loadEvents = new Event[]{
                new Event("lukeludlow", "111", "99", 66.666, -111.111, "usa", "reno", "party", 2020),
                new Event("treyway", "0096", "69", 69.69, -6.9999, "japan", "tokyo", "birth", 3019)
        };

        expectedResponse = new LoadResponse("Successfully added 2 users, 2 persons, and 2 events to the database.");
        actual = null;
        request = new LoadRequest(loadUsers, loadPeople, loadEvents);
        service = new LoadService();

    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    @DisplayName("load success")
    void testLoadService() throws Exception {
        actual = service.load(request);
        assertEquals(expectedResponse, actual);
    }

    @Test
    @DisplayName("load fail (empty data arrays. doesn't technically fail, it just inserts nothing.)")
    void testLoadServiceFail() throws Exception {
        loadUsers = new User[]{};
        loadPeople = new Person[]{};
        loadEvents = new Event[]{};
        request = new LoadRequest(loadUsers, loadPeople, loadEvents);
        expectedResponse = new LoadResponse("Successfully added 0 users, 0 persons, and 0 events to the database.");
        actual = service.load(request);
        assertEquals(expectedResponse, actual);
    }

    @Test
    @DisplayName("load the big example json")
    void testLoadService2() throws Exception {
        String json = new String(Files.readAllBytes(Paths.get("json/example.json")));
        request = Encoder.deserialize(json, LoadRequest.class);
        actual = service.load(request);
        expectedResponse = new LoadResponse("Successfully added 1 users, 3 persons, and 2 events to the database.");
        assertEquals(expectedResponse, actual);
    }

}