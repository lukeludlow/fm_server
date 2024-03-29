package communication;

import com.sun.net.httpserver.HttpExchange;
import message.response.ClearResponse;
import message.response.ResponseException;
import service.ClearService;
import java.io.IOException;

public class ClearHandler extends AbstractHandler<ClearResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("clear handler!");
        if (!isPost(exchange)) {
            return;
        }

        System.out.print("calling clear service...");
        ClearService clearService = new ClearService();
        ClearResponse response = null;
        try {
            response = clearService.clear();
        } catch (ResponseException ex) {
            sendErrorResponse(exchange, ex);
            return;
        }
        printDone();

        System.out.print("sending response...");
        sendResponse(exchange, response);
        printDone();

    }

}
