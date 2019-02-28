package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * simple model to match json for given event location data during fill service
 */
public class Location {
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;
}
