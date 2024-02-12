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

        CommandResponse response = new CommandResponse();

        int targetsHit = 0;
        targetsHit += hitPlayers(map, position, attacker, attackStat, response, attackerNumber);

        targetsHit += hitMinion(map, position, attackStat);

        if (targetsHit == 0) {
            return CommandResponse.of(id, "You did not hit anything!\n");
        }

        response.addResponse(id, "Attack successful, targets hit: " + targetsHit + "\n");
        return response;
    }

    private static int hitPlayers(DungeonMap map, Position position, Player attacker, int attackStat,
                                     CommandResponse response, int attackerNumber) {
        List<Player> playersOnPosition = map.getPlayersOnPosition(position);
        int hits = 0;

        for (Player defender : playersOnPosition) {
            if (defender.equals(attacker)) {
                continue;
            }
            defender.loseHealth(attackStat);
            response.addResponse(defender.getId(), "You were attacked by " + attackerNumber
                + "! You lose " + attackStat + " health!\n");
            hits++;
        }
        return hits;
    }

    private static int hitMinion(DungeonMap map, Position position, int attackStat) {
        int hit = 0;
        if (map.isMinionOnPosition(position)) {
            Minion minion = map.getMinionOnPosition(position);
            minion.loseHealth(attackStat);
            hit++;
        }
        return hit;
    }
}
