package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field;

public class Field {

    private FieldType type;

    public Field(FieldType type) {
        this.type = type;
    }

    public static Field of(FieldType type) {
        return new Field(type);
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString();
    }

}
