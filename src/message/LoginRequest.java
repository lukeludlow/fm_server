package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginRequest extends AbstractRequest {
    private String username;
    private String password;
}
