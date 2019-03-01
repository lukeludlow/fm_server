package service;

import communication.Encoder;
import lombok.Data;
import model.Event;
import model.Location;
import model.NameList;
import model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@Data
public class FillFactory {

    private String descendant;
    private NameList surnames;
    private NameList malenames;
    private NameList femalenames;
    private Location[] locations;
    private final String SURNAMES_PATH = "json/snames.json";
    private final String MALE_NAMES_PATH = "json/mnames.json";
    private final String FEMALE_NAMES_PATH = "json/fnames.json";
    private final String LOCATIONS_PATH = "json/locations.json";

    public FillFactory(String descendant) {
        this.descendant = descendant;
        random = new Random();
    }

    public void readData() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(SURNAMES_PATH)));
        this.surnames = Encoder.deserialize(content, NameList.class);
        content = new String(Files.readAllBytes(Paths.get(MALE_NAMES_PATH)));
        this.malenames = Encoder.deserialize(content, NameList.class);
        content = new String(Files.readAllBytes(Paths.get(FEMALE_NAMES_PATH)));
        this.femalenames = Encoder.deserialize(content, NameList.class);
        content = new String(Files.readAllBytes(Paths.get(LOCATIONS_PATH)));
        this.locations = Encoder.deserialize(content, Location[].class);
    }

    public boolean generateParents(Person current) {
        boolean success = false;

        return success;
    }

    private void generatePerson() {

    }

    private String generateUniqueID() {
        return UUID.randomUUID().toString();
    }

    private void generateBirth(Person p) {

    }

    private void generateDeath(Person p) {
        int birthYear = p.getBirthYear();
        int deathYear = getRandomNumberInRange(birthYear + 40, birthYear + 80);
        Event death = new Event();
        death.setEventType("death");
        death.setYear(deathYear);
        death.setPersonID(p.getPersonID());
        death.setDescendant(p.getDescendant());
        death.setLocation(getRandomLocation(this.locations));
    }

    private void generateMarriage(Person p1, Person p2) {

    }

    private void matchSpouses(Person p1, Person p2) {

    }

    private static int getRandommInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    private static Location getRandomLocation(Location[] array) {
        return array[new Random().nextInt(array.length)];
    }


}
