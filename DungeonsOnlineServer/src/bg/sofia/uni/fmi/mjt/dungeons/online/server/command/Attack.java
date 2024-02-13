package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Minion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;

import java.util.List;

public class Attack {

    private static final String MISS = "You did not hit anything!\n";
    private static final String SUCCESSFUL_HIT = "Attack successful, targets hit: %s\n";
    private static final String DEFENDER_HIT = "You were attacked by %s! You lose %s health!\n";

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
            return CommandResponse.of(id, MISS);
        }

        response.addResponse(id, String.format(SUCCESSFUL_HIT, targetsHit))
            .attachHeader(id);
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
            int lostHealth = attackStat - defender.getStats().getDefense();
            response.addResponse(defender.getId(),
                    String.format(DEFENDER_HIT, attackerNumber, lostHealth))
                .attachHeader(defender.getId());
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
