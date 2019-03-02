package service;

import data.*;
import message.request.FillRequest;
import message.response.FillResponse;
import message.response.ResponseException;
import model.Event;
import model.FamilyTree;
import model.Person;
import model.User;

import java.io.IOException;

/**
 * fill service : web api method /fill/[userName]/{generations}
 */
public class FillService {
    /**
     * populates the server's database with generated data for the specified user name.
     * the required "userName" parameter must be a user already registered with the server. if there is
     * any data in the database already associated with the given user name, it is deleted. the
     * optional “generations” parameter lets the caller specify the number of generations of ancestors
     * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
     * persons each with associated events).
     */
    public FillResponse fill(FillRequest request) throws ResponseException {

        Database db = new Database();
        UserDao userDao = new UserDao(db);
        User found = null;

        // check if user is already registered
        try {
            found = userDao.find(request.getUsername());
        } catch (DatabaseException e) {
            throw new ResponseException(e.toString());
        }

        if (found == null) {
            throw new ResponseException("user not found");
        }

        // clear any existing data related to the user
        try {
            clearAllUserData(request.getUsername());
        } catch (DatabaseException e) {
            throw new ResponseException("failed to clear user's existing data");
        }

        FillFactory factory;
        try {
            factory = new FillFactory(found);
        } catch (IOException e) {
            throw new ResponseException("unable to create fill factory (invalid json path)");
        }
        int DEFAULT_GENERATIONS = 4;
        if (request.getGenerations() < 0) {
            request.setGenerations(DEFAULT_GENERATIONS);
        }
        factory.fill(request.getGenerations());
        String success ;
        try {
            success = insertFamilyTree(factory.getFamilyTree());
            // update the user's info in the database too
            insertUser(request.getUsername(), factory);
        } catch (DatabaseException e) {
            throw new ResponseException("unable to insert generated data into database.\n" + e.getMessage());
        }
        return new FillResponse(success);
    }

    private void clearAllUserData(String username) throws DatabaseException {
        Database db = new Database();
        PersonDao personDao = new PersonDao(db);
        EventDao eventDao = new EventDao(db);
        AuthTokenDao authTokenDao = new AuthTokenDao(db);
        personDao.deleteMany(username);
        eventDao.deleteMany(username);
        authTokenDao.deleteMany(username);
    }

    private String insertFamilyTree(FamilyTree tree) throws DatabaseException {
        Database db = new Database();
        PersonDao personDao = new PersonDao(db);
        EventDao eventDao = new EventDao(db);
        for (Person p : tree.getAllPeople()) {
            personDao.insert(p);
        }
        for (Event e : tree.getAllEvents()) {
            eventDao.insert(e);
        }
        return "successfully added " +
                tree.getAllPeople().size() +
                " people and " + tree.getAllEvents().size() +
                " events to the database.";
    }

    private void insertUser(String username, FillFactory factory) throws DatabaseException {
        Database db = new Database();
        UserDao userDao = new UserDao(db);
        userDao.delete(username);
        userDao.insert(factory.getUser());
    }

}
