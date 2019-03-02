package communication;

import com.sun.net.httpserver.HttpExchange;
import message.request.RegisterRequest;
import message.response.RegisterResponse;
import message.response.ResponseException;
import service.RegisterService;

import java.io.IOException;

public class RegisterHandler extends AbstractHandler<RegisterResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("register handler!");
        if (!isPost(exchange)) {
            return;
        }
        System.out.printf("reading request body...");
        String json = readRequestBody(exchange);
        RegisterRequest request = Encoder.deserialize(json, RegisterRequest.class);
        printSuccess();
        System.out.printf("calling register service...");
        RegisterService service = new RegisterService();
        RegisterResponse response;
        try {
            response = service.register(request);
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
