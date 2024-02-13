package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.inventory.Backpack;

public class Player extends Actor {

    private static final int BASE_HEALTH = 100;
    private static final int BASE_MANA = 100;
    private static final int BASE_ATTACK = 10;
    private static final int BASE_DEFENSE = 10;
    private static final int BASE_LEVEL = 1;
    private static final int BASE_EXPERIENCE = 0;
    private static final int BASE_EXPERIENCE_NEEDED = 100;
    private static final int EXPERIENCE_MODIFIER = 2;
    private static final int EXPERIENCE_INCREASE_PER_LEVEL = BASE_EXPERIENCE_NEEDED * EXPERIENCE_MODIFIER;
    private static final int HEALTH_MODIFIER = 10;
    private static final int MANA_MODIFIER = 10;
    private static final int ATTACK_MODIFIER = 5;
    private static final int DEFENCE_MODIFIER = 5;
    private final String id;

    private Backpack backpack;

    public Player(String id) {
        this.id = id;
        position = new Position(0, 0);
        initBaseStats();
        backpack = new Backpack();
    }

    @Override
    protected void initBaseStats() {
        stats = new Stats(BASE_HEALTH, BASE_MANA, BASE_ATTACK, BASE_DEFENSE,
            BASE_LEVEL, BASE_EXPERIENCE, BASE_EXPERIENCE_NEEDED);
    }

    @Override
    public void loseHealth(int amount) {
        if (amount <= 0) {
            //todo exception
        }
        stats.setHealth(stats.getHealth() + stats.getDefense() - amount);

        if (stats.getHealth() <= 0) {
            initBaseStats();
            //drop random item
        }
    }

    @Override
    public void levelUp() {
        if (stats.getExperience() >= stats.getXpToNextLevel()) {
            updateStats();
            stats.setExperience(BASE_EXPERIENCE);
            stats.setXpToNextLevel(EXPERIENCE_INCREASE_PER_LEVEL * stats.getLevel());
            stats.setLevel(stats.getLevel() + 1);
        } else {
            //todo exception
        }
    }

    @Override
    protected void updateStats() {
        stats.setAttack(getStats().getHealth() + HEALTH_MODIFIER);
        stats.setDefense(getStats().getMana() + MANA_MODIFIER);
        stats.setAttack(getStats().getAttack() + ATTACK_MODIFIER);
        stats.setAttack(getStats().getDefense() + DEFENCE_MODIFIER);
    }

    public void gainExperience(int experience) {
        if (experience <= 0) {
            //todo exception
        }
        stats.setExperience(experience);
        if (stats.getExperience() >= stats.getXpToNextLevel()) {
            levelUp();
        }
    }

    public String getId() {
        return id;
    }
}
