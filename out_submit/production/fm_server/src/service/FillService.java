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

    private Database db;
    private UserDao userDao;
    private PersonDao personDao;
    private EventDao eventDao;
    private AuthTokenDao authTokenDao;
    private User user;
    private FamilyTree familyTree;
    private String success;
    private FillFactory factory;

    public FillService() {
        db = new Database();
        userDao = new UserDao(db);
        personDao = new PersonDao(db);
        eventDao = new EventDao(db);
        authTokenDao = new AuthTokenDao(db);
        user = null;
        familyTree = null;
        success = null;
        factory = null;
    }

    /**
     * populates the server's database with generated data for the specified user name.
     * the required "userName" parameter must be a user already registered with the server. if there is
     * any data in the database already associated with the given user name, it is deleted. the
     * optional “generations” parameter lets the caller specify the number of generations of ancestors
     * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
     * persons each with associated events).
     */
    public FillResponse fill(FillRequest request) throws ResponseException {
        prepareFill(request.getUserName());
        familyTree = fillHelper(request);
        success = insertFamilyTree(familyTree);
        return new FillResponse(success);
    }

    private FamilyTree fillHelper(FillRequest request) throws ResponseException {
        try {
            factory = new FillFactory(user);
        } catch (IOException e) {
            throw new ResponseException("unable to create fill factory (invalid json path)");
        }
        int DEFAULT_GENERATIONS = 4;
        if (request.getGenerations() < 0) {
            request.setGenerations(DEFAULT_GENERATIONS);
        }
        factory.fill(request.getGenerations());
        return factory.getFamilyTree();
    }

    // check if user exists, and clear all data related to that user
    private void prepareFill(String username) throws ResponseException {
        try {
            db.connect();
            checkUserExists(username);
            clearAllUserData(username);
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw new ResponseException(e);
        }
    }

    private String insertFamilyTree(FamilyTree tree) throws ResponseException {
        try {
            db.connect();
            for (Person p : tree.getAllPeople()) {
                personDao.insert(p);
            }
            for (Event e : tree.getAllEvents()) {
                eventDao.insert(e);
            }
            updateUser();
            db.closeResponseConnection(true);
        } catch (DatabaseException e) {
            db.closeResponseConnection(false);
            throw new ResponseException("unable to insert generated data. " + e.getMessage());
        }
        String message = "Successfully added " +
                tree.getAllPeople().size() + " persons and " +
                tree.getAllEvents().size() + " events to the database.";
        return message;
    }

    private void updateUser() throws DatabaseException {
        userDao.delete(factory.getUser().getUserName());
        userDao.insert(factory.getUser());
    }

     private void checkUserExists(String username) throws DatabaseException {
        user = userDao.find(username);
        if (user == null) {
            throw new DatabaseException("user not found");
        }
    }

    private void clearAllUserData(String username) throws DatabaseException {
        try {
            personDao.deleteMany(username);
            eventDao.deleteMany(username);
        } catch (DatabaseException e) {
            throw new DatabaseException("failed to clear user's existing data. " + e.getMessage());
        }
    }

}
