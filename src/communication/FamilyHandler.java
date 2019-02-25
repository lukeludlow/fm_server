package communication;

import com.sun.net.httpserver.HttpExchange;
import message.request.FamilyRequest;
import message.response.FamilyResponse;
import message.response.ResponseException;
import service.FamilyService;

import java.io.IOException;

public class FamilyHandler extends AbstractHandler<FamilyResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("family handler!");

        if (!isGet(exchange)) {
            return;
        }

        String authtoken = getAuthorization(exchange);
        if (authtoken == null) {
            return;
        }
        printSuccess();

        System.out.printf("calling get family service...");
        FamilyRequest request = new FamilyRequest(authtoken);
        FamilyResponse response = null;
        FamilyService service = new FamilyService();
        try {
            response = service.getFamily(request);
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
