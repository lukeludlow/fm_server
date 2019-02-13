package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String token; // authorization token generated at login
    private String username; // user (username) to which this token belongs to
}
