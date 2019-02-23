package service;

import data.Database;
import data.DatabaseException;
import message.response.ClearResponse;

/**
 * clear service : web api method /clear
 */
public class ClearService {
    /**
     * deletes ALL data from the database, including user accounts, auth tokens, and
     * generated person and getEvent data.
     */
    public ClearResponse clear() {
        Database db = new Database();
        try {
            db.clearAll();
        } catch (DatabaseException ex) {
            return new ClearResponse("error: database exception occurred while clearing");
        }
        return new ClearResponse("clear succeeded.");
    }
}
