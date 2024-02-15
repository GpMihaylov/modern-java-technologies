package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.weapon;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.TreasureType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeaponTest {

    @Test
    void testGetAttack() {
        Weapon weapon = new Weapon("Sword", new Position(0, 0), 1, 10);
        assertEquals(12, weapon.getAttack());
    }

    @Test
    void testGetType() {
        Weapon weapon = new Weapon("Sword", new Position(0, 0), 1, 10);
        assertEquals(TreasureType.WEAPON, weapon.getType());
    }
}
