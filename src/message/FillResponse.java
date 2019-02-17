package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Person;
import model.Event;

/**
 * Fill Success Response Body:
 * {
 * “message”: “Successfully added X persons and Y events to the database.”
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FillResponse extends AbstractResponse {
    private String message = "successfully added x persons and y events to the database.";
}
