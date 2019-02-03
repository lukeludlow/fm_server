package model;

/**
 * person object model
 */
public class Person {
    /** unique ID assigned to this person */
    private int personID;
    /** user (username) to which this person belongs, possibly null */
    private String descendant;
    /** person's first name */
    private String firstname;
    /** person's last name */
    private String lastname;
    /** user's gender, either "f" or "m" */
    private String gender;
    /** ID of person's father, possibly null */
    private int fatherID;
    /** ID of person's mother, possibly null */
    private int motherID;
    /** ID of person's spouse, possibly null */
    private int spouseID;

    /** full parameterized constructor */
    public Person(int personID, String descendant, String firstname, String lastname, String gender, int fatherID, int motherID, int spouseID) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }
    /** constructor for lonely people */
    public Person(int personID, String firstname, String lastname, String gender) {
        this.personID = personID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
    }
    /** constructor for lonely people who have an account */
    public Person(int personID, String descendant, String firstname, String lastname, String gender) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
    }
    /** default constructor */
    public Person() { return; }

    public int getPersonID() { return personID; }
    public void setPersonID(int personID) { this.personID = personID; }
    public String getDescendant() { return descendant; }
    public void setDescendant(String descendant) { this.descendant = descendant; }
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public int getFatherID() { return fatherID; }
    public void setFatherID(int fatherID) { this.fatherID = fatherID; }
    public int getMotherID() { return motherID; }
    public void setMotherID(int motherID) { this.motherID = motherID; }
    public int getSpouseID() { return spouseID; }
    public void setSpouseID(int spouseID) { this.spouseID = spouseID; }
}
