package message;

/**
 * Register Success Response Body:
 * {
 * "authToken": "cf7a368f", // Non-empty auth token string
 * "userName": "susan", // User name passed in with request
 * "personID": "39f9fe46" // Non-empty string containing the Person ID of the user’s generated Person object
 * }
 */
public class RegisterResponse {
    private String authtoken;
    private String username;
    private String personid;

    public RegisterResponse(String authtoken, String username, String personid) {
        this.authtoken = authtoken;
        this.username = username;
        this.personid = personid;
    }
}
