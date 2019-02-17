package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Fill Request Body:
 * {
 * "userName": "susan", // Non-empty string
 * "generations": 3 // number of generations to generate
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FillRequest extends AbstractRequest {
    private String username;
    private int generations;
}
