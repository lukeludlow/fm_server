package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String authtoken; // authorization authToken generated at login
    private String username; // user (userName) to which this authToken belongs to
    public AuthToken(String userName) {
        this.username = userName;
        this.generateUniqueToken();
    }
    public void generateUniqueToken() {
        UUID uuid = UUID.randomUUID();
        this.authtoken = uuid.toString();
    }
    public String getPrimaryKey() { return getAuthtoken(); }
}
