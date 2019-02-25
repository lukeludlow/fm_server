package communication;

import com.sun.net.httpserver.HttpExchange;
import message.response.FamilyResponse;

import java.io.IOException;

public class FamilyHandler extends AbstractHandler<FamilyResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("family handler!");
    }
}
