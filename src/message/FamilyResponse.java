package message;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.Person;

/**
 * Family Success Response Body:
 * {
 * "data": [ Array of Person objects ]
 * }
 */
@Data
@NoArgsConstructor
public class FamilyResponse {
    private model.Person[] data;
}
