package model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * person object model
 */
@Data
@NoArgsConstructor
public class Person {
    /**
     * unique ID assigned to this person
     */
    private String personID;
    /**
     * user (username) to which this person belongs, possibly null
     */
    private String descendant;
    /**
     * person's first name
     */
    private String firstname;
    /**
     * person's last name
     */
    private String lastname;
    /**
     * user's gender, either "f" or "m"
     */
    private String gender;
    /**
     * ID of person's father, possibly null
     */
    private String fatherID;
    /**
     * ID of person's mother, possibly null
     */
    private String motherID;
    /**
     * ID of person's spouse, possibly null
     */
    private String spouseID;
}
