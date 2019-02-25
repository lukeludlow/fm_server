package communication;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import message.request.EventRequest;
import message.response.EventResponse;
import message.response.ResponseException;
import service.EventService;

import java.io.IOException;

public class EventHandler extends AbstractHandler<EventResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.printf("get event handler!");
        if (!isGet(exchange)) {
            return;
        }
        printSuccess();

        System.out.printf("reading eventID...");
        String requestUri = exchange.getRequestURI().toString();
        requestUri = requestUri.replaceFirst("/","");
        String[] segments = requestUri.split("/");
        if (segments.length != 2 || !segments[0].equals("event")) {
            sendErrorResponse(exchange, new ResponseException("invalid eventID parameter"));
            return;
        }
        String eventID = segments[1];
        printSuccess();

        System.out.printf("reading headers...");
        Headers requestHeaders = exchange.getRequestHeaders();
        if (!requestHeaders.containsKey("Authorization")) {
            sendErrorResponse(exchange, new ResponseException("request header missing authorization"));
            return;
        }
        String authtoken = requestHeaders.getFirst("Authorization");
        printSuccess();

        System.out.printf("calling get event service...");
        EventRequest request = new EventRequest(eventID, authtoken);
        EventService service = new EventService();
        EventResponse response = null;
        try {
            response = service.getEvent(request);
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
