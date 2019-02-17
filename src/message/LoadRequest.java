package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.Event;
import model.Person;
import model.User;

/**
 * Load Request Body:
 * {
 * “users”: [ Array of User objects ],
 * “persons”: [ Array of Person objects ],
 * “events”: [ Array of Event objects ]
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoadRequest extends AbstractRequest {
    private model.User[] users;
    private model.Person[] persons;
    private model.Event[] events;
}
