package message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Fill Success Response Body:
 * {
 * “message”: “Successfully added X persons and Y events to the database.”
 * }
 */
@Data
@NoArgsConstructor
public class FillResponse {
    private String successMessage;
}
