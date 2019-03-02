package service;

import communication.Encoder;
import lombok.Data;
import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Data
public class FillFactory {

    private FamilyTree familyTree;
    private String descendant;
    private User user;
    private String[] surnames;
    private String[] malenames;
    private String[] femalenames;
    private Location[] locations;

    public FillFactory(User u) throws IOException {
        this.user = u;
        this.descendant = u.getUsername();
        this.familyTree = new FamilyTree();
        readData();
    }

    public void fill(int generations) {
        Node userNode = createUserNode();
        familyTree.setRoot(userNode);
        familyTree.addNode(userNode);

        // breadth first traversal (kinda)
        // note: i call it "next gen" but technically it's the earlier parent generation
        Queue<Node> currentGen = new LinkedList<>();
        Queue<Node> nextGen = new LinkedList<>();
        nextGen.add(userNode);
        for (int gen = 1; gen <= generations; gen++) {
            currentGen = nextGen;
            nextGen = new LinkedList<>();
            for (Node node : currentGen) {
                generateParents(node);
                familyTree.addNode(node.getMom());
                familyTree.addNode(node.getDad());
                nextGen.add(node.getMom());
                nextGen.add(node.getDad());
            }
        }

    }

    private void generateParents(Node child) {
        Person m = generatePerson("f");
        Person d = generatePerson("m");
        m.setLastname(d.getLastname());
        Node mom = new Node(m);
        Node dad = new Node(d);
        generateBirthday(child, mom);
        generateBirthday(child, dad);
        generateDeathday(child, mom);
        generateDeathday(child, dad);
        generateWeddingday(mom, dad);
        mom.setSpouse(dad);
        dad.setSpouse(mom);
        child.setMom(mom);
        child.setDad(dad);
    }

    private void generateBirthday(Node child, Node parent) {
        assert child.hasEventType("birth");
        int year = child.getEvent("birth").getYear();
        int birthYear = year - randomIntWithinRange(18, 50);
        Event birth = generateEvent("birth", parent.getPersonID());
        birth.setYear(birthYear);
        parent.addEvent("birth", birth);
    }

    private void generateDeathday(Node child, Node parent) {
        assert parent.hasEventType("birth");
        assert child.hasEventType("birth");
        int childBirthYear = child.getEvent("birth").getYear();
        int deathYear = childBirthYear + randomIntWithinRange(10, 50);
        Event death = generateEvent("death", parent.getPersonID());
        death.setYear(deathYear);
        parent.addEvent("death", death);
    }

    private void generateWeddingday(Node p1, Node p2) {
        Event wedding = generateEvent("wedding", p1.getPersonID());
        Event weddingCopy = new Event(wedding);
        weddingCopy.setEventID(randomID());
        weddingCopy.setPersonID(p2.getPersonID());
        assert p1.hasEventType("birth");
        assert p2.hasEventType("birth");
        assert p1.hasEventType("death");
        assert p2.hasEventType("death");
        int p1Birth = p1.getEvent("birth").getYear();
        int p2Birth = p2.getEvent("birth").getYear();
        int p1Death = p1.getEvent("death").getYear();
        int p2Death = p2.getEvent("death").getYear();
        int minDate = Math.min(p1Birth, p2Birth) + 18;
        int maxDate = Math.min(p1Death, p2Death) - 2;
        int weddingDate = randomIntWithinRange(minDate, maxDate);
        wedding.setYear(weddingDate);
        weddingCopy.setYear(weddingDate);
        p1.addEvent("wedding", wedding);
        p2.addEvent("wedding", weddingCopy);
    }


    private Person generatePerson(String gender) throws IllegalArgumentException {
        Person p = new Person();
        if (gender == "m") {
            p.setFirstname(randomNameFrom(malenames));
        } else if (gender == "f") {
            p.setFirstname(randomNameFrom(femalenames));
        } else {
            throw new IllegalArgumentException("generated person's gender must be either m or f");
        }
        p.setLastname(randomNameFrom(surnames));
        p.setDescendant(descendant);
        p.setPersonID(randomID());
        p.setGender(gender);
        return p;
    }

    private Node createUserNode() {
        Person userPerson = new Person();
        userPerson.setUserData(user);
        userPerson.setPersonID(randomID());
        Node userNode = new Node(userPerson);
        // randomly generate a birthdate for the user bc they don't give us that info when they register
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int birthYear = randomIntWithinRange(currentYear - 60, currentYear - 18);
        Event birth = generateEvent("birth", userNode.getPersonID());
        birth.setYear(birthYear);
        userNode.addEvent("birth", birth);
        return userNode;
    }

    private Event generateEvent(String eventType, String personID) {
        Event e = new Event();
        e.setEventID(randomID());
        e.setDescendant(descendant);
        e.setPersonID(personID);
        e.setEventType(eventType);
        e.setYear(69); // TODO
        e.setLocation(randomLocation());
        return e;
    }

    private void readData() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("json/snames.json")));
        NameJson content = Encoder.deserialize(json, NameJson.class);
        this.surnames = content.getData();
        json = new String(Files.readAllBytes(Paths.get("json/mnames.json")));
        content = Encoder.deserialize(json, NameJson.class);
        this.malenames = content.getData();
        json = new String(Files.readAllBytes(Paths.get("json/fnames.json")));
        content = Encoder.deserialize(json, NameJson.class);
        this.femalenames = content.getData();
        json = new String(Files.readAllBytes(Paths.get("json/locations.json")));
        LocationJson locationContent = Encoder.deserialize(json, LocationJson.class);
        this.locations = locationContent.getData();
    }

    private String randomID() {
        return UUID.randomUUID().toString();
    }

    private static int randomIntWithinRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min." + " max: " + max + " min: " + min);
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    private Location randomLocation() {
        return locations[new Random().nextInt(locations.length)];
    }

    private String randomNameFrom(String[] names) {
        return names[new Random().nextInt(names.length)];
    }

}
