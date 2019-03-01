package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Event {

    private String eventID;
    private String descendant; // user (username) to which this person belongs, possibly null
    private String personID; // ID of person to which this event belongs
    private double latitude; // format: ###.######
    private double longitude; // format: ###.######
    private String country;
    private String city;
    private String eventType; // type of getEvent (birth, baptism, christening, marriage, death, etc.)
    private int year;

    public Event() {
        setUniqueID();
    }
//    public Event(String descendant) {
//        this.descendant = descendant;
//        setUniqueID();
//    }
//    public Event(String descendant, String personID) {
//        this.descendant = descendant;
//        this.personID = personID;
//        setUniqueID();
//    }
//    public Event(String descendant, String personID) {
//        this.descendant = descendant;
//        this.personID = personID;
//        setUniqueID();
//    }

    private void setUniqueID() {
        UUID uuid = UUID.randomUUID();
        this.eventID = uuid.toString();
    }
    public void setLocation(Location l) {
        this.country = l.getCountry();
        this.city = l.getCity();
        this.latitude = l.getLatitude();
        this.longitude = l.getLongitude();
    }
    public String getPrimaryKey() {
        return getEventID();
    }

}

