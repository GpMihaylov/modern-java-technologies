package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

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

    private static final int BASE_HEALTH = 100;
    private static final int BASE_MANA = 100;
    private static final int BASE_ATTACK = 10;
    private static final int BASE_DEFENSE = 10;
    private static final int BASE_LEVEL = 1;
    private static final int BASE_EXPERIENCE = 0;
    private static final int BASE_EXPERIENCE_NEEDED = 100;
    private static final int EXPERIENCE_MODIFIER = 2;
    private static final int EXPERIENCE_INCREASE_PER_LEVEL = BASE_EXPERIENCE_NEEDED * EXPERIENCE_MODIFIER;
    private static final int HEALTH_MODIFIER = 10;
    private static final int MANA_MODIFIER = 10;
    private static final int ATTACK_MODIFIER = 5;
    private static final int DEFENCE_MODIFIER = 5;
    private final String id;

    private final Backpack backpack;
    private Optional<Weapon> weapon;

    public Player(String id) {
        this.id = id;
        position = new Position(0, 0);
        initBaseStats();
        backpack = new Backpack();
        weapon = Optional.empty();
    }

    @Override
    public void initBaseStats() {
        stats = new Stats(BASE_HEALTH, BASE_MANA, BASE_ATTACK, BASE_DEFENSE,
            BASE_LEVEL, BASE_EXPERIENCE, BASE_EXPERIENCE_NEEDED);
    }

    @Override
    public void loseHealth(int amount) {
        if (amount <= 0) {
            //todo exception
        }
        stats.setHealth(stats.getHealth() + stats.getDefense() - amount);

        if (stats.getHealth() <= 0) {
            initBaseStats();
            //drop random item
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
            //todo exception
        }
    }

    public void useHealthPotion(HealthPotion potion) {
        if (backpack.contains(potion)) {
            heal(potion.getHealthPoints());
            backpack.remove(potion);
        }
    }

    public void useManaPotion(ManaPotion potion) {
        if (backpack.contains(potion)) {
            gainMana(potion.getManaPoints());
            backpack.remove(potion);
        }
    }

    private void heal(int amount) {
        int maxHealth = BASE_HEALTH + stats.getLevel() * HEALTH_MODIFIER;
        stats.setHealth(Math.min(stats.getHealth() + amount, maxHealth));
    }

    private void gainMana(int amount) {
        int maxMana = BASE_MANA + stats.getLevel() * MANA_MODIFIER;
        stats.setMana(Math.min(stats.getMana() + amount, maxMana));
    }

    @Override
    public void updateStats() {
        stats.setHealth(getStats().getHealth() + HEALTH_MODIFIER);
        stats.setMana(getStats().getMana() + MANA_MODIFIER);
        stats.setAttack(getStats().getAttack() + ATTACK_MODIFIER);
        stats.setDefense(getStats().getDefense() + DEFENCE_MODIFIER);
    }

    public void gainExperience(int experience) {
        if (experience <= 0) {
            //todo exception
        }
        stats.setExperience(stats.getExperience() + experience);
        if (stats.getExperience() >= stats.getXpToNextLevel()) {
            levelUp();
        }
    }

    public void pickUpItem(Treasure item) {
        if (item.getLevel() > getStats().getLevel()) {
            //todo exception
        }
        backpack.put(item);
        DungeonMap.getInstance().removeItem(item);
    }

    public void equipWeapon(Weapon weapon) {
        sendCurrentWeaponToBackpack();

        backpack.remove(weapon);
        this.weapon = Optional.of(weapon);
        stats.setAttack(stats.getAttack() + weapon.getAttack());
    }

    private void sendCurrentWeaponToBackpack() {
        this.weapon.ifPresent(value -> stats.setAttack(stats.getAttack() - value.getAttack()));
        this.weapon.ifPresent(backpack::put);
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public Treasure getTreasureFromName(String name) {
        return backpack.get(name);
    }

    public String getId() {
        return id;
    }
}
