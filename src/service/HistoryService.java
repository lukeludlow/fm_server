package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.EventDao;
import message.request.HistoryRequest;
import message.response.HistoryResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.Event;

import java.util.ArrayList;

/**
 * history service : web api method /event
 */
public class HistoryService {

    private Database db;
    private AuthTokenDao authTokenDao;
    private EventDao eventDao;
    private AuthToken foundToken;
    private String username;
    private ArrayList<Event> events;

    public HistoryService() {
        db = new Database();
        authTokenDao = new AuthTokenDao(db);
        eventDao = new EventDao(db);
        foundToken = null;
        username = null;
        events = null;
    }

    /**
     * returns ALL events for ALL family members of the current user. the current
     * user is determined from the provided auth authToken.
     */
    public HistoryResponse getHistory(HistoryRequest request) throws ResponseException {
        find(request);
        checkValidity();
        return new HistoryResponse(events.toArray(new Event[0]));
    }

    // TODO fix duplicates
    private void find(HistoryRequest request) throws ResponseException {
        try {
            db.connect();
            foundToken = authTokenDao.find(request.getAuthToken());
            username = foundToken.getUserName();
            events = eventDao.findMany(username);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw new ResponseException(e);
        }
    }

    private void checkValidity() throws ResponseException {
        if (foundToken == null) {
            throw new ResponseException("invalid authtoken");
        }
        if (events.size() == 0) {
            throw new ResponseException("user has no connected events");
        }
    }


}
