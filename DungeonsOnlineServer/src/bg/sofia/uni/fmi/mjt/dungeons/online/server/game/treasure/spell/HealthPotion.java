package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell;

public class HealthPotion extends Spell {

    private int healthPoints;

    public HealthPotion(int level, int manaCost, int healthPoints) {
        super(level, manaCost);
        this.healthPoints = healthPoints;
    }

    @Override
    public int use() {
        return healthPoints;
    }

}
