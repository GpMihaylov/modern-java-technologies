package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell;

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
    public void use(Player player) {
        if (getManaCost() > player.getStats().getMana()
            || getLevel() > player.getStats().getLevel()) {
            //todo exception + handle
        }
        player.gainMana(manaPoints);
    }

    @Override
    public String toString() {
        return getName() + "\tLevel " + getLevel() +
            " " + getType() + ";\t"
            + "mana cost: " + getManaCost() +
            "; \t mana points: " + manaPoints;
    }
}
