package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Minion;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;

import java.util.List;

public class Attack {
    private static final String MISS = "You did not hit anything!" + System.lineSeparator();
    private static final String DEFENDER_HIT = "You were attacked by player %s! You lose %s health!" + System.lineSeparator();
    private static final String DAMAGE_DEALT = "You dealt %s damage to %s!" + System.lineSeparator();

    public static CommandResponse execute(String id) {
        CommandResponse response = new CommandResponse();
        DungeonMap map = DungeonMap.getInstance();

        Player attacker = map.getPlayer(id);
        Position position = attacker.getPosition();
        int attackStat = attacker.getStats().getAttack();

        int targetsHit = 0;
        targetsHit += hitPlayers(position, attacker, attackStat, response);

        targetsHit += hitMinion(position, attacker, attackStat, response);

        if (targetsHit == 0) {
            return CommandResponse.of(id, MISS);
        }

        response.attachStatsHeader(id);
        return response;
    }

    private static int hitPlayers(Position position, Player attacker, int attackStat,
                                     CommandResponse response) {
        DungeonMap map = DungeonMap.getInstance();

        int attackerNumber = map.getPlayerNumber(attacker.getId());

        List<Player> playersOnPosition = map.getPlayersOnPosition(position);
        int hits = 0;

        for (Player defender : playersOnPosition) {
            if (defender.equals(attacker)) {
                continue;
            }
            int defenderNumber = map.getPlayerNumber(defender.getId());

            defender.loseHealth(attackStat);
            int lostHealth = attackStat - defender.getStats().getDefense();

            response.addResponse(defender.getId(),
                    String.format(DEFENDER_HIT, attackerNumber, lostHealth))
                .attachStatsHeader(defender.getId());

            response.addResponse(attacker.getId(),
                String.format(DAMAGE_DEALT, lostHealth, "player " + defenderNumber));

            hits++;
        }
        return hits;
    }

    private static int hitMinion(Position position, Player attacker, int attackStat,
                                 CommandResponse response) {
        DungeonMap map = DungeonMap.getInstance();

        int hit = 0;
        if (map.isMinionOnPosition(position)) {
            Minion minion = map.getMinionOnPosition(position);
            int lostHealth = attackStat - minion.getStats().getDefense();

            if (minion.getStats().getHealth() - lostHealth <= 0) {
                map.givePlayerExperienceUponMinionDeath(attacker.getId(), minion);
            }
            minion.loseHealth(attackStat);

            response.addResponse(attacker.getId(),
                String.format(DAMAGE_DEALT, lostHealth, "a minion"));

            hit++;
        }
        return hit;
    }
}
