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
        System.out.println("login handler!");
        if (!isPost(exchange)) {
            return;
        }
        printSuccess();
        String json = readRequestBody(exchange);
        if (!json.toLowerCase().contains("\"users\":")
                || !json.toLowerCase().contains("\"people\":")
                || !json.toLowerCase().contains("\"events\":")) {
            sendErrorResponse(exchange, new ResponseException("request body has missing or invalid value"));
            return;
        }
        LoadRequest request = Encoder.deserialize(json, LoadRequest.class);
        printSuccess();
        System.out.println("calling load service...");
        LoadService loadService = new LoadService();
        LoadResponse response = null;
        try {
            response = loadService.load(request);
        } catch (ResponseException e) {
            sendErrorResponse(exchange, e);
            return;
        }
        printSuccess();
        System.out.printf("sending response...");
        sendResponse(exchange, response);
        printSuccess();
    }

}
