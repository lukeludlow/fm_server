package data;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.Event;

/**
 * event database access object
 */
@Data
@NoArgsConstructor
public class EventDao {
    /**
     * database connection
     */
    private Database db;

    /**
     * insert event to database
     *
     * @param e new event
     * @return true on success
     */
    public boolean add(Event e) {
        return false;
    }

    /**
     * find event from database
     *
     * @param eventID event's unique id
     * @return event object. null if not found.
     */
    public Event get(String eventID) {
        return null;
    }

    /**
     * delete event from database
     *
     * @param eventID event's unique id
     * @return true on success
     */
    public boolean delete(String eventID) {
        return false;
    }

    /**
     * delete event from database
     *
     * @param e event object
     * @return true on success
     */
    public boolean delete(Event e) {
        return false;
    }
}
