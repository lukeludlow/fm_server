package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String authToken; // authorization authToken generated at login
    private String userName; // user (userName) to which this authToken belongs to
    public String getPrimaryKey() { return getAuthToken(); }
}
