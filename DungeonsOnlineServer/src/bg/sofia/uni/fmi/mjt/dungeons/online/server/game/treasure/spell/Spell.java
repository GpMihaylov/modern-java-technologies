package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;

public abstract class Spell extends Treasure {

    private final int manaCost;

    public Spell(String name, Position position, int level, int manaCost) {
        super(name, position, level);
        this.manaCost = manaCost;
    }

    public int getManaCost() {
        return manaCost;
    }
}
