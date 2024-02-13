package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.TreasureType;

public class Use {

    private static final String INVALID_ARGUMENTS = "Invalid command arguments!\n";
    private static final String POTION_USED = "Potion used!\n";
    private static final String WEAPON_EQUIPED = "Weapon equipped!\n";
    private static final String UNKNOWN_TYPE = "Unknown treasure type\n";
    private static final String UNKNOWN_ITEM = "Item does not exist!\n";
    public static CommandResponse execute(String id, String... args) {
        if (areArgsInvalid(args)) {
            return CommandResponse.of(id, INVALID_ARGUMENTS);
        }

        Player player = DungeonMap.getInstance().getPlayer(id);
        String itemName = args[0].strip();
        Treasure treasure;

        try {
            treasure = player.getTreasureFromName(itemName);
            treasure.use(player);
        } catch (Exception e) { //todo fix exception type and two catches for two statements
            return CommandResponse.of(id, UNKNOWN_ITEM);
        }

        if (treasure.getType().equals(TreasureType.WEAPON)) {
            return CommandResponse.of(id, WEAPON_EQUIPED);
        } else if (treasure.getType().equals(TreasureType.MANA_POTION)
            || treasure.getType().equals(TreasureType.HEALTH_POTION)) {
            return CommandResponse.of(id, POTION_USED);
        }

        return CommandResponse.of(id, UNKNOWN_TYPE);
    }

    private static boolean areArgsInvalid(String[] args) {
        return args == null || args.length != 1;
    }

}
