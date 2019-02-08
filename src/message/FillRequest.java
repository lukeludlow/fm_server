package message;

/**
 * Fill Request Body:
 * {
 * "userName": "susan", // Non-empty string
 * "generations": 3 // number of generations to generate
 * }
 */
public class FillRequest {
    private String username;
    private int generations;

    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }
}
