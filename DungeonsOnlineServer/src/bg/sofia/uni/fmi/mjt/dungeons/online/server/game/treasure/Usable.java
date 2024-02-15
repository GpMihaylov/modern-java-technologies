package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientLevelException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientManaCostException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.MaxCapacityReachedException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;

public interface Usable {
    void use(Player player) throws InsufficientLevelException, NonexistentItemException, MaxCapacityReachedException,
        InsufficientManaCostException;
}
