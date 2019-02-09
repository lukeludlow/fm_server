package message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Register Request Body:
 * {
 * "userName": "susan", // Non-empty string
 * "password": "mysecret", // Non-empty string
 * "email": "susan@gmail.com", // Non-empty string
 * "firstName": "Susan", // Non-empty string
 * "lastName": "Ellis", // Non-empty string
 * "gender": "f" // “f” or “m”
 * }
 */
@Data
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String gender;
}
