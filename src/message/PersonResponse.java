package message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Person Success Response Body:
 * {
 * "descendant": "susan", // Name of user account this person belongs to
 * "personID": "7255e93e", // Person’s unique ID
 * "firstName": "Stuart", // Person’s first name
 * "lastName": "Klocke", // Person’s last name
 * "gender": "m", // Person’s gender (“m” or “f”)
 * “father”: “7255e93e” // ID of person’s father [OPTIONAL, can be missing]
 * “mother”: “f42126c8” // ID of person’s mother [OPTIONAL, can be missing]
 * "spouse":"f42126c8" // ID of person’s spouse [OPTIONAL, can be missing]
 * }
 */
@Data
@NoArgsConstructor
public class PersonResponse {
    private String descendant;
    private String personid;
    private String firstname;
    private String lastname;
    private String gender;
    private String fatherid;
    private String motherid;
    private String spouseid;
}
