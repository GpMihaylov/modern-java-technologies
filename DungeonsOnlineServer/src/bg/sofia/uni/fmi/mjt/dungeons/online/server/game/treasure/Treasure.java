package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;

import java.util.function.Supplier;

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

    public static Treasure of(TreasureType type, Supplier<Treasure> constructor) {
        return switch (type) {
            case WEAPON, MANA_POTION, HEALTH_POTION -> constructor.get();
            default -> throw new IllegalArgumentException("Invalid treasure type");
        };
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
