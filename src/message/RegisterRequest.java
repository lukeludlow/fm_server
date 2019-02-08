package message;

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
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String gender;

    public RegisterRequest(String username, String password, String email, String firstname, String lastname, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
    }
}
