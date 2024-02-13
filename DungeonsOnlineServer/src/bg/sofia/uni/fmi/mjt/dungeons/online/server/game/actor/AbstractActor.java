package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Stats;

public abstract class AbstractActor implements Actor {

    protected Position position;
    protected Stats stats;

    protected abstract void initBaseStats();

    protected abstract void updateStats();

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Stats getStats() {
        return stats;
    }

    @Override
    public void setPosition(Position newPosition) {
        position.setX(newPosition.getX());
        position.setY(newPosition.getY());
    }

}
