package message;

/**
 * Error Response Body:
 * {
 * “message”: “Description of the error”
 * }
 */
public class ErrorResponse {
    private String errorMessage;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
