package message;

/**
 * History Request Body:
 * {
 * "authtoken": "xxxxxxxx"
 * }
 */
public class HistoryRequest {
    private String authtoken;

    public HistoryRequest(String authtoken) {
        this.authtoken = authtoken;
    }
}
