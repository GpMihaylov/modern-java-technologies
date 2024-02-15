package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;


import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

public class MinionTest {
    private Minion minion;

    @BeforeEach
    void setUp() {
        minion = new Minion(mock());
    }

    @Test
    void testLoseHealth() {

        Position initialPosition = new Position(0, 0);
        int initialHealth = minion.getStats().getHealth();
        int damage = 10;
        int defence = minion.getStats().getDefense();

        minion.loseHealth(damage);

        assertEquals(initialHealth - damage + defence, minion.getStats().getHealth());
        assertFalse(minion.isDead());
        assertEquals(initialPosition, minion.getPosition());

        minion.loseHealth(minion.getStats().getHealth() + defence);

        assertEquals(Minion.BASE_HEALTH + minion.getStats().getLevel(), minion.getStats().getHealth());
        assertNotEquals(initialPosition, minion.getPosition());
    }

    @Test
    void testIsDead() {
        assertFalse(minion.isDead());
        minion.loseHealth(minion.getStats().getHealth() + minion.getStats().getDefense());
        assertFalse(minion.isDead());
    }

    @Test
    void testLevelUp() {
        int initialLevel = minion.getStats().getLevel();

        minion.levelUp();

        assertEquals(initialLevel + 1, minion.getStats().getLevel());
    }

    @Test
    void testUpdateStats() {
        int initialHealth = minion.getStats().getHealth();
        int initialDefense = minion.getStats().getDefense();
        int initialLevel = minion.getStats().getLevel();

        minion.updateStats();

        assertEquals(initialHealth + initialLevel, minion.getStats().getHealth());
        assertEquals(initialDefense + initialLevel, minion.getStats().getDefense());
    }
}
