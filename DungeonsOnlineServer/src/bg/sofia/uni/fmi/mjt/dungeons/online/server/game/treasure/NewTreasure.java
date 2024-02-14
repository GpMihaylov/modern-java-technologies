package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;

public class NewTreasure {

    private final String name;
    private final Position position;
    private final int level;
    private final TreasureType type;

    private final int attack;
    private final int cost;
    private final int manaPoints;
    private final int healthPoints;

    public static NewTreasureBuilder builder(String name, Position position, int level, TreasureType type) {
        return new NewTreasureBuilder(name, position, level, type);
    }

    private NewTreasure(NewTreasureBuilder builder) {
        this.position = builder.position;
        this.level = builder.level;
        this.name = builder.name;
        this.type = builder.type;
        this.attack = builder.attack;
        this.cost = builder.cost;
        this.manaPoints = builder.manaPoints;
        this.healthPoints = builder.healthPoints;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + "\tlevel " + level +
            " " + type + ";\t");

        if (attack != 0) {
            sb.append("attack: ").append(attack).append("\t");
        }
        if (cost != 0) {
            sb.append("cost: ").append(cost).append("\t");
        }
        if (manaPoints != 0) {
            sb.append("mana points: ").append(manaPoints).append("\t");

        }
        if (healthPoints != 0) {
            sb.append("health points: ").append(healthPoints).append("\t");

        }
        sb.append("\n");

        return sb.toString();
    }

    public static class NewTreasureBuilder {

        private final String name;
        private final Position position;
        private final int level;
        private final TreasureType type;

        private int attack;
        private int cost;
        private int manaPoints;
        private int healthPoints;

        private NewTreasureBuilder(String name, Position position, int level, TreasureType type) {
            this.position = position;
            this.level = level;
            this.name = name;
            this.type = type;
        }

        public NewTreasureBuilder setAttack(int attack) {
            this.attack = attack;
            return this;
        }

        public NewTreasureBuilder setManaPoints(int manaPoints) {
            this.manaPoints = manaPoints;
            return this;

        }

        public NewTreasureBuilder setHealthPoints(int healthPoints) {
            this.healthPoints = healthPoints;
            return this;

        }

        public NewTreasureBuilder setCost(int cost) {
            this.cost = cost;
            return this;
        }

        public NewTreasure build() {
            return new NewTreasure(this);
        }
    }

}
