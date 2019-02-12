package service;

import message.request.RegisterRequest;
import message.response.RegisterResponse;

/**
 * register service : web api method /user/register
 */
public class RegisterService {
    /**
     * creates a new user account, generates 4 generations of ancestor data for the new
     * user, logs the user in, and returns an auth token.
     *
     * @param r register request
     * @return register response
     */
    public RegisterResponse register(RegisterRequest r) {
        return null;
    }
}
