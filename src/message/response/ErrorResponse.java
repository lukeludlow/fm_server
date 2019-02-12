package message.response;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ErrorResponse extends AbstractResponse {
    private String errorMessage;
}
