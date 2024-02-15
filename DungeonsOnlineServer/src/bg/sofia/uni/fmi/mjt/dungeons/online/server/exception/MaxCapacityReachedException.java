package bg.sofia.uni.fmi.mjt.dungeons.online.server.exception;

public class MaxCapacityReachedException extends Exception {
    public MaxCapacityReachedException(String message) {
        super(message);
    }

    public MaxCapacityReachedException(String message, Throwable cause) {
        super(message, cause);
    }
}
