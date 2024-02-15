package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.MaxCapacityReachedException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.PlayerNotFoundException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;

public class Send {
    private static final String INVALID_ARGUMENTS = "Invalid command arguments!" + System.lineSeparator();
    private static final String INVALID_TARGET = "Cannot send to player %s!" + System.lineSeparator();
    private static final String UNKNOWN_ITEM = "Item does not exist!" + System.lineSeparator();
    private static final String ITEM_SENT = "You sent a %s to player %s!" + System.lineSeparator();
    private static final String ITEM_NOT_SENT = "You failed to send %s to player %s!" + System.lineSeparator();
    private static final String ITEM_RECEIVED = "You received a %s from player %s!" + System.lineSeparator();
    private static final String ITEM_NOT_RECEIVED = "You failed to receive %s from player %s!" + System.lineSeparator();

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
        } catch (NonexistentItemException e) {
            return CommandResponse.of(id, UNKNOWN_ITEM);
        }
        int receiverNumber = Integer.parseInt(args[1].strip());
        Player receiver = null;
        try {
            receiver = map.getPlayerFromNumber(receiverNumber);
            sender.getBackpack().remove(item);
            receiver.getBackpack().put(item);
        } catch (PlayerNotFoundException e) {
            return CommandResponse.of(id, String.format(INVALID_TARGET, receiverNumber));
        } catch (MaxCapacityReachedException | NonexistentItemException e) {
            response.addResponse(id, String.format(ITEM_NOT_SENT, itemName, receiver))
                .addResponse(receiver.getId(), String.format(ITEM_NOT_RECEIVED, itemName, sender));
            return response;
        }

        return getSuccessfulResponse(id, response, itemName, receiverNumber, receiver, map);
    }

    private static CommandResponse getSuccessfulResponse(String id, CommandResponse response,
                                                         String itemName, int receiverNumber,
                                                         Player receiver, DungeonMap map) {
        response.addResponse(id, String.format(ITEM_SENT, itemName, receiverNumber));
        response.addResponse(receiver.getId(), String.format(ITEM_RECEIVED, itemName, map.getPlayerNumber(id)));

        return response;
    }

    private static boolean areArgsInvalid(String... args) {
        return args == null || args.length != 2;
    }
}
