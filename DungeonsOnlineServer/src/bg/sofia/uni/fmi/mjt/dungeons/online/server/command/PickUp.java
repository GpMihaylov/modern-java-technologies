package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;

import java.util.List;

public class PickUp {

    private static final String ITEM_PICKED_UP = "You picked up a treasure!" + System.lineSeparator();
    private static final String NO_AVAILABLE_ITEMS = "No available items on this position!" + System.lineSeparator();

    public static CommandResponse execute(String id) {
        DungeonMap map = DungeonMap.getInstance();

        Player player = map.getPlayer(id);
        Position position = player.getPosition();

        Treasure item = map.getTreasureOnPosition(position);
        if (item == null) {
            return new CommandResponse().addResponse(id, NO_AVAILABLE_ITEMS);
        }
        player.pickUpItem(item);

        return new CommandResponse().addResponse(id, ITEM_PICKED_UP);
    }
}
