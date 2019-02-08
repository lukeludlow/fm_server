package service;

import message.FillRequest;
import message.FillResponse;

/**
 * fill service : web api method /fill/[username]/{generations}
 */
public class FillService {
    /**
     * populates the server's database with generated data for the specified user name.
     * the required "username" parameter must be a user already registered with the server. if there is
     * any data in the database already associated with the given user name, it is deleted. the
     * optional “generations” parameter lets the caller specify the number of generations of ancestors
     * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
     * persons each with associated events).
     * @param f fill request
     * @return fill result
     */
    public FillResponse fill(FillRequest f) {
        return null;
    }
}
