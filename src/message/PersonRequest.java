package message;

/**
 * Person Request Body:
 * {
 * "personid": "xxxxxxxx"
 * "authtoken": "xxxxxxxx"
 * }
 */
public class PersonRequest {
    private String personid;
    private String authtoken;

    public PersonRequest(String personid, String authtoken) {
        this.personid = personid;
        this.authtoken = authtoken;
    }
}
