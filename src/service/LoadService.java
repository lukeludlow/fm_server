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
    /**
     * clears all data from the database (just like the /clear api), and then loads the
     * posted user, person, and event data into the database.
     */
    public LoadResponse load(LoadRequest request) throws ResponseException {
        Database db = new Database();
        try {
            db.clearAll();
        } catch (DatabaseException e) {
            throw new ResponseException(e.toString());
        }
        UserDao userDao = new UserDao(db);
        PersonDao personDao = new PersonDao(db);
        EventDao eventDao = new EventDao(db);
        System.out.printf("inserting:");
        try {
            System.out.printf(" users");
            for (User u : request.getUsers()) {
                userDao.insert(u);
            }
            System.out.printf(" people");
            for (Person p : request.getPeople()) {
                personDao.insert(p);
            }
            System.out.printf(" events");
            for (Event e : request.getEvents()) {
                eventDao.insert(e);
            }
            System.out.printf("\n");
        } catch (DatabaseException e) {
            throw new ResponseException(e.toString());
        }
        String message = "successfully added " +
                request.getUsers().length + " users, " +
                request.getPeople().length + " people, and " +
                request.getEvents().length + " events to the database.";
        return new LoadResponse(message);
    }
}
