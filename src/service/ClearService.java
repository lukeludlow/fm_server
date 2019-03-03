package service;

import data.Database;
import data.DatabaseException;
import message.response.ClearResponse;
import message.response.ResponseException;

/**
 * clear service : web api method /clear
 */
public class ClearService {

    private Database db;

    public ClearService() {
        db = new Database();
    }

    /**
     * deletes ALL data from the database, including user accounts, auth tokens, and
     * generated person and getEvent data.
     */
    public ClearResponse clear() throws ResponseException {
        try {
            db.clearAll();
        } catch (DatabaseException e) {
            throw new ResponseException(e);
        }
        return new ClearResponse("clear succeeded.");
    }

}
