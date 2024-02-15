package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientLevelException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.InsufficientManaCostException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.MaxCapacityReachedException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.NonexistentItemException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.Treasure;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.treasure.TreasureType;

public class Use {

    private static final String INVALID_ARGUMENTS = "Invalid command arguments!" + System.lineSeparator();
    private static final String INSUFFICIENT_LEVEL = "Player level must be equal " +
        "or greater than item level!" + System.lineSeparator();
    private static final String INSUFFICIENT_MANA = "You cannot use this - insufficient mana!" + System.lineSeparator();
    private static final String UNKNOWN_ITEM = "Item does not exist!" + System.lineSeparator();
    private static final String POTION_USED = "Potion used!" + System.lineSeparator();
    private static final String WEAPON_EQUIPPED = "Weapon equipped!" + System.lineSeparator();
    private static final String WEAPON_NOT_EQUIPPED = "Cannot equip weapon!" + System.lineSeparator();
    private static final String UNKNOWN_TYPE = "Unknown treasure type" + System.lineSeparator();

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
        } catch (InsufficientLevelException e) {
            return CommandResponse.of(id, INSUFFICIENT_LEVEL);
        } catch (InsufficientManaCostException e) {
            return CommandResponse.of(id, INSUFFICIENT_MANA);
        } catch (MaxCapacityReachedException e) {
            return CommandResponse.of(id, WEAPON_NOT_EQUIPPED);
        } catch (NonexistentItemException e) {
            return CommandResponse.of(id, UNKNOWN_ITEM);
        }

        if (treasure.getType().equals(TreasureType.WEAPON)) {
            return CommandResponse.of(id, WEAPON_EQUIPPED);
        } else if (treasure.getType().equals(TreasureType.MANA_POTION)
            || treasure.getType().equals(TreasureType.HEALTH_POTION)) {
            return CommandResponse.of(id, POTION_USED);
        }
        return CommandResponse.of(id, UNKNOWN_TYPE);
    }

    private static boolean areArgsInvalid(String... args) {
        return args == null || args.length != 1;
    }

}
