package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util;

public record Position(int x, int y) {

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }
}
