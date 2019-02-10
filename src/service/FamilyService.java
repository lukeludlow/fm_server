package service;

import message.FamilyRequest;
import message.FamilyResponse;

/**
 * family service : web api method /person
 */
public class FamilyService {
    /**
     * returns ALL family members of the current user. the current user is
     * determined from the provided auth token.
     *
     * @param f family request
     * @return family response
     */
    public FamilyResponse family(FamilyRequest f) {
        return null;
    }
}
