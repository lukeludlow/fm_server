package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Family Request Body:
 * {
 * "authtoken": "xxxxxxxx"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FamilyRequest extends AbstractRequest {
    private String authtoken;
}
