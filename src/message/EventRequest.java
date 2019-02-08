package message;

/**
 * Event Request Body:
 * {
 * "eventid": "xxxxxxxx",
 * "authtoken": "xxxxxxxx"
 * }
 */
public class EventRequest {
    private String eventid;
    private String authtoken;

    public EventRequest(String eventid, String authtoken) {
        this.eventid = eventid;
        this.authtoken = authtoken;
    }
}
