package model;

import com.sun.xml.internal.bind.v2.model.core.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String eventID;
    private String descendant; // user (username) to which this person belongs, possibly null
    private String personID; // ID of person to which this event belongs
    private double latitude; // format: ###.######
    private double longitude; // format: ###.######
    private String country;
    private String city;
    private String eventType; // type of event (birth, baptism, christening, marriage, death, etc.)
    private int year;
}

