package bg.sofia.uni.fmi.mjt.dungeons.online.server.exception;

public class NonexistentItemException extends Exception {

    public NonexistentItemException(String message) {
        super(message);
    }

    public NonexistentItemException(String message, Throwable cause) {
        super(message, cause);
    }

}