package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.inventory;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.MaxCapacityReachedException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
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

    public void put(Treasure item) throws MaxCapacityReachedException {
        if (items.size() >= MAX_ITEMS) {
            throw new MaxCapacityReachedException("Backpack max capacity reached");
        }
        items.add(item);
    }

    public boolean contains(Treasure item) {
        return items.contains(item);
    }

    public void remove(Treasure item) throws NonexistentItemException {
        if (isEmpty()) {
            throw new NonexistentItemException("Backpack is empty; item does not exist");
        }

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

    public Treasure get(String name) throws NonexistentItemException {
        for (Treasure item :
            items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        throw new NonexistentItemException("No such item exists");
    }

    public Treasure getRandomItem() {
        List<Treasure> backpackList = new ArrayList<>(items);

        Random random = new Random();
        int indexToRemove = random.nextInt(backpackList.size());

        return backpackList.get(indexToRemove);
    }
}
