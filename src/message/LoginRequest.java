package message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login Request Body:
 * {
 * "userName": "susan", // Non-empty string
 * "password": "mysecret" // Non-empty string
 * }
 */
@Data
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
