package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.inventory;


import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.MaxCapacityReachedException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.weapon.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class BackpackTest {
    private Backpack backpack;

    @BeforeEach
    void setUp() {
        backpack = new Backpack();
    }

    @Test
    void testIsEmptyWhenEmpty() {
        assertTrue(backpack.isEmpty());
    }

    @Test
    void testIsEmptyWhenNotEmpty() throws MaxCapacityReachedException {
        Weapon weapon = mock();
        backpack.put(weapon);

        assertFalse(backpack.isEmpty());
    }

    @Test
    void testPut() throws MaxCapacityReachedException {
        Weapon weapon = mock();

        backpack.put(weapon);

        assertFalse(backpack.isEmpty());
    }

    @Test
    void testPutMaxCapacityReached() throws MaxCapacityReachedException {
        for (int i = 0; i < 10; i++) {
            Weapon weapon = mock();
            backpack.put(weapon);
        }

        assertThrows(MaxCapacityReachedException.class, () -> backpack.put(mock()));
    }

    @Test
    void testContains() throws MaxCapacityReachedException {
        Weapon weapon = mock();
        backpack.put(weapon);

        assertTrue(backpack.contains(weapon));
    }

    @Test
    void testRemove() throws MaxCapacityReachedException, NonexistentItemException {
        Weapon weapon = mock();
        backpack.put(weapon);

        backpack.remove(weapon);

        assertTrue(backpack.isEmpty());
    }

    @Test
    void testRemoveNonexistentItem() {
        Weapon weapon = mock();

        assertThrows(NonexistentItemException.class, () -> backpack.remove(weapon));
    }

    @Test
    void testListAllItems() throws MaxCapacityReachedException {
        Weapon weapon1 = mock();
        Weapon weapon2 = mock();
        backpack.put(weapon1);
        backpack.put(weapon2);

        String result = backpack.listAllItems();

        assertTrue(result.contains(weapon1.toString()));
        assertTrue(result.contains(weapon2.toString()));
    }

    @Test
    void testGetNonexistentItem() {
        assertThrows(NonexistentItemException.class, () -> backpack.get("Nonexistent"));
    }

    @Test
    void testGetRandomItem() throws MaxCapacityReachedException {
        Weapon weapon1 = mock();
        Weapon weapon2 = mock();
        backpack.put(weapon1);
        backpack.put(weapon2);

        Treasure result = backpack.getRandomItem();

        assertTrue(result == weapon1 || result == weapon2);
    }
}
