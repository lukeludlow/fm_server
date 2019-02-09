package message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clear Success Response Body:
 * {
 * “message”: “Clear succeeded.”
 * }
 */
@Data
@NoArgsConstructor
public class ClearResponse {
    private static final String SUCCESS = "clear succeeded.";
}
