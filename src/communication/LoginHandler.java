package communication;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fmserver.Server;
import message.ErrorResponse;
import message.LoginRequest;
import message.LoginResponse;
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
        LoginRequest request = Encoder.decode(json, LoginRequest.class);
        System.out.printf("done\n");
        System.out.println(request);

        System.out.printf("calling login service...");
        LoginService loginService = new LoginService();
        LoginResponse response = loginService.login(request);
        System.out.printf("done\n");
        System.out.println(response);

        if (response == null) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            System.err.println("error: login service returned null");
            ErrorResponse errorResponse = new ErrorResponse("login service returned null");
            String error = Encoder.encode(errorResponse);
            OutputStream os = exchange.getResponseBody();
            os.write(error.getBytes());
            os.close();
            return;
        }

        System.out.printf("sending response headers...");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        System.out.printf("done\n");

        System.out.printf("writing response body...");
        OutputStream os = exchange.getResponseBody();
        //os.write(response.toString().getBytes());
        String test = "test";
        os.write(test.getBytes());
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
