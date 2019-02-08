package message;

import model.Person;

/**
 * Family Success Response Body:
 *  {
 * "data": [ Array of Person objects ]
 * }
 */
public class FamilyResponse {
    private model.Person[] data;

    public FamilyResponse(Person[] data) {
        this.data = data;
    }
}
