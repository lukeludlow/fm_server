package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * History Request Body:
 * {
 * "authtoken": "xxxxxxxx"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HistoryRequest extends AbstractRequest {
    private String authtoken;
}
