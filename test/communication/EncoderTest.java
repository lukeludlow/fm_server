package communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import message.LoginRequest;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.Test;
import message.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static org.junit.jupiter.api.Assertions.*;

class EncoderTest {

    EventRequest eventRequest = new EventRequest("123", "secret");
    FamilyRequest familyRequest = new FamilyRequest("secret");
    FillRequest fillRequest = new FillRequest("lukeludlow", 2);
    HistoryRequest historyRequest = new HistoryRequest("secret");
    LoginRequest loginRequest = new LoginRequest("lukeludlow", "hunter2");
    PersonRequest personRequest = new PersonRequest("69", "secret");
    RegisterRequest registerRequest = new RegisterRequest("lukeludlow", "hunter2", "ll@live.com", "luke", "ludlow", "m");

    String eventRequestJson = "{\"eventID\": \"123\", \"authToken\": \"secret\"}";
    String familyRequestJson = "{\"authToken\": \"secret\"}";
    String fillRequestJson = "{\"userName\": \"lukeludlow\", \"generations\": 2}";
    String historyRequestJson = "{\"authToken\": \"secret\"}";
    String loginRequestJson = "{\"userName\": \"lukeludlow\", \"password\": \"hunter2\"}";
    String personRequestJson = "{\"personID\": \"69\", \"authToken\": \"secret\"}";
    String registerRequestJson = "{\"userName\": \"lukeludlow\", \"password\": \"hunter2\", \"email\": \"ll@live.com\", \"firstName\": \"luke\", \"lastName\": \"ludlow\", \"gender\": \"m\"}";

    User[] loadUsers = {
            new User("lukeludlow", "hunter2", "ll@live.com", "luke", "ludlow", "m", "99"),
            new User("treyway", "scumgang2", "tekashi69@gmail.com", "tekashi", "sixnine", "m", "69")
    };
    Person[] loadPeople = {
            new Person("99", "lukeludlow", "luke", "ludlow", "m", "199", "299", "66"),
            new Person("69", "treyway", "tekashi", "sixnine", "m", "169", "269", "6969")
    };
    Event[] loadEvents = {
            new Event("111", "lukeludlow","99",66.666,-111.111,"usa","reno","party",2020),
            new Event("0096", "treyway", "69",69.69, -6.9999,"japan","tokyo","birth",3019)
    };
    String loadRequestJson = "" +
            "{" +
            "\"users\": [" +
                "{" +
                    "\"userName\": \"lukeludlow\", \"password\": \"hunter2\", \"email\": \"ll@live.com\", \"firstName\": \"luke\", \"lastName\": \"ludlow\", \"gender\": \"m\", \"personID\": \"99\"" +
                "}," +
                "{" +
                    "\"userName\": \"treyway\", \"password\": \"scumgang2\", \"email\": \"tekashi69@gmail.com\", \"firstName\": \"tekashi\", \"lastName\": \"sixnine\", \"gender\": \"m\", \"personID\": \"69\"" +
                "}" +
            "]," +
            "\"persons\": [" +
                "{" +
                    "\"personID\": \"99\", \"descendant\": \"lukeludlow\", \"firstName\": \"luke\", \"lastName\": \"ludlow\", \"gender\": \"m\", \"father\": \"199\", \"mother\": \"299\", \"spouse\": \"66\"" +
                "}," +
                "{" +
                    "\"personID\": \"69\", \"descendant\": \"treyway\", \"firstName\": \"tekashi\", \"lastName\": \"sixnine\", \"gender\": \"m\", \"father\": \"169\", \"mother\": \"269\", \"spouse\": \"6969\"" +
                "}" +
            "]," +
            "\"events\": [" +
                "{" +
                    "\"eventID\": \"111\", \"descendant\": \"lukeludlow\", \"personID\": \"99\", \"latitude\": 66.666, \"longitude\": -111.111, \"country\": \"usa\", \"city\": \"reno\", \"eventType\": \"party\", \"year\": 2020" +
                "}," +
                "{" +
                    "\"eventID\": \"0096\", \"descendant\": \"treyway\", \"personID\": \"69\", \"latitude\": 69.69, \"longitude\": -6.9999, \"country\": \"japan\", \"city\": \"tokyo\", \"eventType\": \"birth\", \"year\": 3019" +
                "}" +
            "]" +
            "}";
    LoadRequest loadRequest = new LoadRequest(loadUsers, loadPeople, loadEvents);

    AbstractRequest[] requestObjects = {
            eventRequest,
            familyRequest,
            fillRequest,
            historyRequest,
            loginRequest,
            personRequest,
            registerRequest,
            loadRequest
    };
    String[] requestJson = {
            eventRequestJson,
            familyRequestJson,
            fillRequestJson,
            historyRequestJson,
            loginRequestJson,
            personRequestJson,
            registerRequestJson,
            loadRequestJson
    };

    @Test
    void testEncodeRequests() {
        for (int i = 0; i < requestObjects.length; i++) {
            String actual = Encoder.encode(requestObjects[i]);
            JSONAssert.assertEquals(requestJson[i], actual, JSONCompareMode.STRICT);
        }
    }

}