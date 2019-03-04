package service;

import data.*;
import message.request.LoadRequest;
import message.response.LoadResponse;
import message.response.ResponseException;
import model.Event;
import model.Person;
import model.User;

/**
 * load service : web api method /load
 */
public class LoadService {

    private Database db;
    private UserDao userDao;
    private PersonDao personDao;
    private EventDao eventDao;

    public LoadService() {
        db = new Database();
        userDao = new UserDao(db);
        personDao = new PersonDao(db);
        eventDao = new EventDao(db);
    }

    /**
     * clears all data from the database (just like the /clear api), and then loads the
     * posted user, person, and event data into the database.
     */
    public LoadResponse load(LoadRequest request) throws ResponseException {
        clear();
        insert(request);
        String message = "successfully added " +
                request.getUsers().length + " users, " +
                request.getPersons().length + " people, and " +
                request.getEvents().length + " events to the database.";
        return new LoadResponse(message);
    }

    public void insert(LoadRequest request) throws ResponseException {
        try {
            db.connect();
            for (User u : request.getUsers()) {
                userDao.insert(u);
            }
            for (Person p : request.getPersons()) {
                personDao.insert(p);
            }
            for (Event e : request.getEvents()) {
                eventDao.insert(e);
            }
            db.closeResponseConnection(true);
        } catch (DatabaseException ex) {
            db.closeResponseConnection(false);
            throw new ResponseException(ex);
        }
    }

    public void clear() throws ResponseException {
        try {
            db.clearAll();
        } catch (DatabaseException e) {
            throw new ResponseException(e);
        }
    }

}
