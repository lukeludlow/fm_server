package message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * History Request Body:
 * {
 * "authtoken": "xxxxxxxx"
 * }
 */
@Data
@NoArgsConstructor
public class HistoryRequest {
    private String authtoken;
}
