package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field;

public enum FieldType {

    EMPTY_SPACE("."),
    OBSTACLE("#"),
    TREASURE("T"),
    MINION("M"),
    PLAYER1("1"),
    PLAYER2("2"),
    PLAYER3("3"),
    PLAYER4("4"),
    PLAYER5("5"),
    PLAYER6("6"),
    PLAYER7("7"),
    PLAYER8("8"),
    PLAYER9("9");

    private final String symbol;

    FieldType(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static FieldType of(String input) {
        for (FieldType type : FieldType.values()) {
            if (type.symbol.equals(input)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching FieldType for input: " + input);
    }

}
