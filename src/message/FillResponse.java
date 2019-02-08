package message;

/**
 * Fill Success Response Body:
 * {
 * “message”: “Successfully added X persons and Y events to the database.”
 * }
 */
public class FillResponse {
    private String successMessage;

    public FillResponse(String successMessage) {
        this.successMessage = successMessage;
    }
}
