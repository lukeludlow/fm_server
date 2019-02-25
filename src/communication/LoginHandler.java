package communication;

import com.sun.net.httpserver.HttpExchange;
import message.request.LoginRequest;
import message.response.LoginResponse;
import message.response.ResponseException;
import service.LoginService;

import java.io.IOException;

public class LoginHandler extends AbstractHandler<LoginResponse> {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("login handler!");

        if (!isPost(exchange)) {
            return;
        }
        printSuccess();

        String json = readRequestBody(exchange);
        if (!json.toLowerCase().contains("\"username\":") || !json.toLowerCase().contains("\"password\":")) {
            sendErrorResponse(exchange, new ResponseException("request body has missing or invalid value"));
            return;
        }
        LoginRequest request = Encoder.deserialize(json, LoginRequest.class);
        printSuccess();

        System.out.printf("calling login service...");
        LoginService loginService = new LoginService();
        LoginResponse response = null;
        try {
            response = loginService.login(request);
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
