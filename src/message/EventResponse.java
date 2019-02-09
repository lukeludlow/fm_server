package message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event Success Response Body:
 * {
 * "descendant": "susan" // Name of user account this event belongs to (non-empty string)
 * "eventID": "251837d7", // Event’s unique ID (non-empty string)
 * "personID": "7255e93e", // ID of the person this event belongs to (non-empty string)
 * "latitude": 65.6833, // Latitude of the event’s location (number)
 * "longitude": -17.9, // Longitude of the event’s location (number)
 * "country": "Iceland", // Name of country where event occurred (non-empty string)
 * "city": "Akureyri", // Name of city where event occurred (non-empty string)
 * "eventType": "birth", // Type of event (“birth”, “baptism”, etc.) (non-empty string)
 * "year": 1912, // Year the event occurred (integer)
 * }
 */
@Data
@NoArgsConstructor
public class EventResponse {
    private String descendant;
    private String eventid;
    private String personid;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
}
