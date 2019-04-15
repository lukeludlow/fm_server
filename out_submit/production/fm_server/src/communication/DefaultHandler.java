package communication;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultHandler extends AbstractHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!isGet(exchange)) {
            return;
        }

        try {
            URI uri = exchange.getRequestURI();
            String pathName = Paths.get("").toAbsolutePath().toString() + "/web" + uri.getPath();
            if (uri.getPath().equals("/")) {
                pathName += "index.html";
            }
            Path path = FileSystems.getDefault().getPath(pathName);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            Files.copy(path, exchange.getResponseBody());
            exchange.getResponseBody().close();
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            System.err.println(e);
        }

    }

}
