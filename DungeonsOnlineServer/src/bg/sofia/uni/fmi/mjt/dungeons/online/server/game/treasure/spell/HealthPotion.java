package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientLevelException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientManaCostException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.TreasureType;

public class HealthPotion extends Spell {

    private final int healthPoints;

    public HealthPotion(String name, Position position, int level, int manaCost, int healthPoints) {
        super(name, position, level, manaCost);
        this.healthPoints = healthPoints + 2 * level;
        setType(TreasureType.HEALTH_POTION);
    }

    @Override
    public void use(Player player)
        throws InsufficientLevelException, NonexistentItemException, InsufficientManaCostException {
        if (getManaCost() > player.getStats().getMana()
            || getLevel() > player.getStats().getLevel()) {
            throw new InsufficientManaCostException("Insufficient mana; cannot use");
        }
        player.useHealthPotion(this);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    @Override
    public String toString() {
        return getName() + "\tLevel " + getLevel() +
            " " + getType() + ";\t"
            + "mana cost: " + getManaCost() +
            "; \t health points: " + healthPoints;
    }
}
