package service;

import message.request.LoadRequest;
import message.response.LoadResponse;

/**
 * load service : web api method /load
 */
public class LoadService {
    /**
     * clears all data from the database (just like the /clear api), and then loads the
     * posted user, person, and event data into the database.
     *
     * @param l load request
     * @return load response
     */
    public LoadResponse load(LoadRequest l) {
        return null;
    }
}
