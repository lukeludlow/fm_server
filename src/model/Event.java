package model;

/**
 * event object model
 */
public class Event {
    /** unique identifier for this event */
    private int eventID;
    /** user (username) to which this person belongs, possibly null */
    private String descendant;
    /** ID of person to which this event belongs */
    private int personID;
    /** latitude of event’s location. format: ###.###### */
    private double latitude;
    /** longitude of event’s location. format: ###.###### */
    private double longitude;
    /** country: Country in which event occurred */
    private String country;
    /** city in which event occurred */
    private String city;
    /** type of event (birth, baptism, christening, marriage, death, etc.) */
    private String eventType;
    /** year in which event occurred. 4 digits only. negative nums can represent b.c. */
    private int year;

    /** parameterized constructor */
    public Event(int eventID, String descendant, int personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }
    /** constructor for event with no user */
    public Event(int eventID, int personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }
    /** default constructor */
    public Event() { return; }

    public int getEventID() { return eventID; }
    public void setEventID(int eventID) { this.eventID = eventID; }
    public String getDescendant() { return descendant; }
    public void setDescendant(String descendant) { this.descendant = descendant; }
    public int getPersonID() { return personID; }
    public void setPersonID(int personID) { this.personID = personID; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}

