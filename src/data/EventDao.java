package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * event database access object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDao {
    /**
     * database connection
     */
    private Database db;

    /**
     * insert event into database
     * @param e new event
     */
    public void insert(Event e) throws DatabaseException {
        try {
            this.db.connect();
            String sql = "insert into event " +
                    "(event_id, descendant, person_id, latitude, longitude, country, city, event_type, year) " +
                    "values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, e.getEventID());
            statement.setString(2, e.getDescendant());
            statement.setString(3, e.getPersonID());
            statement.setDouble(4, e.getLatitude());
            statement.setDouble(5, e.getLongitude());
            statement.setString(6, e.getCountry());
            statement.setString(7, e.getCity());
            statement.setString(8, e.getEventID());
            statement.setInt(9, e.getYear());
            statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException ex) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while inserting event into database");
        } catch (DatabaseException ex) {
            db.closeConnection(false);
            throw ex;
        }
    }

    /**
     * insert many events into database
     * @param events list of new events
     */
    public void insertMany(Event[] events) throws DatabaseException {
        // TODO
    }

    /**
     * find event from database
     * @param eventID event's unique ID
     * @return event object. null if not found.
     */
    public Event find(String eventID) throws DatabaseException{
        try {
            this.db.connect();
            String sql = "select * from event where event_id = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, eventID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Event e = new Event(
                        rs.getString("event_id"),
                        rs.getString("descendant"),
                        rs.getString("person_id"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("event_type"),
                        rs.getInt("year")
                );
                db.closeConnection(true);
                return e;
            } else {
                db.closeConnection(true);
            }
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while finding event");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
        return null;
    }

    /**
     * find many events in the database
     * @param IDs list of events to find
     * @return list of found events. length is the same as input length, entries can be null if not found
     */
    public Event[] findMany(String[] IDs) throws DatabaseException {
        // TODO
        return null;
    }

    /**
     * delete event from database
     * @param eventID event's unique ID
     * @return true if the entry is found and deleted
     */
    public boolean delete(String eventID) throws DatabaseException {
        int deleteCount = 0;
        try {
            this.db.connect();
            String sql = "delete from event where event_id = ?;";
            PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
            statement.setString(1, eventID);
            deleteCount = statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while deleting event");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
        return (deleteCount > 0) ? true : false;
    }

    /**
     * delete many events from database
     * @param IDs list of events to delete
     * @return number of events deleted
     */
    public int deleteMany(String IDs) throws DatabaseException {
        // TODO
        return 0;
    }

}


