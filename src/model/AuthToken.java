package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String authToken; // authorization authToken generated at login
    private String userName; // user (userName) to which this authToken belongs to
    public AuthToken(String userName) {
        this.userName = userName;
        this.generateUniqueToken();
    }
    public void generateUniqueToken() {
        UUID uuid = UUID.randomUUID();
        this.authToken = uuid.toString();
    }
    public String getPrimaryKey() { return getAuthToken(); }
}
