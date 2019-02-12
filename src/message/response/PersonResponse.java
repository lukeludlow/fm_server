package message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Person;

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
@AllArgsConstructor
public class PersonResponse extends AbstractResponse {
    private Person person;
}
