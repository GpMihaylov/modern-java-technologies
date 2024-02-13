package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure;

public enum TreasureType {
    WEAPON("weapon"),
    HEALTH_POTION("health potion"),
    MANA_POTION("mana potion");

    private final String symbol;

    TreasureType(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
