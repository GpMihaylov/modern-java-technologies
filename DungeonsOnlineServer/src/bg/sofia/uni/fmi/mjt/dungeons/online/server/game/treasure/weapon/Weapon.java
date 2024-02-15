package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.weapon;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientLevelException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientManaCostException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.MaxCapacityReachedException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.TreasureType;

public class Weapon extends Treasure {

    private final int attack;

    public Weapon(String name, Position position, int level, int attack) {
        super(name, position, level);
        this.attack = attack + 2 * level;
        setType(TreasureType.WEAPON);
    }

    @Override
    public void use(Player player)
        throws InsufficientLevelException, NonexistentItemException, MaxCapacityReachedException {
        player.equipWeapon(this);
    }

    public int getAttack() {
        return attack;
    }

    @Override
    public String toString() {
        return getName() + "\tLevel " + getLevel() +
            " " + getType() + ";\t"
            + "attack: " + attack + System.lineSeparator();
    }
}
