package service;

import data.AuthTokenDao;
import data.Database;
import data.EventDao;
import message.request.HistoryRequest;
import message.response.HistoryResponse;
import model.AuthToken;
import model.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoryServiceTest {

    @Test
    void testHistoryService() throws Exception {
        Event birthday = new Event("9","lukeludlow","1",40.0,-111.0,"usa","reno","birth",1999);
        Event anotherEvent = new Event("101", "lukeludlow", "nunya01",10.1,-10.1, "japan", "tokyo", "birth", 3019);
        Event foundBirthday = null;
        Event foundAnother = null;
        Database db = new Database();
        db.clearAll();
        EventDao eventDao = new EventDao(db);
        eventDao.insert(birthday);
        eventDao.insert(anotherEvent);
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        AuthToken secret = new AuthToken("xX_secret_Xx", "lukeludlow");
        authTokenDao.insert(secret);
        Event[] expectedData = {
            birthday,
            anotherEvent
        };
        HistoryResponse expectedResponse = new HistoryResponse(expectedData);
        HistoryService historyService = new HistoryService();
        HistoryRequest historyRequest = new HistoryRequest(secret.getAuthtoken());
        HistoryResponse actualResponse = historyService.getHistory(historyRequest);
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        db.clearAll();
    }


}
