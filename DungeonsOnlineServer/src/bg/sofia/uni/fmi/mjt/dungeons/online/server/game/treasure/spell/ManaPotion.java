package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientLevelException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientManaCostException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.TreasureType;

public class ManaPotion extends Spell {

    private final int manaPoints;

    public ManaPotion(String name, Position position, int level, int manaCost, int manaPoints) {
        super(name, position, level, manaCost);
        this.manaPoints = manaPoints + 2 * level;
        setType(TreasureType.MANA_POTION);
    }

    @Override
    public void use(Player player)
        throws InsufficientLevelException, NonexistentItemException, InsufficientManaCostException {
        if (getManaCost() > player.getStats().getMana()
            || getLevel() > player.getStats().getLevel()) {
            throw new InsufficientManaCostException("Insufficient mana; cannot use");
        }
        player.useManaPotion(this);
    }

    public int getManaPoints() {
        return manaPoints;
    }

    @Override
    public String toString() {
        return getName() + "\tLevel " + getLevel() +
            " " + getType() + ";\t"
            + "mana cost: " + getManaCost() +
            "; \t mana points: " + manaPoints;
    }
}
