package communication;

import com.sun.net.httpserver.HttpExchange;
import message.response.PersonResponse;

import java.io.IOException;

public class PersonHandler extends AbstractHandler<PersonResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("get person handler!");
        if (!isGet(exchange)) {
            return;
        }
        printSuccess();

        // read header
        // authtoken
        // person id

    }

}
