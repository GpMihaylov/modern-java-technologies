package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.inventory;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
        //todo validation + exception
        items.remove(item);
    }

    public String listAllItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventory:").append(System.lineSeparator());
        for (Treasure item : items) {
            sb.append(item.toString()).append(System.lineSeparator());
        }

        return sb.toString();
    }

    public Treasure get(String name) {
        for (Treasure item :
            items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        //todo exception
        return null;
    }

    public Treasure getRandomItem() {
        if (!isEmpty()) {
            List<Treasure> backpackList = new ArrayList<>(items);

            Random random = new Random();
            int indexToRemove = random.nextInt(backpackList.size());

            return backpackList.get(indexToRemove);
        } else {
            //todo exception
            throw new UnsupportedOperationException("not implemented");
        }
    }
}
