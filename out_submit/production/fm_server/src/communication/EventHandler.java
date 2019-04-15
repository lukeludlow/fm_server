package communication;

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

        System.out.print("reading eventID...");
        String requestUri = exchange.getRequestURI().toString();
        requestUri = requestUri.replaceFirst("/","");
        String[] segments = requestUri.split("/");
        if (segments.length != 2 || !segments[0].equals("event")) {
            sendErrorResponse(exchange, new ResponseException("invalid eventID parameter"));
            return;
        }
        String eventID = segments[1];
        printDone();

        System.out.print("reading authtoken...");
        String authtoken = getAuthorization(exchange);
        printDone();

        System.out.print("calling get event service...");
        EventRequest request = new EventRequest(eventID, authtoken);
        EventService service = new EventService();
        EventResponse response;
        try {
            response = service.getEvent(request);
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
