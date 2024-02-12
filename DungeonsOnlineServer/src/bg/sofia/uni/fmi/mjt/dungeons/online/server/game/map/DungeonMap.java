package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field.Field;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field.FieldType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DungeonMap {

    private static volatile DungeonMap instance;

    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;

    private final Field[][] map;

    private Map<String, Player> players;
    private Map<String, Integer> playerNumbers;

    private DungeonMap() {
        map = new Field[WIDTH][HEIGHT];
        initMap();
        players = new HashMap<>();
        playerNumbers = new HashMap<>();
    }

    public static DungeonMap getInstance() {
        if (instance == null) {
            synchronized (DungeonMap.class) {
                if (instance == null) {
                    instance = new DungeonMap();
                }
            }
        }
        return instance;
    }

    private void initMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                map[i][j] = new Field();
            }
        }
    }

    public String getFieldSymbol(int x, int y) {
        return map[x][y].toString();
    }

    public void addPlayer(String id) {
        players.put(id, new Player(id));
        playerNumbers.put(id, players.size());

        map[0][0].setType(FieldType.valueOf("PLAYER" + playerNumbers.get(id)));
    }

    public void removePlayer(String id) {
        validateId(id);
        playerNumbers.remove(id);
        players.remove(id);
    }

    private void validateId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Player id must not be null");
        }
        if (!players.containsKey(id)) {
            //todo exception
        }
    }

    public Player getPlayer(String id) {
        return players.get(id);
    }

    public boolean isObstacle(int x, int y) {
        return map[x][y].getType().equals(FieldType.OBSTACLE);
    }

    public void updatePlayerPosition(String id, Position oldPosition, Position newPosition) {
        validateId(id);
        long playerNumber = playerNumbers.get(id);

        map[newPosition.getX()][newPosition.getY()]
            .setType(FieldType.valueOf("PLAYER" + playerNumber));

        setFieldTypeAfterMovement(oldPosition);
    }

    private void setFieldTypeAfterMovement(Position oldPosition) {
        Optional<Player> player = players.values().stream()
            .filter(p -> p.getPosition().equals(oldPosition))
            .findFirst();

        if (player.isPresent()) {
            Integer playerNumber = playerNumbers.get(player.get().getId());
            map[oldPosition.getX()][oldPosition.getY()]
                .setType(FieldType.valueOf("PLAYER" + playerNumber));
        } else {
            map[oldPosition.getX()][oldPosition.getY()]
                .setType(FieldType.EMPTY_SPACE);
        }
    }

}
