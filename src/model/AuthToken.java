package model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * auth token object model
 */
@Data
@NoArgsConstructor
public class AuthToken {
    /**
     * authorization token generated at login
     */
    private String token;
    /**
     * user (username) to which this token belongs to
     */
    private String username;
}
