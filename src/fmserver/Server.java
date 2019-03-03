package fmserver;

import com.sun.net.httpserver.HttpServer;
import communication.*;
import data.Database;
import data.DatabaseException;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private HttpServer server;
    private static final int MAX_WAITING_CONNECTIONS = 12;

    public static void main(String[] args) {
        String port = args[0];
        new Server().run(port);
    }

    private void run(String portNumber) {
        System.out.println("initializing http server");
        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        } catch (IOException ex) {
            System.err.println(ex.toString());
            return;
        }
        server.setExecutor(null);
        System.out.println("creating contexts");
        server.createContext("/fill/", new FillHandler());
        server.createContext("/person/", new PersonHandler());
        server.createContext("/person", new FamilyHandler());
        server.createContext("/event/", new EventHandler());
        server.createContext("/event", new HistoryHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/", new DefaultHandler());
        printDone();

        System.out.print("clearing existing authtokens...");
        clearAuthTokens();
        printDone();

        System.out.print("starting server...");
        server.start();
        printDone();
    }

    private static void printDone() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[92m";
        System.out.print(ANSI_GREEN + "done\n" + ANSI_RESET);
    }

    private void clearAuthTokens() {
        Database db = new Database();
        try {
            db.clearAuthTokens();
        } catch (DatabaseException e) {
            System.err.println("unable to clear authtokens. non-fatal error, server will continue.");
        }
    }

}
