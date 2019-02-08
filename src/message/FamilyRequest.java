package message;

/**
 * Family Request Body:
 * {
 * "authtoken": "xxxxxxxx"
 * }
 */
public class FamilyRequest {
    private String authtoken;

    public FamilyRequest(String authtoken) {
        this.authtoken = authtoken;
    }
}
