package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

public class Stats {

    private int health;
    private int mana;
    private int attack;
    private int defense;

    public Stats(int health, int mana, int attack, int defense) {
        this.health = health;
        this.mana = mana;
        this.attack = attack;
        this.defense = defense;
    }

    public Stats(int health, int defense) {
        this.health = health;
        mana = 0;
        attack = 0;
        this.defense = defense;
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

    //todo validation + dying
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

}
