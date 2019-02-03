package dao;

import model.AuthToken;
import model.Person;

import java.sql.*;

/**
 * person data access object
 * used to connect to database and do stuff with model objects
 */
public class PersonDao {





    public Person generatePerson() {
        return new Person();
    }

}
