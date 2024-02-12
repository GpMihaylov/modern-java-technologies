package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

public abstract class Actor {

    protected Position position;
    protected Stats stats;

    protected abstract void initStats();

    public void loseHealth(int amount) {
        stats.setHealth(stats.getHealth() + stats.getDefense() - amount);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        position.setX(newPosition.getX());
        position.setY(newPosition.getY());
    }

    public Stats getStats() {
        return stats;
    }

}
