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
    /**
     * returns the single Event object with the specified ID.
     */
    public EventResponse getEvent(EventRequest request) throws ResponseException {
        Database db = new Database();
        EventDao eventDao = new EventDao(db);
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        Event found = null;
        AuthToken foundToken = null;
        try {
            foundToken = authTokenDao.find(request.getAuthtoken());
            if (foundToken == null) {
                throw new ResponseException("invalid authtoken");
            }
            found = eventDao.find(request.getEventID());
        } catch (DatabaseException e) {
            throw new ResponseException(e.toString());
        }
        if (found == null) {
            throw new ResponseException("event not found");
        }
        if (!found.getDescendant().equals(foundToken.getUsername())) {
            throw new ResponseException("not allowed to retrieve information that belongs to another user");
        }
        return new EventResponse(found);
    }
}
