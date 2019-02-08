package message;

import model.Event;

/**
 * History Success Response Body:
 * {
 * "data": [ Array of Event objects ]
 * }
 */
public class HistoryResponse {
    private model.Event[] data;

    public HistoryResponse(Event[] data) {
        this.data = data;
    }
}
