package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String personID; // unique ID assigned to this person
    private String descendant; // user (userName) to which this person belongs, possibly null
    private String firstname;
    private String lastname;
    private String gender; // either "f" or "m"
    private String father; // ID of person's father, possibly null
    private String mother; // ID of person's mother, possibly null
    private String spouse; // ID of person's spouse, possibly null
    public String getPrimaryKey() { return getPersonID(); }
    public void generateUniqueID() {
        UUID uuid = UUID.randomUUID();
        this.personID = uuid.toString();
    }
}
