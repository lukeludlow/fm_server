package model;

/**
 * user object model
 */
public class User {
    /** unique username */
    private String username;
    /** user's password */
    private String password;
    /** user's email address */
    private String email;
    /** user's first name */
    private String firstname;
    /** user's last name */
    private String lastname;
    /** user's gender, either "f" or "m" */
    private String gender;
    /** unique person ID assigned to this user's generated person object */
    private int personID;

    /** parameterized constructor */
    public User(String username, String password, String email, String firstname, String lastname, String gender, int personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.personID = personID;
    }
    /** default constructor */
    public User() { return; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public int getPersonID() { return personID; }
    public void setPersonID(int personID) { this.personID = personID; }
}
