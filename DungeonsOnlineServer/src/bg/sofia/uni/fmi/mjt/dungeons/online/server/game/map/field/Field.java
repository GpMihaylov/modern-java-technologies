package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return type == field.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return type.toString();
    }

}
