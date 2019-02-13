package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * abstract response is base class for all responses so that
 * any response can also return an error
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractResponse {
    private boolean success = true;
    public boolean success() {
        return this.success;
    }
}
