package communication;

import com.sun.net.httpserver.HttpExchange;
import message.request.HistoryRequest;
import message.response.HistoryResponse;
import message.response.ResponseException;
import service.HistoryService;
import java.io.IOException;

public class HistoryHandler extends AbstractHandler<HistoryResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("history handler!");
        if (!isGet(exchange)) {
            return;
        }

        System.out.print("reading authtoken...");
        String authtoken = getAuthorization(exchange);
        printDone();

        System.out.print("calling get history service...");
        HistoryRequest request = new HistoryRequest(authtoken);
        HistoryResponse response = null;
        HistoryService service = new HistoryService();
        try {
            response = service.getHistory(request);
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
