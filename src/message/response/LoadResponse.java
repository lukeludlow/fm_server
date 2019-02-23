package message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String message = "successfully added x users, y persons, and z events to the database.";
}
