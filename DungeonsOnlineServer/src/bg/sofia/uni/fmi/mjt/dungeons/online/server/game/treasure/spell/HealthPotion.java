package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.TreasureType;

public class HealthPotion extends Spell {

    private final int healthPoints;

    public HealthPotion(Position position, int level, int manaCost, int healthPoints) {
        super(position, level, manaCost);
        this.healthPoints = healthPoints;
        setType(TreasureType.HEALTH_POTION);
    }

    @Override
    public void use(Player player) {
        if (getManaCost() > player.getStats().getMana()
            || getLevel() > player.getStats().getLevel()) {
            //todo
        }
        int playerHealth = player.getStats().getHealth();
        player.getStats().setHealth(playerHealth + healthPoints);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    @Override
    public String toString() {
        return "Level " + getLevel() +
            " " + getType() + ";\t"
            + "mana cost: " + getManaCost() +
            "; \t health points: " + healthPoints;
    }
}
