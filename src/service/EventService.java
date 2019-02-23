package service;

import data.AuthTokenDao;
import data.Database;
import data.DatabaseException;
import data.EventDao;
import message.request.EventRequest;
import message.response.EventResponse;
import model.AuthToken;
import model.Event;

/**
 * getEvent service : web api method /getEvent/[eventID]
 */
public class EventService {
    /**
     * returns the single Event object with the specified ID.
     */
    public EventResponse getEvent(EventRequest e) {
        Database db = new Database();
        EventDao eventDao = new EventDao(db);
        Event found = null;
        try {
            // first, make sure authtoken is valid
            AuthTokenDao authTokenDao = new AuthTokenDao(db);
            AuthToken foundToken = authTokenDao.find(e.getAuthToken());
            if (foundToken == null) {
                // TODO return error response
                return null;
            }
            found = eventDao.find(e.getEventID());
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
        }
        if (found != null) {
            return new EventResponse(found);
        }
        return null;
    }
}
