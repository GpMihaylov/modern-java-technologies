package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientXpException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.MaxCapacityReachedException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientLevelException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell.HealthPotion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell.ManaPotion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.weapon.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player.ATTACK_MODIFIER;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player.BASE_ATTACK;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player.BASE_DEFENSE;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player.BASE_EXPERIENCE;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player.BASE_HEALTH;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player.BASE_LEVEL;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player.BASE_MANA;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player.DEFENCE_MODIFIER;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player.HEALTH_MODIFIER;
import static bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player.MANA_MODIFIER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("playerId");
    }

    @Test
    void testUseHealthPotion() throws InsufficientLevelException, NonexistentItemException,
        MaxCapacityReachedException {
        HealthPotion potion = mock();
        when(potion.getHealthPoints()).thenReturn(5);

        assertThrows(NonexistentItemException.class, () -> player.useHealthPotion(potion));

        player.getBackpack().put(potion);
        player.useHealthPotion(potion);
        assertEquals(player.getStats().getHealth(), BASE_HEALTH + potion.getHealthPoints());
        assertTrue(player.getBackpack().isEmpty());
    }

    @Test
    void testUseManaPotion() throws InsufficientLevelException, NonexistentItemException, MaxCapacityReachedException {
        ManaPotion potion = mock();
        when(potion.getManaPoints()).thenReturn(5);

        assertThrows(NonexistentItemException.class, () -> player.useManaPotion(potion));

        player.getBackpack().put(potion);
        player.useManaPotion(potion);
        assertEquals(player.getStats().getMana(), BASE_MANA + potion.getManaPoints());
        assertTrue(player.getBackpack().isEmpty());
    }

    @Test
    void testEquipWeapon() throws InsufficientLevelException, MaxCapacityReachedException, NonexistentItemException {
        Weapon weapon = mock();

        assertThrows(NonexistentItemException.class, () -> player.equipWeapon(weapon));

        player.getBackpack().put(weapon);
        player.equipWeapon(weapon);
        assertTrue(player.getWeapon().isPresent());
        assertEquals(player.getWeapon().get(), weapon);
        assertEquals(player.getStats().getAttack(), BASE_ATTACK + weapon.getAttack());
    }

    @Test
    void testLevelUp() {
        player.getStats().setExperience(player.getStats().getXpToNextLevel());

        player.levelUp();

        assertEquals(BASE_LEVEL + 1, player.getStats().getLevel());
        assertEquals(BASE_EXPERIENCE, player.getStats().getExperience());
    }

    @Test
    void testGainExperience() {
        int experience = 50;

        player.gainExperience(experience);

        assertEquals(BASE_EXPERIENCE + experience, player.getStats().getExperience());
    }

    @Test
    void testLoseHealth() {
        int damage = 20;

        player.loseHealth(damage);

        assertEquals(BASE_HEALTH + BASE_DEFENSE - damage, player.getStats().getHealth());
    }

    @Test
    void testEquipWeaponInsufficientLevel() throws MaxCapacityReachedException {
        Weapon weapon = mock();
        when(weapon.getLevel()).thenReturn(10);
        player.getBackpack().put(weapon);

        assertThrows(InsufficientLevelException.class, () -> player.equipWeapon(weapon));

        assertTrue(player.getWeapon().isEmpty());
        assertEquals(BASE_ATTACK, player.getStats().getAttack());
    }

    @Test
    void testGainNegativeExperience() {
        int negativeExperience = -50;

        assertThrows(IllegalArgumentException.class, () -> player.gainExperience(negativeExperience));

        assertEquals(BASE_EXPERIENCE, player.getStats().getExperience());
    }

    @Test
    void testUseHealthPotionNegativeHealthPoints()
        throws MaxCapacityReachedException {
        HealthPotion potion = mock();
        when(potion.getHealthPoints()).thenReturn(-10);
        player.getBackpack().put(potion);

        assertThrows(IllegalArgumentException.class, () -> player.useHealthPotion(potion));

        assertEquals(BASE_HEALTH, player.getStats().getHealth());
    }

    @Test
    void testDropRandomItemUponDeathNoItems() {
        player.loseHealth(player.getStats().getHealth() + player.getStats().getDefense());

        assertEquals(BASE_HEALTH, player.getStats().getHealth() );

        assertTrue(player.getBackpack().isEmpty());
    }

    @Test
    void testLevelUpIncreasesStats() {
        player.getStats().setExperience(player.getStats().getXpToNextLevel());

        player.levelUp();

        assertEquals(BASE_LEVEL + 1, player.getStats().getLevel());
        assertEquals(BASE_HEALTH + HEALTH_MODIFIER, player.getStats().getHealth());
        assertEquals(BASE_MANA + MANA_MODIFIER, player.getStats().getMana());
        assertEquals(BASE_ATTACK + ATTACK_MODIFIER, player.getStats().getAttack());
        assertEquals(BASE_DEFENSE + DEFENCE_MODIFIER, player.getStats().getDefense());
    }

    @Test
    void testLevelUpDoesNotIncreaseStatsWhenInsufficientExperience() {
        assertThrows(InsufficientXpException.class, player::levelUp);
        assertEquals(BASE_LEVEL, player.getStats().getLevel());
    }

    @Test
    void testGainExperienceIncreasesExperience() {
        int experience = 50;
        int expectedExperience = player.getStats().getExperience() + experience;

        player.gainExperience(experience);

        assertEquals(expectedExperience, player.getStats().getExperience());
    }

    @Test
    void testGainExperienceLevelUp() {
        int experience = player.getStats().getXpToNextLevel() - player.getStats().getExperience();

        player.gainExperience(experience);

        assertEquals(BASE_LEVEL + 1, player.getStats().getLevel());
        assertEquals(0, player.getStats().getExperience());
    }

}
