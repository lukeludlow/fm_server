package service;

import message.request.LoadRequest;
import message.response.LoadResponse;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {

    @Test
    void testLoadService() throws Exception {
        User[] loadUsers = {
                new User("lukeludlow", "hunter2", "ll@live.com", "luke", "ludlow", "m", "99"),
                new User("treyway", "scumgang2", "tekashi69@gmail.com", "tekashi", "sixnine", "m", "69")
        };
        Person[] loadPeople = {
                new Person("99", "lukeludlow", "luke", "ludlow", "m", "199", "299", "66"),
                new Person("69", "treyway", "tekashi", "sixnine", "m", "169", "269", "6969")
        };
        Event[] loadEvents = {
                new Event("111", "lukeludlow", "99", 66.666, -111.111, "usa", "reno", "party", 2020),
                new Event("0096", "treyway", "69", 69.69, -6.9999, "japan", "tokyo", "birth", 3019)
        };
        LoadRequest loadRequest = new LoadRequest(loadUsers, loadPeople, loadEvents);
        LoadResponse expectedResponse = new LoadResponse("successfully added 2 users, 2 persons, and 2 events to the database.");
        LoadService loadService = new LoadService();
        LoadResponse actualResponse = loadService.load(loadRequest);
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

}