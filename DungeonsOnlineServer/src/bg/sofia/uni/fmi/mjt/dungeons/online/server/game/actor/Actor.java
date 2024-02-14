package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Stats;

public interface Actor {

    void loseHealth(int amount);

    void levelUp();

    void initBaseStats();

    void updateStats();

    Position getPosition();

    Stats getStats();

    void setPosition(Position newPosition);

}
