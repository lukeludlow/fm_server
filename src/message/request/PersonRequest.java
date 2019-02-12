package message.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Person Request Body:
 * {
 * "personid": "xxxxxxxx"
 * "authtoken": "xxxxxxxx"
 * }
 */
@Data
@NoArgsConstructor
public class PersonRequest {
    private String personid;
    private String authtoken;
}
