package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userName; // unique userName
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender; // either "f" or "m"
    private String personID; // unique person ID assigned to this user's generated person object
    public String getPrimaryKey() { return getUserName(); }
}
