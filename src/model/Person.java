package model;

import com.sun.xml.internal.bind.v2.model.core.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String personID; // unique ID assigned to this person
    private String descendant; // user (username) to which this person belongs, possibly null
    private String firstname;
    private String lastname;
    private String gender; // either "f" or "m"
    private String fatherID; // ID of person's father, possibly null
    private String motherID; // ID of person's mother, possibly null
    private String spouseID; // ID of person's spouse, possibly null
    public String getPrimaryKey() { return getPersonID(); }
}
