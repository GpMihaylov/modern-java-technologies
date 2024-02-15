package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;

public class Send {
    private static final String INVALID_ARGUMENTS = "Invalid command arguments!" + System.lineSeparator();
    private static final String INVALID_TARGET = "Cannot send to player %s!" + System.lineSeparator();
    private static final String UNKNOWN_ITEM = "Item does not exist!" + System.lineSeparator();
    private static final String ITEM_SENT = "You sent a %s to player %s!" + System.lineSeparator();
    private static final String ITEM_RECEIVED = "You received a %s from player %s!" + System.lineSeparator();

    public static CommandResponse execute(String id, String... args) {
        if (areArgsInvalid(args)) {
            return CommandResponse.of(id, INVALID_ARGUMENTS);
        }
        CommandResponse response = new CommandResponse();
        DungeonMap map = DungeonMap.getInstance();

        Player sender = map.getPlayer(id);

        String itemName = args[0].strip();
        Treasure item;

        try {
            item = sender.getTreasureFromName(itemName);
        } catch (Exception e) {
            return CommandResponse.of(id, UNKNOWN_ITEM);
        }

        int receiverNumber = Integer.parseInt(args[1].strip());
        Player receiver = map.getPlayerFromNumber(receiverNumber);

        //todo should be exception
        if (receiver == null) {
            return CommandResponse.of(id, String.format(INVALID_TARGET, receiverNumber));
        }

        sender.getBackpack().remove(item); //todo should throw exception - handle
        receiver.getBackpack().put(item);

        response.addResponse(id, String.format(ITEM_SENT, itemName, receiverNumber));
        response.addResponse(receiver.getId(), String.format(ITEM_RECEIVED, itemName, map.getPlayerNumber(id)));

        return response;
    }

    private static boolean areArgsInvalid(String... args) {
        return args == null || args.length != 2;
    }
}
