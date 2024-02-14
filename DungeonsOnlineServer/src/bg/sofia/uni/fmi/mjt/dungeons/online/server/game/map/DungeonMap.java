package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Minion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field.Field;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field.FieldType;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.TreasureType;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell.HealthPotion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell.ManaPotion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.weapon.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_10_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_10_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_11_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_11_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_12_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_12_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_13_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_13_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_14_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_14_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_15_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_15_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_16_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_16_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_17_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_17_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_18_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_18_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_19_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_19_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_1_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_1_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_20_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_20_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_2_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_2_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_3_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_3_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_4_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_4_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_5_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_5_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_6_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_6_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_7_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_7_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_8_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_8_Y;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_9_X;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_9_Y;

public class DungeonMap {
//todo remove picked up items
    //todo use potion are not consumed
    private static volatile DungeonMap instance;

    public static final int WIDTH = 9;
    public static final int HEIGHT = 9;
    public static final int MINION_COUNT = 4;
    public static final int ITEMS_COUNT = 9;
    private static final int BASE_EXPERIENCE_FROM_MINION = 50;
    private static final int MAX_ITEM_LEVEL = 10;
    private static final int BASE_WEAPON_ATTACK = 30;
    private static final int BASE_POTION_COST = 20;
    private static final int BASE_POTION_AMOUNT = 30;
    private static final int MODIFIER = 5;

    private final Field[][] map;

    private final Map<String, Player> players;
    private final Map<String, Integer> playerNumbers;
    private final Map<Position, Minion> minions;
    private final Map<Position, Treasure> treasure;
    private static final int[][] OBSTACLE_COORDINATES = {
        {OBSTACLE_1_X, OBSTACLE_1_Y},
        {OBSTACLE_2_X, OBSTACLE_2_Y},
        {OBSTACLE_3_X, OBSTACLE_3_Y},
        {OBSTACLE_4_X, OBSTACLE_4_Y},
        {OBSTACLE_5_X, OBSTACLE_5_Y},
        {OBSTACLE_6_X, OBSTACLE_6_Y},
        {OBSTACLE_7_X, OBSTACLE_7_Y},
        {OBSTACLE_8_X, OBSTACLE_8_Y},
        {OBSTACLE_9_X, OBSTACLE_9_Y},
        {OBSTACLE_10_X, OBSTACLE_10_Y},
        {OBSTACLE_11_X, OBSTACLE_11_Y},
        {OBSTACLE_12_X, OBSTACLE_12_Y},
        {OBSTACLE_13_X, OBSTACLE_13_Y},
        {OBSTACLE_14_X, OBSTACLE_14_Y},
        {OBSTACLE_15_X, OBSTACLE_15_Y},
        {OBSTACLE_16_X, OBSTACLE_16_Y},
        {OBSTACLE_17_X, OBSTACLE_17_Y},
        {OBSTACLE_18_X, OBSTACLE_18_Y},
        {OBSTACLE_19_X, OBSTACLE_19_Y},
        {OBSTACLE_20_X, OBSTACLE_20_Y}
    };

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
        players = new HashMap<>();
        playerNumbers = new HashMap<>();
        minions = new HashMap<>();
        treasure = new HashMap<>();
        initMap();
    }

    /*
    Map layout:
    0  1  2  3  4  5  6  7  8
 0  .  #  #  .  .  .  .  .  .
 1  .  .  #  .  #  #  #  .  .
 2  .  .  #  .  .  .  .  .  .
 3  .  .  .  #  .  .  #  #  #
 4  .  .  .  #  .  .  .  .  .
 5  .  .  #  .  #  .  .  .  .
 6  .  .  .  .  .  .  .  .  .
 7  .  .  .  .  .  .  #  #  .
 8  .  .  #  #  #  .  #  .  .
     */

    private void initMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                map[i][j] = new Field();
            }
        }
        initObstacles();
        initMinions();
        initTreasure();
    }

    private void initObstacles() {
        for (int[] obstacle : OBSTACLE_COORDINATES) {
            int x = obstacle[0];
            int y = obstacle[1];
            setObstacle(x, y);
        }
    }

    private void setObstacle(int x, int y) {
        map[x][y] = new Field(FieldType.OBSTACLE);
    }

    private void initMinions() {
        for (int i = 0; i < MINION_COUNT; i++) {
            Position minionPosition = getRandomUnoccupiedPosition();
            setMinion(minionPosition);
        }
    }

    private Position getRandomUnoccupiedPosition() {
        Random random = new Random();
        int x;
        int y;
        do {
            x = random.nextInt(DungeonMap.WIDTH);
            y = random.nextInt(DungeonMap.HEIGHT);
        } while (map[x][y].getType() != FieldType.EMPTY_SPACE);

        return new Position(x, y);
    }

    private void setMinion(Position position) {
        minions.put(position, new Minion(position));
        map[position.getX()][position.getY()] = new Field(FieldType.MINION);
    }

    private void initTreasure() {
        for (int i = 0; i < ITEMS_COUNT; i++) {
            Position itemPosition = getRandomUnoccupiedPosition();
            setTreasure(itemPosition);

        }
    }

    private void setTreasure(Position position) {
        Map.Entry<TreasureType, String> itemTypeAndName = generateRandomItemTypeAndName();

        TreasureType itemType = itemTypeAndName.getKey();
        int itemLevel = generateRandomItemLevel();
        String itemName = itemTypeAndName.getValue();

        Treasure newItem = switch (itemType) {
            case WEAPON -> Treasure.of(itemType, () -> new Weapon(itemName, position, itemLevel,
                BASE_WEAPON_ATTACK + itemLevel * MODIFIER));
            case HEALTH_POTION -> Treasure.of(itemType, () -> new HealthPotion(itemName, position, itemLevel,
                BASE_POTION_COST + itemLevel * MODIFIER,
                BASE_POTION_AMOUNT + itemLevel * MODIFIER));
            case MANA_POTION -> Treasure.of(itemType, () -> new ManaPotion(itemName, position, itemLevel,
                BASE_POTION_COST + itemLevel * MODIFIER,
                BASE_POTION_AMOUNT + itemLevel * MODIFIER));
        };
        map[position.getX()][position.getY()] = new Field(FieldType.TREASURE);
        treasure.put(position, newItem);
    }

    private Map.Entry<TreasureType, String> generateRandomItemTypeAndName() {
        Map<TreasureType, String> possibleTypesAndNames = Map.of(TreasureType.WEAPON, "sword",
            TreasureType.MANA_POTION, "mpotion", TreasureType.HEALTH_POTION, "hpotion");

        List<Map.Entry<TreasureType, String>> entryList = new ArrayList<>(possibleTypesAndNames.entrySet());

        Random rand = new Random();
        return entryList.get(rand.nextInt(possibleTypesAndNames.size()));
    }

    private int generateRandomItemLevel() {
        Random rand = new Random();
        return rand.nextInt(MAX_ITEM_LEVEL);
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
