package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.inventory;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Backpack {

    private static final int MAX_ITEMS = 10;

    private final Set<Treasure> items;

    public Backpack() {
        items = new HashSet<>();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void put(Treasure item) {
        if (items.size() >= MAX_ITEMS) {
            //todo
        }
        items.add(item);
    }

    public boolean contains(Treasure item) {
        return items.contains(item);
    }

    public void remove(Treasure item) {
        items.remove(item);
    }

    public String listAllItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventory:\n");
        for (Treasure item : items) {
            sb.append(item.toString()).append("\n");
        }

        return sb.toString();
    }

}
