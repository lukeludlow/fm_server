package communication;

import com.sun.net.httpserver.HttpExchange;
import message.request.PersonRequest;
import message.response.PersonResponse;
import message.response.ResponseException;
import service.PersonService;

import java.io.IOException;

public class PersonHandler extends AbstractHandler<PersonResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.printf("get person handler!");
        if (!isGet(exchange)) {
            return;
        }

        System.out.printf("reading personID...");
        String requestUri = exchange.getRequestURI().toString();
        requestUri = requestUri.replaceFirst("/","");
        String[] segments = requestUri.split("/");
        if (segments.length != 2 || !segments[0].equals("person")) {
            sendErrorResponse(exchange, new ResponseException("invalid personID parameter"));
            return;
        }
        String personID = segments[1];
        printDone();

        String authtoken = getAuthorization(exchange);
        if (authtoken == null) {
            sendErrorResponse(exchange, new ResponseException("request missing authorization"));
            return;
        }
        printDone();

        System.out.printf("calling get person service...");
        PersonRequest request = new PersonRequest(personID, authtoken);
        PersonService service = new PersonService();
        PersonResponse response = null;
        try {
            response = service.getPerson(request);
        } catch (ResponseException e) {
            sendErrorResponse(exchange, e);
            return;
        }
        printDone();
        System.out.printf("sending response...");
        sendResponse(exchange, response);
        printDone();
    }

}
