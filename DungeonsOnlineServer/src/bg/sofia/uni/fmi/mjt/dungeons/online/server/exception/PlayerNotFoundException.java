package bg.sofia.uni.fmi.mjt.dungeons.online.server.exception;

public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
