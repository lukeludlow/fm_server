package message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login Success Response Body:
 * {
 * "authToken": "cf7a368f", // Non-empty auth token string
 * "userName": "susan", // User name passed in with request
 * "personID": "39f9fe46" // Non-empty string containing the Person ID of the user’s generated Person object
 * }
 */
@Data
@NoArgsConstructor
public class LoginResponse {
    private String authtoken;
    private String username;
    private String personid;
}
