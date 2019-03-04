package service;

import data.AuthTokenDao;
import data.Database;
import data.EventDao;
import message.request.EventRequest;
import message.response.EventResponse;
import model.AuthToken;
import model.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    @Test
    void testEventService() throws Exception {
        Event birthday = new Event("9","lukeludlow","1",40.0,-111.0,"usa","reno","birth",1999);
        Event anotherEvent = new Event("101", "nunya", "nunya01",10.1,-10.1, "japan", "tokyo", "birth", 3019);
        Event foundBirthday = null;
        Event foundAnother = null;
        Database db = new Database();
        db.clearAll();
        EventDao eventDao = new EventDao(db);
        eventDao.insert(birthday);
        EventResponse response = new EventResponse(birthday);
        EventService eventService = new EventService();
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        AuthToken secret = new AuthToken("xX_secret_Xx", "lukeludlow");
        authTokenDao.insert(secret);
        EventRequest eventRequest = new EventRequest(birthday.getEventID(), secret.getAuthToken());
        EventResponse actualResponse = eventService.getEvent(eventRequest);
        assertNotNull(actualResponse);
        assertEquals(response, actualResponse);
        db.clearAll();
    }


}