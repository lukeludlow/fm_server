package message.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event Request Body:
 * {
 * "eventid": "xxxxxxxx",
 * "authtoken": "xxxxxxxx"
 * }
 */
@Data
@NoArgsConstructor
public class EventRequest {
    private String eventid;
    private String authtoken;
}
