package bg.sofia.uni.fmi.mjt.dungeons.online.server.exception;

public class InsufficientManaCostException extends Exception {

    public InsufficientManaCostException(String message) {
        super(message);
    }

    public InsufficientManaCostException(String message, Throwable cause) {
        super(message, cause);
    }

}
