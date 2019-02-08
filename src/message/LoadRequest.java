package message;

import model.Event;
import model.Person;
import model.User;

/**
 * Load Request Body:
 * {
 * “users”: [ Array of User objects ],
 * “persons”: [ Array of Person objects ],
 * “events”: [ Array of Event objects ]
 * }
 */
public class LoadRequest {
    private model.User[] users;
    private model.Person[] persons;
    private model.Event[] events;

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }
}
