package message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;

/**
 * Login Success Response Body:
 * {
 * "authToken": "cf7a368f", // Non-empty auth authToken string
 * "userName": "susan", // User name passed in with request
 * "personID": "39f9fe46" // Non-empty string containing the Person ID of the user’s generated Person object
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends AbstractResponse {
    private String authtoken;
    private String username;
    private String personID;
    public LoginResponse(User u) {
        this.authtoken = null;
        this.username = u.getUsername();
        this.personID = u.getPersonID();
    }
}