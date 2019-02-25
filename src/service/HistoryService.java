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

import java.util.List;

/**
 * history service : web api method /event
 */
public class HistoryService {
    /**
     * returns ALL events for ALL family members of the current user. the current
     * user is determined from the provided auth authToken.
     */
    @SuppressWarnings("Duplicates")  // TODO FIXME
    public HistoryResponse getHistory(HistoryRequest h) throws ResponseException {
        Database db = new Database();
        AuthTokenDao authtokenDao = new AuthTokenDao(db);
        EventDao eventDao = new EventDao(db);
        AuthToken found = null;
        String username = null;
        List<Event> events = null;
        try {
            found = authtokenDao.find(h.getAuthtoken());
        } catch (DatabaseException ex) {
            throw new ResponseException(ex.toString());
        }
        if (found == null) {
            throw new ResponseException("invalid authtoken");
        }
        username = found.getUsername();
        try {
            events = eventDao.findMany(username);
        } catch (DatabaseException e) {
            throw new ResponseException(e.toString());
        }
        if (events.size() == 0) {
            throw new ResponseException("user has no connected events");
        }
        return new HistoryResponse(events.toArray(new Event[0]));
    }
}
