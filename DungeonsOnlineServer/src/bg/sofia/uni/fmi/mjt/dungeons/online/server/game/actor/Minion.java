package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

public class Minion extends Actor {
    private static final int BASE_HEALTH = 20;
    private static final int BASE_DEFENSE = 5;
    private static final int BASE_LEVEL = 1;
    private static final int BASE_EXPERIENCE_AWARD = 50;


    int experienceAward;

    //todo change for level
    @Override
    protected void initBaseStats() {
        stats = new Stats(BASE_HEALTH, BASE_DEFENSE, BASE_LEVEL);
        experienceAward = BASE_LEVEL * BASE_EXPERIENCE_AWARD;
    }

    @Override
    public void loseHealth(int amount) {
        stats.setHealth(stats.getHealth() + stats.getDefense() - amount);

        if (stats.getHealth() <= 0) {
            stats.setHealth(BASE_HEALTH);
            levelUp();
            //todo random position
        }
    }

    @Override
    public void levelUp() {
        if (stats.getExperience() >= stats.getXpToNextLevel()) {
            stats.setLevel(stats.getLevel() + 1);
            updateStats();
        } else {
            //todo exception
        }
    }

    @Override
    protected void updateStats() {
        int level = stats.getLevel();
        stats.setHealth(stats.getHealth() + level);
        stats.setDefense(stats.getDefense() + level);
        experienceAward = BASE_EXPERIENCE_AWARD * level;
    }

    public int getExperienceAward() {
        return experienceAward;
    }
}
