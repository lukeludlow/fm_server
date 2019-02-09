package message;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.Event;

/**
 * History Success Response Body:
 * {
 * "data": [ Array of Event objects ]
 * }
 */
@Data
@NoArgsConstructor
public class HistoryResponse {
    private model.Event[] data;
}
