package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Minion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field.Field;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field.FieldType;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.weapon.Weapon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DungeonMap {

    private static volatile DungeonMap instance;

    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;
    public static final int MINION_COUNT = 3;
    private static final int BASE_EXPERIENCE_FROM_MINION = 50;

    private final Field[][] map;

    private final Map<String, Player> players;
    private final Map<String, Integer> playerNumbers;
    private final Map<Position, Minion> minions;
    private final Map<Position, Treasure> treasure;

    private DungeonMap() {
        map = new Field[WIDTH][HEIGHT];
        initMap();
        players = new HashMap<>();
        playerNumbers = new HashMap<>();
        minions = new HashMap<>();
        treasure = new HashMap<>();

        initMinions();
        initTreasure();
    }

    private void initMinions() {
        //minions.put(new Position(0, 0), new Minion());
    }

    private void initTreasure() {
        Position pos = new Position(0,0);
        treasure.put(pos, new Weapon("sword", pos, 1, 30));
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

    public Field getField(Position p) {
        return map[p.getX()][p.getY()];
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
        validateId(id);
        return players.get(id);
    }

    public Integer getPlayerNumber(String id) {
        validateId(id);
        return playerNumbers.get(id);
    }

    public boolean isObstacle(Position p) {
        return map[p.getX()][p.getY()].getType().equals(FieldType.OBSTACLE);
    }

    public void updatePlayerPosition(String id, Position oldPosition, Position newPosition) {
        validateId(id);
        long playerNumber = playerNumbers.get(id);

        map[newPosition.getX()][newPosition.getY()]
            .setType(FieldType.valueOf("PLAYER" + playerNumber));

        setFieldTypeAfterMovement(oldPosition);
    }

    //todo validation of position

    private void setFieldTypeAfterMovement(Position oldPosition) {
        List<Player> playersOnPosition = getPlayersOnPosition(oldPosition);

        if (!playersOnPosition.isEmpty()) {
            Integer playerNumber = playerNumbers.get(playersOnPosition.getFirst().getId());
            map[oldPosition.getX()][oldPosition.getY()]
                .setType(FieldType.valueOf("PLAYER" + playerNumber));
        } else {
            map[oldPosition.getX()][oldPosition.getY()]
                .setType(FieldType.EMPTY_SPACE);
        }
    }

    public List<Player> getPlayersOnPosition(Position position) {
        return players.values().stream()
            .filter(p -> p.getPosition().equals(position)).toList();
    }

    public boolean isMinionOnPosition(Position position) {
        return minions.containsKey(position);
    }

    public Minion getMinionOnPosition(Position position) {
        return minions.get(position);
    }

    public Stats getPlayerStats(String id) {
        return players.get(id).getStats();
    }

    public void givePlayerExperienceUponMinionDeath(String id, Minion minion) {
        int experience = calculateExperience(minion);

        players.get(id).gainExperience(experience);
    }

    private int calculateExperience(Minion minion) {
        return BASE_EXPERIENCE_FROM_MINION * minion.getStats().getLevel();
    }

    public Treasure getTreasureOnPosition(Position position) {
        return treasure.get(position);
    }

}
