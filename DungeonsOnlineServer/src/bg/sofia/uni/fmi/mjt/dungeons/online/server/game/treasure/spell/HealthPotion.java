package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell;

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
    public void use(Player player) {
        if (getManaCost() > player.getStats().getMana()
            || getLevel() > player.getStats().getLevel()) {
            //todo
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
