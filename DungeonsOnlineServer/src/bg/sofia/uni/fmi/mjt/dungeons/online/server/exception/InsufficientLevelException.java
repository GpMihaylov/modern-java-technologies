package bg.sofia.uni.fmi.mjt.dungeons.online.server.exception;

public class InsufficientLevelException extends Exception {

    public InsufficientLevelException(String message) {
        super(message);
    }

    public InsufficientLevelException(String message, Throwable cause) {
        super(message, cause);
    }

}
