package communication;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fmserver.Server;
import message.response.ErrorResponse;
import message.response.ResponseException;
import message.request.LoginRequest;
import message.response.LoginResponse;
import service.LoginService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("login handler!");

        System.out.printf("checking request method...");
        if (!exchange.getRequestMethod().toUpperCase().equals("POST")) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            System.err.println("error: login handler expected POST request");
            return;
        }
        System.out.printf(Server.ANSI_GREEN + "done\n" + Server.ANSI_RESET);

        System.out.printf("reading request body...");
        InputStream inputStream = exchange.getRequestBody();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        String json = sb.toString();
        LoginRequest request = Encoder.deserialize(json, LoginRequest.class);
        System.out.printf("done\n");

        System.out.printf("calling login service...");
        LoginService loginService = new LoginService();
        LoginResponse response;
        try {
            response = loginService.login(request);
        } catch (ResponseException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            System.err.println(ex.toString());
            ErrorResponse errorResponse = new ErrorResponse(ex);
            String message = Encoder.serialize(errorResponse);
            OutputStream os = exchange.getResponseBody();
            os.write(message.getBytes());
            os.close();
            return;
        }
        System.out.printf("done\n");
        System.out.println(response);

        System.out.printf("writing response body...");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os = exchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
        System.out.printf("done\n");
        return;

        // no authorization necessary, so don't need to get headers
        // but use this code in my other stuff!
//        Headers headers = exchange.getRequestHeaders();
//        System.out.printf("checking header...");
//        System.out.println(headers.values().toString());
//        if (!(headers.containsKey("userName") && headers.containsKey("password"))) {
//            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//            exchange.getResponseBody().close();
//            System.err.println("error: login request invalid header keys");
//            return;
//        }
//        System.out.printf("done\n");

//        String userName = headers.getFirst("userName");
//        String password = headers.getFirst("password");

    }
}
