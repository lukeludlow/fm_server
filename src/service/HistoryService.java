package service;

import message.HistoryRequest;
import message.HistoryResponse;

/**
 * history service : web api method /event
 */
public class HistoryService {
    /**
     * returns ALL events for ALL family members of the current user. the current
     * user is determined from the provided auth authToken.
     */
    public HistoryResponse history(HistoryRequest h) {
        return null;
    }
}
