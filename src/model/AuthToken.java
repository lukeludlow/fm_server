package model;

/**
 * auth token object model
 */
public class AuthToken {
    /** authorization token generated at login */
    private int token;
    /** user (username) to which this token belongs to */
    private String username;

    /**
     * generate new token with random int value
     * @param username user (username) to which this token belongs to
     * @return authtoken
     */
    AuthToken generateAuthToken(String username) {
        return new AuthToken();
    }

    /** parameterized constructor */
    public AuthToken(int token, String username) {
        this.token = token;
        this.username = username;
    }
    /** default constructor */
    public AuthToken() { return; }

    public int getToken() { return token; }
    public void setToken(int token) { this.token = token; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
