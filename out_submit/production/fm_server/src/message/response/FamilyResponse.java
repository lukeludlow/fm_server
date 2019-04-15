package message.response;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class FamilyResponse extends AbstractResponse {
    private Person[] data;
}
