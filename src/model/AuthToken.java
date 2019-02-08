package model;

/**
 * auth token object model
 */
public class AuthToken {
    /** authorization token generated at login */
    private String token;
    /** user (username) to which this token belongs to */
    private String username;

    public AuthToken(String token, String username) {
        this.token = token;
        this.username = username;
    }
    public AuthToken() { return; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
