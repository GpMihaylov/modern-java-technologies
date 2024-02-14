package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.weapon;

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
    public void use(Player player) {
        if (getLevel() > player.getStats().getLevel()) {
            //todo exception + handle
        }
        player.equipWeapon(this); //todo should throw exception
    }

    public int getAttack() {
        return attack;
    }

    @Override
    public String toString() {
        return getName() + "\tLevel " + getLevel() +
            " " + getType() + ";\t"
            + "attack: " + attack + "\n";
    }
}
