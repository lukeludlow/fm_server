package communication;

import com.sun.net.httpserver.HttpExchange;
import message.request.FillRequest;
import message.response.FillResponse;
import message.response.ResponseException;
import service.FillService;

import java.io.IOException;

public class FillHandler extends AbstractHandler<FillResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("fill handler!");
        if (!isPost(exchange)) {
            return;
        }

        System.out.printf("reading fill parameters...");
        String requestUri = exchange.getRequestURI().toString();
        requestUri = requestUri.replaceFirst("/","");
        String[] segments = requestUri.split("/");
        if ((segments.length != 2 && segments.length != 3) || !segments[0].equals("fill")) {
            sendErrorResponse(exchange, new ResponseException("invalid fill parameters"));
            return;
        }
        String username = segments[1];
        int gen = (segments.length == 2) ? -1 : Integer.parseInt(segments[2]); // -1 for when generations parameter is not given
        printSuccess();
        System.out.printf("calling fill service...");
        FillRequest request = new FillRequest(username, gen);
        FillService service = new FillService();
        FillResponse response;
        try {
            response = service.fill(request);
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
