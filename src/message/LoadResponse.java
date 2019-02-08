package message;

/**
 * Load Success Response Body:
 * {
 * “message”: “Successfully added X users, Y persons, and Z events to the database.”
 * }
 */
public class LoadResponse {
    private String successMessage;

    public LoadResponse(String successMessage) {
        this.successMessage = successMessage;
    }
}
