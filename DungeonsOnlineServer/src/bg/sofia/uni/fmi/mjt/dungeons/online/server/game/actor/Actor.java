package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

public abstract class Actor {

    protected Position position;
    protected Stats stats;

    protected abstract void initBaseStats();

    public abstract void loseHealth(int amount);

    public abstract void levelUp();

    protected abstract void updateStats();

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
