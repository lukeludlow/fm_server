package service;

import message.request.FillRequest;
import message.response.FillResponse;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FillServiceTest {

    User luke;
    FillRequest request;
    FillResponse response;
    FillService service;

    @BeforeEach
    void setUp() throws Exception {
        luke = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
        service = new FillService();
    }
    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testFill0() throws Exception {
        request = new FillRequest("lukeludlow", 0);
        response = service.fill(request);
        System.out.println(response.getMessage());
        String expected = "successfully added 1 people and 1 events to the database.";
        assertEquals(expected, response.getMessage());
    }

    @Test
    void testFill4() throws Exception {
        request = new FillRequest("lukeludlow", 4);
        response = service.fill(request);
        System.out.println(response.getMessage());
        String expected = "successfully added 31 people and 91 events to the database.";
        assertEquals(expected, response.getMessage());
    }


}