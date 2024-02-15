package bg.sofia.uni.fmi.mjt.dungeons.online.server.exception;

public class InsufficientXpException extends RuntimeException {

    public InsufficientXpException(String message) {
        super(message);
    }

    public InsufficientXpException(String message, Throwable cause) {
        super(message, cause);
    }

}
