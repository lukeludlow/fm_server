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

    public static boolean isPost(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
            return true;
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();
        System.err.println("error: login handler expected POST request");
        return false;
    }

    public static void sendResponse(HttpExchange exchange, LoginResponse response) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        String message = Encoder.serialize(response);
        OutputStream os = exchange.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }

    public static void sendErrorResponse(HttpExchange exchange, ResponseException error) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        System.err.println(error.toString());
        ErrorResponse response = new ErrorResponse(error);
        String message = Encoder.serialize(response);
        OutputStream os = exchange.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }

    public static String readRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static void printSuccess() {
        System.out.printf(Server.ANSI_GREEN + "done\n" + Server.ANSI_RESET);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("login handler!");

        System.out.printf("checking request method...");
        if (!isPost(exchange)) {
            return;
        }
        printSuccess();

        System.out.printf("reading request...");
        String json = readRequestBody(exchange);
        LoginRequest request = Encoder.deserialize(json, LoginRequest.class);
        printSuccess();

        System.out.printf("calling login service...");
        LoginService loginService = new LoginService();
        LoginResponse response = null;
        try {
            response = loginService.login(request);
        } catch (ResponseException ex) {
            sendErrorResponse(exchange, ex);
            return;
        }
        printSuccess();

        System.out.printf("writing response body...");
        sendResponse(exchange, response);
        printSuccess();


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
