package se.contribe.bookstore.frontend.api;

public class ParseBookException extends RuntimeException {
    public ParseBookException() {
    }

    public ParseBookException(String message) {
        super(message);
    }

    public ParseBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseBookException(Throwable cause) {
        super(cause);
    }
}
