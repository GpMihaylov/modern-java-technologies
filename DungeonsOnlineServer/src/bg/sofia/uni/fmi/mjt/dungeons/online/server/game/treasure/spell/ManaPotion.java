package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell.Spell;

public class ManaPotion extends Spell {

    int manaPoints;

    public ManaPotion(int level, int manaCost, int manaPoints) {
        super(level, manaCost);
        this.manaPoints = manaPoints;
    }

    @Override
    public int use() {
        return manaPoints;
    }
}
