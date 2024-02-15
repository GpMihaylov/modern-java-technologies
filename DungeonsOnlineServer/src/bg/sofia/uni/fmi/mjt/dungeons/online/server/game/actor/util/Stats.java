package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util;

public class Stats {

    private int health;
    private int mana;
    private int attack;
    private int defense;
    private int level;
    private int experience;
    private int xpToNextLevel;

    public Stats(int health, int defense, int level) {
        this.health = health;
        this.defense = defense;
        this.level = level;
    }

    public Stats(int health, int mana, int attack, int defense, int level, int experience, int xpToNextLevel) {
        this.health = health;
        this.mana = mana;
        this.attack = attack;
        this.defense = defense;
        this.level = level;
        this.experience = experience;
        this.xpToNextLevel = xpToNextLevel;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }

    @Override
    public String toString() {
        return "Player stats:"  + System.lineSeparator() +
            " health: " + health +
            "\t mana: " + mana + System.lineSeparator() +
            " attack: " + attack +
            "\t defense: " + defense + System.lineSeparator() +
            " level: " + level +
            "\t experience: " + experience + System.lineSeparator();
    }
}
