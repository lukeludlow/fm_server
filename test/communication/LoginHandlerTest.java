package communication;

import fmserver.Server;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.net.HttpURLConnection;
import java.net.URL;

class LoginHandlerTest {

    URL url;
    Server server;
    HttpURLConnection connection;

    @BeforeEach
    void setUp() throws Exception {
        url = new URL("http://localhost:8080/user/login");
        connection = (HttpURLConnection)url.openConnection();
        server = new Server();
        server.run("8080");
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    void testLogin() throws Exception {

    }

    @Test
    void testLoginFail() throws Exception {

    }

    @Test
    void testBadRequest() throws Exception {

    }



//    @Test
//    void testIsPost() throws Exception {
//        connection.setRequestMethod("POST");
//        connection.connect();
//        assertNotEquals(HttpURLConnection.HTTP_BAD_REQUEST, connection.getResponseCode());
//    }
//
//    @Test
//    void testIsPostFail() throws Exception {
//        connection.setRequestMethod("GET");
//        connection.connect();
//        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, connection.getResponseCode());
//    }


}