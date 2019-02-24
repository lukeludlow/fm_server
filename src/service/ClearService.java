package service;

import data.Database;
import data.DatabaseException;
import message.response.ClearResponse;
import message.response.ResponseException;

/**
 * clear service : web api method /clear
 */
public class ClearService {
    /**
     * deletes ALL data from the database, including user accounts, auth tokens, and
     * generated person and getEvent data.
     */
    public ClearResponse clear() throws ResponseException {
        Database db = new Database();
        try {
            db.clearAll();
        } catch (DatabaseException ex) {
            throw new ResponseException(ex.toString());
        }
        return new ClearResponse("clear succeeded.");
    }
}
