package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientLevelException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientXpException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.MaxCapacityReachedException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.inventory.Backpack;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell.HealthPotion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.spell.ManaPotion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.weapon.Weapon;

import java.util.Optional;

public class Player extends AbstractActor {

    public static final int BASE_HEALTH = 100;
    public static final int BASE_MANA = 100;
    public static final int BASE_ATTACK = 10;
    public static final int BASE_DEFENSE = 10;
    public static final int BASE_LEVEL = 1;
    public static final int BASE_EXPERIENCE = 0;
    public static final int BASE_EXPERIENCE_NEEDED = 100;
    public static final int EXPERIENCE_MODIFIER = 2;
    public static final int EXPERIENCE_INCREASE_PER_LEVEL = BASE_EXPERIENCE_NEEDED * EXPERIENCE_MODIFIER;
    public static final int HEALTH_MODIFIER = 10;
    public static final int MANA_MODIFIER = 10;
    public static final int ATTACK_MODIFIER = 5;
    public static final int DEFENCE_MODIFIER = 5;

    public static final String NO_ITEM = "No such item in backpack";

    private final String id;

    private final Backpack backpack;
    private Optional<Weapon> weapon;

    public Player(String id) {
        this.id = id;
        position = new Position(0, 0);
        backpack = new Backpack();
        weapon = Optional.empty();
        initBaseStats();
    }

    @Override
    public void initBaseStats() {
        stats = new Stats(BASE_HEALTH, BASE_MANA, BASE_ATTACK, BASE_DEFENSE,
            BASE_LEVEL, BASE_EXPERIENCE, BASE_EXPERIENCE_NEEDED);
    }

    @Override
    public void loseHealth(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Cannot lose negative amount of health");
        }
        stats.setHealth(stats.getHealth() + stats.getDefense() - amount);

        if (stats.getHealth() <= 0) {
            initBaseStats();
            dropRandomItem();
        }
    }

    private void dropRandomItem() {
        if (!backpack.isEmpty()) {
            Treasure itemToBeRemoved = backpack.getRandomItem();

            DungeonMap.getInstance().placeItemOnPosition(itemToBeRemoved, itemToBeRemoved.getPosition());
        }
    }

    @Override
    public void levelUp() {
        if (stats.getExperience() >= stats.getXpToNextLevel()) {
            updateStats();
            stats.setExperience(BASE_EXPERIENCE);
            stats.setXpToNextLevel(EXPERIENCE_INCREASE_PER_LEVEL * stats.getLevel());
            stats.setLevel(stats.getLevel() + 1);
        } else {
            throw new InsufficientXpException("Not enough experience to level up");
        }
    }

    @Override
    public void updateStats() {
        stats.setHealth(getStats().getHealth() + HEALTH_MODIFIER);
        stats.setMana(getStats().getMana() + MANA_MODIFIER);
        stats.setAttack(getStats().getAttack() + ATTACK_MODIFIER);
        stats.setDefense(getStats().getDefense() + DEFENCE_MODIFIER);
    }

    public void useHealthPotion(HealthPotion potion) throws InsufficientLevelException, NonexistentItemException {
        handleNullObjects(potion);
        validateItemLevel(potion);

        if (backpack.contains(potion)) {
            heal(potion.getHealthPoints());
            backpack.remove(potion);
        } else {
            throw new NonexistentItemException(NO_ITEM);
        }
    }

    public void useManaPotion(ManaPotion potion) throws InsufficientLevelException, NonexistentItemException {
        handleNullObjects(potion);
        validateItemLevel(potion);

        if (backpack.contains(potion)) {
            gainMana(potion.getManaPoints());
            backpack.remove(potion);
        } else {
            throw new NonexistentItemException(NO_ITEM);
        }
    }

    private void heal(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Health gain must be positive");
        }
        int maxHealth = BASE_HEALTH + stats.getLevel() * HEALTH_MODIFIER;
        stats.setHealth(Math.min(stats.getHealth() + amount, maxHealth));
    }

    private void gainMana(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Mana gain must be positive");
        }
        int maxMana = BASE_MANA + stats.getLevel() * MANA_MODIFIER;
        stats.setMana(Math.min(stats.getMana() + amount, maxMana));
    }

    public void equipWeapon(Weapon weapon)
        throws InsufficientLevelException, NonexistentItemException, MaxCapacityReachedException {
        validateItemLevel(weapon);

        if (backpack.contains(weapon)) {
            sendCurrentWeaponToBackpack();

            backpack.remove(weapon);
            this.weapon = Optional.of(weapon);
            stats.setAttack(stats.getAttack() + weapon.getAttack());
        } else {
            throw new NonexistentItemException(NO_ITEM);
        }
    }

    private void validateItemLevel(Treasure item) throws InsufficientLevelException {
        if (item.getLevel() > getStats().getLevel()) {
            throw new InsufficientLevelException("Insufficient player level");
        }
    }

    private void sendCurrentWeaponToBackpack() throws MaxCapacityReachedException {
        this.weapon.ifPresent(value -> stats.setAttack(stats.getAttack() - value.getAttack()));
        if (weapon.isPresent()) {
            backpack.put(weapon.get());
        }
    }

    public void gainExperience(int experience) {
        if (experience <= 0) {
            throw new IllegalArgumentException("Experience gain must be positive");
        }
        stats.setExperience(stats.getExperience() + experience);
        if (stats.getExperience() >= stats.getXpToNextLevel()) {
            levelUp();
        }
    }

    public void pickUpItem(Treasure item) throws MaxCapacityReachedException {
        handleNullObjects(item);
        backpack.put(item);
        DungeonMap.getInstance().removeItem(item);
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public Treasure getTreasureFromName(String name) throws NonexistentItemException {
        handleNullObjects(name);
        handleEmptyString(name);
        return backpack.get(name);
    }

    public String getId() {
        return id;
    }

    public Optional<Weapon> getWeapon() {
        return weapon;
    }

    private void handleEmptyString(String s) {
        if (s.isEmpty() || s.isBlank()) {
            throw new IllegalArgumentException("String must not be empty or blank");
        }
    }

    private void handleNullObjects(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
    }

}
