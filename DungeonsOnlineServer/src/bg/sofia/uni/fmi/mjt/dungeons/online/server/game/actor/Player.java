package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.inventory.Backpack;

public class Player extends Actor {

    private static final int BASE_HEALTH = 100;
    private static final int BASE_MANA = 100;
    private static final int BASE_ATTACK = 10;
    private static final int BASE_DEFENSE = 10;

    private final String id;

    public Player(String id) {
        this.id = id;
        position = new Position(0, 0);
        initStats();
    }

    @Override
    protected void initStats() {
        stats = new Stats(BASE_HEALTH, BASE_MANA, BASE_ATTACK, BASE_DEFENSE);
    }

    public String getId() {
        return id;
    }
}
