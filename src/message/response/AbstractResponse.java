package message.response;

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
    /** success state is set to true by default, since it's true most of the time */
    private boolean success = true;

    /**
     * check if result was a success. if false, we know we're dealing with an error message response
     * @return success
     */
    public boolean success() {
        return this.success;
    }
}
