package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Stats;

public class Minion extends AbstractActor {
    private static final int BASE_HEALTH = 20;
    private static final int BASE_DEFENSE = 5;
    private static final int BASE_LEVEL = 1;

    public Minion(Position position) {
        initBaseStats();
        this.position = new Position(position.getX(), position.getY());
    }

    @Override
    protected void initBaseStats() {
        stats = new Stats(BASE_HEALTH, BASE_DEFENSE, BASE_LEVEL);
    }

    @Override
    public void loseHealth(int amount) {
        stats.setHealth(stats.getHealth() + stats.getDefense() - amount);

        if (isDead()) {
            handleDeath();
        }
    }

    public boolean isDead() {
        return stats.getHealth() <= 0;
    }

    private void handleDeath() {
        stats.setHealth(BASE_HEALTH);
        levelUp();
        //todo random position
    }

    @Override
    public void levelUp() {
        stats.setLevel(stats.getLevel() + 1);
        updateStats();
    }

    @Override
    protected void updateStats() {
        int level = stats.getLevel();
        stats.setHealth(stats.getHealth() + level);
        stats.setDefense(stats.getDefense() + level);
    }

}
