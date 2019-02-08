package data;

import model.Person;

/**
 * person data access object
 * used to connect to database and do stuff with model objects
 */
public class PersonDao {





    public Person generatePerson() {
        return new Person();
    }

}
