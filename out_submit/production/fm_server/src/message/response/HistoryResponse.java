package message.response;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class HistoryResponse extends AbstractResponse {
    private Event[] data;
}
