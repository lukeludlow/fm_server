package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;
import model.Person;
import model.Event;

/**
 * Load Success Response Body:
 * {
 * “message”: “Successfully added X users, Y persons, and Z events to the database.”
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadResponse extends AbstractResponse {
    private String successMessage;
    private User[] users;
    private Person[] people;
    private Event[] events;
}
