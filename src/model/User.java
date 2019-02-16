package model;

import com.sun.xml.internal.bind.v2.model.core.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username; // unique username
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String gender; // either "f" or "m"
    private String personID; // unique person ID assigned to this user's generated person object
    public String getPrimaryKey() { return getUsername(); }
}
