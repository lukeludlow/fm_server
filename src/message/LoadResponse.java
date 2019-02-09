package message;

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
public class LoadResponse {
    private String successMessage;
}
