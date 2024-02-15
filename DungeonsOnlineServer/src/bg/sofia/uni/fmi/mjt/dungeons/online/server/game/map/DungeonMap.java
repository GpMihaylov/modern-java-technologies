package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.PlayerNotFoundException;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.ObstaclePositions.OBSTACLE_COORDINATES;

public class DungeonMap {
    private static volatile DungeonMap instance;

    public static final int WIDTH = 9;
    public static final int HEIGHT = 9;
    public static final int MINION_COUNT = 6;
    public static final int ITEMS_COUNT = 9;
    private static final int BASE_EXPERIENCE_FROM_MINION = 100;
    private static final int MIN_ITEM_LEVEL = 1;
    private static final int MAX_ITEM_LEVEL = 5;
    private static final int BASE_WEAPON_ATTACK = 30;
    private static final int BASE_POTION_COST = 20;
    private static final int BASE_POTION_AMOUNT = 30;
    private static final int MODIFIER = 5;

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
        players = new LinkedHashMap<>();
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
                map[i][j] = Field.of(FieldType.EMPTY_SPACE);
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
            setObstacle(new Position(x, y));
        }
    }

    private void setObstacle(Position position) {
        map[position.x()][position.y()] = Field.of(FieldType.OBSTACLE);
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
        map[position.x()][position.y()] = Field.of(FieldType.MINION);
    }

    private void initTreasure() {
        for (int i = 0; i < ITEMS_COUNT; i++) {
            Position itemPosition = getRandomUnoccupiedPosition();
            setRandomTreasureOnPosition(itemPosition);
        }
    }

    private void setRandomTreasureOnPosition(Position position) {
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
        map[position.x()][position.y()] = Field.of(FieldType.TREASURE);
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
        return rand.nextInt(MIN_ITEM_LEVEL, MAX_ITEM_LEVEL);
    }

    public Field getField(Position p) {
        return map[p.x()][p.y()];
    }

    public void addPlayer(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Player id must not be null");
        }
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
            throw new IllegalArgumentException("This player does not exist");
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

    public Player getPlayerFromNumber(int num) throws PlayerNotFoundException {
        for (Map.Entry<String, Integer> entry : playerNumbers.entrySet()) {
            if (entry.getValue() == num) {
                return getPlayer(entry.getKey());
            }
        }
        throw new PlayerNotFoundException("Cannot find player with this number");
    }

    public boolean isObstacle(Position position) {
        return map[position.x()][position.y()].getType().equals(FieldType.OBSTACLE);
    }

    public void updatePlayerPosition(String id, Position oldPosition, Position newPosition) {
        validateId(id);
        int playerNumber = playerNumbers.get(id);

        map[newPosition.x()][newPosition.y()]
            .setType(FieldType.valueOf("PLAYER" + playerNumber));

        setFieldTypeAfterMovement(oldPosition);
    }

    private void setFieldTypeAfterMovement(Position oldPosition) {
        List<Player> playersOnPosition = getPlayersOnPosition(oldPosition);

        if (!playersOnPosition.isEmpty()) {
            Integer playerNumber = playerNumbers.get(playersOnPosition.getFirst().getId());
            map[oldPosition.x()][oldPosition.y()]
                .setType(FieldType.valueOf("PLAYER" + playerNumber));
        } else if (isMinionOnPosition(oldPosition)) {
            map[oldPosition.x()][oldPosition.y()].setType(FieldType.MINION);
        } else if (isTreasureOnPosition(oldPosition)) {
            map[oldPosition.x()][oldPosition.y()].setType(FieldType.TREASURE);
        } else {
            map[oldPosition.x()][oldPosition.y()]
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
        validateId(id);
        return players.get(id).getStats();
    }

    public void givePlayerExperienceUponMinionDeath(String id, Minion minion) {
        validateId(id);
        int experience = calculateExperience(minion);
        players.get(id).gainExperience(experience);
    }

    private int calculateExperience(Minion minion) {
        return BASE_EXPERIENCE_FROM_MINION * minion.getStats().getLevel();
    }

    public Treasure getTreasureOnPosition(Position position) throws NonexistentItemException {
        if (isTreasureOnPosition(position)) {
            return treasure.get(position);
        } else {
            throw new NonexistentItemException("No treasure on this position");
        }
    }

    public void removeItem(Treasure item) {
        Position position = item.getPosition();
        treasure.remove(position);
        map[position.x()][position.y()].setType(FieldType.TREASURE);

        restorePlayerIconOnPosition(position);
    }

    private void restorePlayerIconOnPosition(Position position) {
        for (Player player :
            players.values()) {
            if (position.equals(player.getPosition())) {
                map[position.x()][position.y()].setType(
                    FieldType.valueOf("PLAYER" + getPlayerNumber(player.getId())));
                break;
            }
        }
    }

    public void randomizeMinionPosition(Minion minion) {
        minions.remove(minion.getPosition());
        minion.setPosition(getRandomUnoccupiedPosition());
        Position position = minion.getPosition();
        minions.put(position, minion);
        map[position.x()][position.y()].setType(FieldType.MINION);
        restorePlayerIconOnPosition(position);
    }

    public void placeItemOnPosition(Treasure item, Position position) {
        treasure.put(position, item);
        map[position.x()][position.y()] = Field.of(FieldType.TREASURE);
        restorePlayerIconOnPosition(position);
    }
}
