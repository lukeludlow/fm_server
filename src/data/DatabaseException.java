package data;

public class DatabaseException extends Exception {
    DatabaseException(String msg) {
        super(msg);
    }
    DatabaseException() {
        super();
    }
}
