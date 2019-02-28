package service;

import com.google.gson.stream.JsonReader;
import lombok.Data;
import model.Location;
import model.Person;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Data
public class FillFactory {

    private String descendant;
    private String[] surnames;
    private String[] malenames;
    private String[] femalenames;
    private Location[] locations;
    private final String SURNAMES_PATH = "json/snames.json";
    private final String MALE_NAMES_PATH = "json/mnames.json";
    private final String FEMALE_NAMES_PATH = "json/fnames.json";
    private final String LOCATIONS_PATH = "json/locations.json";

    public FillFactory(String descendant) {
        this.descendant = descendant;
    }

    private void readJsonData() throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(new FileReader(SURNAMES_PATH));
//        this.surnames = Encoder.deserialize("", String[].class);
    }

    public boolean generateParents(Person current) {
        boolean success = false;

        return success;
    }

    private void generatePerson() {

    }

    private String generateUniqueID() {
        return "";
    }

    private void generateBirth() {

    }

    private void generateDeath() {

    }

    private void generateMarriage(Person p1, Person p2) {

    }

    private void matchSpouses(Person p1, Person p2) {

    }


}
