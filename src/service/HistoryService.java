package service;

import message.HistoryRequest;
import message.HistoryResponse;

/**
 * history service : web api method /event
 */
public class HistoryService {
    /**
     * returns ALL events for ALL family members of the current user. the current
     * user is determined from the provided auth token.
     * @param h history request
     * @return history response
     */
    public HistoryResponse history(HistoryRequest h) {
        return null;
    }
}
