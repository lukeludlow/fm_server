package communication;

import com.sun.net.httpserver.HttpExchange;
import message.request.LoadRequest;
import message.response.LoadResponse;
import message.response.ResponseException;
import service.LoadService;
import java.io.IOException;

public class LoadHandler extends AbstractHandler<LoadResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("load handler!");
        if (!isPost(exchange)) {
            return;
        }

        System.out.print("reading load request body...");
        String json = readRequestBody(exchange);
        if (!json.toLowerCase().contains("\"users\":")
                || !json.toLowerCase().contains("\"persons\":")
                || !json.toLowerCase().contains("\"events\":")) {
            sendErrorResponse(exchange, new ResponseException("request body has missing or invalid value"));
            return;
        }
        LoadRequest request = Encoder.deserialize(json, LoadRequest.class);
        printDone();

        System.out.print("calling load service...");
        LoadService loadService = new LoadService();
        LoadResponse response = null;
        try {
            response = loadService.load(request);
        } catch (ResponseException e) {
            sendErrorResponse(exchange, e);
            return;
        }
        printDone();

        System.out.print("sending response...");
        sendResponse(exchange, response);
        printDone();

    }

}
