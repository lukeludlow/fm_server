package model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * event object model
 */
@Data
@NoArgsConstructor
public class Event {
    /**
     * unique identifier for this event
     */
    private String eventID;
    /**
     * user (username) to which this person belongs, possibly null
     */
    private String descendant;
    /**
     * ID of person to which this event belongs
     */
    private String personID;
    /**
     * latitude of event’s location. format: ###.######
     */
    private double latitude;
    /**
     * longitude of event’s location. format: ###.######
     */
    private double longitude;
    /**
     * country: Country in which event occurred
     */
    private String country;
    /**
     * city in which event occurred
     */
    private String city;
    /**
     * type of event (birth, baptism, christening, marriage, death, etc.)
     */
    private String eventType;
    /**
     * year in which event occurred. 4 digits only. negative nums can represent b.c.
     */
    private int year;
}

