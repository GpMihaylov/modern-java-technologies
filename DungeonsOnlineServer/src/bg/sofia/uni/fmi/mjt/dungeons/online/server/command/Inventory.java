package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;

public class Inventory {
    public static CommandResponse execute(String id) {
        Player player = DungeonMap.getInstance().getPlayer(id);

        return CommandResponse.of(id, player.getBackpack().listAllItems());
    }
}
