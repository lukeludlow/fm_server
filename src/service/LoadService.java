package service;

import data.*;
import message.LoadRequest;
import message.LoadResponse;
import model.Event;
import model.Person;
import model.User;

/**
 * load service : web api method /load
 */
public class LoadService {
    /**
     * clears all data from the database (just like the /clear api), and then loads the
     * posted user, person, and event data into the database.
     */
    public LoadResponse load(LoadRequest request) {
        Database db = new Database();
        try {
            db.clearAll();
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
            return null;
        }
        UserDao userDao = new UserDao(db);
        PersonDao personDao = new PersonDao(db);
        EventDao eventDao = new EventDao(db);
        try {
            for (User u : request.getUsers()) {
                userDao.insert(u);
            }
            for (Person p : request.getPersons()) {
                personDao.insert(p);
            }
            for (Event e : request.getEvents()) {
                eventDao.insert(e);
            }
        } catch (DatabaseException ex) {
            System.err.println(ex.toString());
            return null;
        }
        String message = "successfully added " +
                request.getUsers().length + " users, " +
                request.getPersons().length + " persons, and " +
                request.getEvents().length + " events to the database.";
        return new LoadResponse(message);

    }
}
