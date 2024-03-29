package data;

import model.Event;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventDao extends Dao<Event> {

    public EventDao(Database db) {
        super(Event.class, db);
        this.db = db;
        this.setSqlStatements();
    }

    private void setSqlStatements() {
        this.insertSql = "insert into event " +
                "(descendant, event_id, person_id, latitude, longitude, country, city, event_type, year) " +
                "values (?,?,?,?,?,?,?,?,?)";
        this.findSql = "select * from event where event_id = ?";
        this.findManySql = "select * from event where descendant = ?";
        this.deleteSql = "delete from event where event_id = ?";
        this.deleteManySql = "delete from event where descendant = ?";
    }

    @Override
    protected Event getObject(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new Event(
                    rs.getString("descendant"),
                    rs.getString("event_id"),
                    rs.getString("person_id"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getString("country"),
                    rs.getString("city"),
                    rs.getString("event_type"),
                    rs.getInt("year")
            );
        }
        return null;
    }
}
