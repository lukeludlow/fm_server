package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user object model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /** unique username */
    private String username;
    /** user's password */
    private String password;
    /** user's email address */
    private String email;
    /** user's first name */
    private String firstname;
    /** user's last name */
    private String lastname;
    /** user's gender, either "f" or "m" */
    private String gender;
    /** unique person ID assigned to this user's generated person object */
    private String personID;
}
