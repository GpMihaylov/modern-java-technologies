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

    public static final int WIDTH = 9;
    public static final int HEIGHT = 9;
    public static final int MINION_COUNT = 4;
    public static final int ITEMS_COUNT = 4;
    private static final int BASE_EXPERIENCE_FROM_MINION = 50;

    private final Field[][] map;

    private final Map<String, Player> players;
    private final Map<String, Integer> playerNumbers;
    private final Map<Position, Minion> minions;
    private final Map<Position, Treasure> treasure;

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

    /*
    0  1  2  3  4  5  6  7  8
0   .  #  #  T  .  .  .  T  .
1   .  T  #  .  #  #  #  .  .
2   T  .  #  .  T  T  .  M  .
3   .  M  .  #  .  .  #  #  #
4   .  .  .  #  .  .  .  .  .
5   .  .  #  T  #  .  T  .  .
6   .  .  .  .  .  .  .  M  .
7   .  T  .  M  .  .  #  #  .
8   .  .  #  #  #  .  #  M  .

     */

    private void initMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                map[i][j] = new Field();
            }
        }
        initObstacles();
    }

    private void initObstacles() {
        map[0][1] = new Field(FieldType.OBSTACLE);
        map[0][2] = new Field(FieldType.OBSTACLE);
        map[1][2] = new Field(FieldType.OBSTACLE);
        map[1][4] = new Field(FieldType.OBSTACLE);
        map[1][5] = new Field(FieldType.OBSTACLE);
        map[1][6] = new Field(FieldType.OBSTACLE);
        map[2][1] = new Field(FieldType.OBSTACLE);
        map[3][3] = new Field(FieldType.OBSTACLE);
        map[3][6] = new Field(FieldType.OBSTACLE);
        map[3][7] = new Field(FieldType.OBSTACLE);
        map[3][8] = new Field(FieldType.OBSTACLE);
        map[4][3] = new Field(FieldType.OBSTACLE);
        map[5][2] = new Field(FieldType.OBSTACLE);
        map[5][4] = new Field(FieldType.OBSTACLE);
        map[7][6] = new Field(FieldType.OBSTACLE);
        map[7][7] = new Field(FieldType.OBSTACLE);
        map[8][2] = new Field(FieldType.OBSTACLE);
        map[8][3] = new Field(FieldType.OBSTACLE);
        map[8][4] = new Field(FieldType.OBSTACLE);
        map[8][6] = new Field(FieldType.OBSTACLE);
    }

    private void initMinions() {

        minions.put(new Position(1, 1), new Minion(new Position(1, 1)));
    }

    private void initTreasure() {
        Position pos = new Position(0,0);
        Position pos2 = new Position(1,2);
        treasure.put(pos, new Weapon("sword", pos, 1, 30));
        treasure.put(pos2, new Weapon("sword", pos2, 1, 30));
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

    public Player getPlayerFromNumber(int num) {
        for (Map.Entry<String, Integer> entry : playerNumbers.entrySet()) {
            if (entry.getValue() == num) {
                return getPlayer(entry.getKey());
            }
        }
        //todo exception?
        return null;
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
        } else if (isMinionOnPosition(oldPosition)) {
            map[oldPosition.getX()][oldPosition.getY()].setType(FieldType.MINION);
        } else if (isTreasureOnPosition(oldPosition)) {
            map[oldPosition.getX()][oldPosition.getY()].setType(FieldType.TREASURE);
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

    private boolean isTreasureOnPosition(Position position) {
        return treasure.containsKey(position);
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
        if (isTreasureOnPosition(position)) {
            return treasure.get(position);
        } else {
            throw new IllegalArgumentException("adsa"); //todo fix exception
        }
    }

}
