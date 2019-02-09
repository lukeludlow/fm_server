package message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error Response Body:
 * {
 * “message”: “Description of the error”
 * }
 */
@Data
@NoArgsConstructor
public class ErrorResponse {
    private String errorMessage;
}
