package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

public class Minion extends Actor {
    private static final int BASE_HEALTH = 100;
    private static final int BASE_DEFENSE = 10;

    //todo change for level
    @Override
    protected void initStats() {
        stats = new Stats(BASE_HEALTH, BASE_DEFENSE);
    }
}
