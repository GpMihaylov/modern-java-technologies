package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.PlayerNotFoundException;

public class Stats {
    public static CommandResponse execute(String id) {
        try {
            return new CommandResponse().attachStatsHeader(id);
        } catch (PlayerNotFoundException e) {
            throw new IllegalArgumentException("Player with this id does not exist");
        }
    }
}
