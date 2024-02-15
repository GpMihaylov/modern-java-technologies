package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.MaxCapacityReachedException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;

import java.util.List;

public class PickUp {

    private static final String NO_AVAILABLE_ITEMS = "No available items on this position!" + System.lineSeparator();
    private static final String MAX_CAPACITY = "Cannot pick up item - backpack is full!" + System.lineSeparator();
    private static final String ITEM_PICKED_UP = "You picked up a treasure!" + System.lineSeparator();

    public static CommandResponse execute(String id) {
        DungeonMap map = DungeonMap.getInstance();

        Player player = map.getPlayer(id);
        Position position = player.getPosition();

        Treasure item;
        try {
            item = map.getTreasureOnPosition(position);
            player.pickUpItem(item);
        } catch (NonexistentItemException e) {
            return new CommandResponse().addResponse(id, NO_AVAILABLE_ITEMS);
        } catch (MaxCapacityReachedException e) {
            return CommandResponse.of(id, MAX_CAPACITY);
        }

        return new CommandResponse().addResponse(id, ITEM_PICKED_UP);
    }
}
