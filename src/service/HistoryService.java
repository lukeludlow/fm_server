package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.EventDao;
import message.request.HistoryRequest;
import message.response.HistoryResponse;
import model.AuthToken;
import model.Event;

import java.util.List;

/**
 * history service : web api method /event
 */
public class HistoryService {
    /**
     * returns ALL events for ALL family members of the current user. the current
     * user is determined from the provided auth authToken.
     */
    public HistoryResponse getHistory(HistoryRequest h) {

        Database db = new Database();
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        AuthToken found = null;
        try {
            found = authTokenDao.find(h.getAuthToken());
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
            return null;
        }
        if (found == null) {
            return null;
        }
        String username = found.getUserName();
        EventDao eventDao = new EventDao(db);
        List<Event> events = null;
        try {
            events = eventDao.findMany(username);
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
            return null;
        }
        if (events.size() == 0) {
            return null;
        }
        return new HistoryResponse(events.toArray(new Event[0]));
    }
}
