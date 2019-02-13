package message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Family Request Body:
 * {
 * "authtoken": "xxxxxxxx"
 * }
 */
@Data
@NoArgsConstructor
public class FamilyRequest {
    private String authtoken;
}
