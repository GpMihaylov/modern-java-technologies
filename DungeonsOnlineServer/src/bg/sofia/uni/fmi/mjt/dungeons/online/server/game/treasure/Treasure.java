package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;

public abstract class Treasure implements Usable {

    private String name;
    private Position position;
    private int level;
    private TreasureType type;

    public Treasure(String name, Position position, int level) {
        this.position = position;
        this.level = level;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public int getLevel() {
        return level;
    }

    public TreasureType getType() {
        return type;
    }

    protected void setType(TreasureType type) {
        this.type = type;
    }
}
