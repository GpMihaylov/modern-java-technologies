package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map;

public class MapVisualizer {

    public static String getMapAsString() {
        StringBuilder mapString = new StringBuilder();

        for (int i = 0; i < DungeonMap.WIDTH; i++) {
            for (int j = 0; j < DungeonMap.HEIGHT; j++) {

                mapString.append(DungeonMap.getInstance().getFieldSymbol(i, j)).append(" ");
            }
            mapString.append(System.lineSeparator());
        }

        return mapString.toString();
    }

}
