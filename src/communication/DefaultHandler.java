package communication;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            URI uri = exchange.getRequestURI();
            String pathName = Paths.get("").toAbsolutePath().toString() + "/web" + uri.getPath();
            if (uri.getPath().equals("/")) {
                pathName += "index.html";
            }
            System.out.println("web file path: " + pathName);
            Path path = FileSystems.getDefault().getPath(pathName);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            Files.copy(path, exchange.getResponseBody());
            exchange.getResponseBody().close();
        } catch (IOException ex) {
            // internal server error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            System.err.println(ex);
        }
    }


}
