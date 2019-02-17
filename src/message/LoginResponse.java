package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.AuthToken;
import model.Person;

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
    private String authToken;
    private String userName;
    private String personID;
}
