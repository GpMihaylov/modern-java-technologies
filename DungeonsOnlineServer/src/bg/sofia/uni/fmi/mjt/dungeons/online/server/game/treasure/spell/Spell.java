package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;

public abstract class Spell extends Treasure implements Usable {
    private int level;
    private int manaCost;

    public Spell(int level, int manaCost) {
        this.level = level;
        this.manaCost = manaCost;
    }
}
