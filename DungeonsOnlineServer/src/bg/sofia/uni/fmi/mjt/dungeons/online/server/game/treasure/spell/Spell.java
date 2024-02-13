package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell;

public abstract class Spell implements Usable {
    private int level;
    private int manaCost;

    public Spell(int level, int manaCost) {
        this.level = level;
        this.manaCost = manaCost;
    }
}
