package communication;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import message.response.ErrorResponse;
import message.response.ResponseException;

import java.io.*;
import java.net.HttpURLConnection;

public abstract class AbstractHandler<T> implements HttpHandler {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[92m";
    public static final String ANSI_RED = "\u001B[91m";

    public void sendResponse(HttpExchange exchange, T response) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        String message = Encoder.serialize(response);
        OutputStream os = exchange.getResponseBody();
        os.write(message.getBytes());
        os.close();

    }

    public void sendErrorResponse(HttpExchange exchange, ResponseException error) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        System.err.println(error.toString());
        ErrorResponse response = new ErrorResponse(error);
        String message = Encoder.serialize(response);
        OutputStream os = exchange.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }

     public static boolean isPost(HttpExchange exchange) throws IOException {
         System.out.printf("checking request method...");
        if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
            printSuccess();
            return true;
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();
        System.err.println("error: handler expected POST request");
        return false;
    }

    public static boolean isGet(HttpExchange exchange) throws IOException {
        System.out.printf("checking request method...");
        if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
            printSuccess();
            return true;
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();
        System.err.println("error: handler expected GET request");
        return false;
    }

    public static String readRequestBody(HttpExchange exchange) throws IOException {
        System.out.printf("reading request body...");
        InputStream inputStream = exchange.getRequestBody();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static String getAuthorization(HttpExchange exchange) throws IOException {
        System.out.printf("reading authtoken...");
        Headers requestHeaders = exchange.getRequestHeaders();
        if (!requestHeaders.containsKey("Authorization")) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            System.err.println("error: request does not contain authorization");
            return null;
        }
        return requestHeaders.getFirst("Authorization");
    }

    public static void printSuccess() {
        System.out.printf(ANSI_GREEN + "done\n" + ANSI_RESET);
    }

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
