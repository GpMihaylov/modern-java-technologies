package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Minion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;

import java.util.List;

public class Attack {
    public static CommandResponse execute(String id) {
        DungeonMap map = DungeonMap.getInstance();

        Player attacker = map.getPlayer(id);
        Position position = attacker.getPosition();
        int attackerNumber = map.getPlayerNumber(id);
        int attackStat = attacker.getStats().getAttack();

        List<Player> playersOnPosition = map.getPlayersOnPosition(position);

        CommandResponse response = new CommandResponse();

        int targetsHit = 0;
        for (Player defender : playersOnPosition) {
            if (defender.equals(attacker)) {
                continue;
            }
            defender.loseHealth(attackStat);
            response.addResponse(defender.getId(), "You were attacked by " + attackerNumber
                + "! You lose " + attackStat + " health!\n");
            targetsHit++;
        }

        if (map.isMinionOnPosition(position)) {
            Minion minion = map.getMinionOnPosition(position);
            minion.loseHealth(attackStat);
            targetsHit++;
        }

        if (targetsHit == 0) {
            return CommandResponse.of(id, "You did not hit anything!\n");
        }

        response.addResponse(id, "Attack successful, targets hit: " + targetsHit + "\n");
        return response;
    }
}
