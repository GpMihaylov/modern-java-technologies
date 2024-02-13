package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;

public class MapVisualizer {

    public static String getMapAsString() {
        StringBuilder mapString = new StringBuilder();

        for (int i = 0; i < DungeonMap.WIDTH; i++) {
            for (int j = 0; j < DungeonMap.HEIGHT; j++) {
                Position position = new Position(i, j);
                mapString.append(DungeonMap.getInstance().getField(position).toString()).append(" ");
            }
            mapString.append(System.lineSeparator());
        }

        return mapString.toString();
    }

}
