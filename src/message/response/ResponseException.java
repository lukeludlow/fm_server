package message.response;

/**
 * Error Response Body:
 * {
 * “message”: “Description of the error”
 * }
 */
public class ResponseException extends Exception {
    public ResponseException() {
        super("description of the error");
    }
    public ResponseException(String s) {
        super(s);
    }
}

