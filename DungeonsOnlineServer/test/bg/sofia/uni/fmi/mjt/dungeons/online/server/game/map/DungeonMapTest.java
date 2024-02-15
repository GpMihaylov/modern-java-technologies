package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.PlayerNotFoundException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Minion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field.Field;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.field.FieldType;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.weapon.Weapon;

import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap.BASE_EXPERIENCE_FROM_MINION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DungeonMapTest {

    private DungeonMap dungeonMap;

    @BeforeEach
    void setUp() {
        dungeonMap = DungeonMap.getInstance();
        dungeonMap.getTreasure().clear();
        dungeonMap.getPlayers().clear();
        dungeonMap.getMinions().clear();
    }

    @Test
    void testGetInstance() {
        assertNotNull(dungeonMap);
    }

    @Test
    void testAddPlayer() {
        dungeonMap.addPlayer("testPlayer");
        assertNotNull(dungeonMap.getPlayer("testPlayer"));
    }

    @Test
    void testRemovePlayer() {
        dungeonMap.removePlayer("testPlayer");
        assertNull(dungeonMap.getPlayer("testPlayer"));
    }

    @Test
    void testAddPlayerInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> dungeonMap.addPlayer(null));
    }

    @Test
    void testRemovePlayerInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> dungeonMap.removePlayer(null));
    }

    @Test
    void testGetPlayer() {
        dungeonMap.addPlayer("testPlayer");
        assertNotNull(dungeonMap.getPlayer("testPlayer"));
    }

    @Test
    void testGetPlayerNumber() {
        assertEquals(1, dungeonMap.getPlayerNumber("testPlayer"));
    }

    @Test
    void testGetPlayerFromNumber() throws PlayerNotFoundException {
        dungeonMap.addPlayer("testPlayer");
        assertEquals("testPlayer", dungeonMap.getPlayerFromNumber(1).getId());
    }

    @Test
    void testGetPlayerStats() {
        dungeonMap.addPlayer("testPlayer");
        Stats stats = dungeonMap.getPlayerStats("testPlayer");
        assertNotNull(stats);
        assertEquals(Player.BASE_HEALTH, stats.getHealth());
        assertEquals(Player.BASE_MANA, stats.getMana());
        assertEquals(Player.BASE_ATTACK, stats.getAttack());
        assertEquals(Player.BASE_DEFENSE, stats.getDefense());
        assertEquals(Player.BASE_LEVEL, stats.getLevel());
        assertEquals(Player.BASE_EXPERIENCE, stats.getExperience());
        assertEquals(Player.BASE_EXPERIENCE_NEEDED, stats.getXpToNextLevel());
    }

    @Test
    void testIsObstacle() {
        Position obstaclePosition = new Position(0, 1);
        assertTrue(dungeonMap.isObstacle(obstaclePosition));
    }

    @Test
    void testGetFieldPlayer() {
        Position playerPosition = new Position(0, 0);
        dungeonMap.addPlayer("playerId");
        assertEquals(Field.of(FieldType.PLAYER1), dungeonMap.getField(playerPosition));
    }

    @Test
    void testGivePlayerExperienceUponMinionDeath() {
        Player player = mock();
        Minion minion = mock();

        Stats minionStats = new Stats(10, 1, 1);
        when(minion.getStats()).thenReturn(minionStats);

        dungeonMap.getPlayers().put("1", player);
        dungeonMap.getMinions().put(new Position(0,0), minion);
        dungeonMap.givePlayerExperienceUponMinionDeath("1", minion);


        int expectedExperience = BASE_EXPERIENCE_FROM_MINION * minion.getStats().getLevel();
        verify(player).gainExperience(expectedExperience);
    }

    @Test
    void testGetTreasureOnPosition() throws NonexistentItemException {
        Position treasurePosition = new Position(2, 4);
        dungeonMap.getTreasure().put(treasurePosition, mock());
        assertNotNull(dungeonMap.getTreasureOnPosition(treasurePosition));
    }

    @Test
    void testRemoveItemNoTreasure() throws NonexistentItemException {
        assertThrows(NonexistentItemException.class, () -> dungeonMap.getTreasureOnPosition(new Position(2, 4)));
    }

    @Test
    void testRemoveItem() throws NonexistentItemException {
        Position treasurePosition = new Position(2, 4);
        Treasure treasure = mock();
        when(treasure.getPosition()).thenReturn(treasurePosition);
        dungeonMap.placeItemOnPosition(treasure, treasurePosition);
        treasure = dungeonMap.getTreasureOnPosition(treasurePosition);
        dungeonMap.removeItem(treasure);
        assertThrows(NonexistentItemException.class, () -> dungeonMap.getTreasureOnPosition(treasurePosition));
    }

    @Test
    void testPlaceItemOnPosition() {
        Position position = new Position(3, 3);
        Weapon weapon = new Weapon("Sword", position, 1, 10);
        dungeonMap.placeItemOnPosition(weapon, position);
        assertEquals(FieldType.TREASURE, dungeonMap.getField(position).getType());
    }
}
