package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.EventDao;
import message.request.EventRequest;
import message.response.EventResponse;
import message.response.ResponseException;
import model.AuthToken;
import model.Event;

/**
 * getEvent service : web api method /getEvent/[eventID]
 */
public class EventService {

    private Database db;
    private EventDao eventDao;
    private AuthTokenDao authTokenDao;
    private Event foundEvent;
    private AuthToken foundToken;

    public EventService() {
        db = new Database();
        eventDao = new EventDao(db);
        authTokenDao = new AuthTokenDao(db);
        foundEvent = null;
        foundToken = null;
    }
    
    public EventResponse getEvent(EventRequest request) throws ResponseException {
        find(request);
        checkValidity();
        return new EventResponse(foundEvent);
    }

    private void find(EventRequest request) throws ResponseException {
        try {
            db.connect();
            foundToken = authTokenDao.find(request.getAuthToken());
            foundEvent = eventDao.find(request.getEventID());
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
        if (foundEvent == null) {
            throw new ResponseException("event not found");
        }
        if (!foundEvent.getDescendant().equals(foundToken.getUserName())) {
            throw new ResponseException("not allowed to retrieve information that belongs to another user");
        }
    }

}
