package fmserver;

import com.sun.net.httpserver.HttpServer;
import communication.ClearHandler;
import communication.DefaultHandler;
import communication.LoginHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private HttpServer server;
    private static final int MAX_WAITING_CONNECTIONS = 12;

    public void run(String portNumber) {
        System.out.println("initializing http server");
        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        } catch (IOException ex) {
            System.err.println(ex.toString());
            return;
        }
        server.setExecutor(null);
        System.out.println("creating contexts");

        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/", new DefaultHandler());

        System.out.println("starting server");
        server.start();
        System.out.println("success");
    }

    public static void main(String[] args) {
        String port = args[0];
        new Server().run(port);
    }

}
