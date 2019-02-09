package message;

import lombok.Data;
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
public class LoadRequest {
    private model.User[] users;
    private model.Person[] persons;
    private model.Event[] events;
}
