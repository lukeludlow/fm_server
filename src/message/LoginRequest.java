package message;

/**
 * Login Request Body:
 * {
 * "userName": "susan", // Non-empty string
 * "password": "mysecret" // Non-empty string
 * }
 */
public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
