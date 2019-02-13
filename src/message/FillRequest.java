package message;

import lombok.Data;
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
public class FillRequest {
    private String username;
    private int generations;
}
