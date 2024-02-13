package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;

public class Stats {
    public static CommandResponse execute(String id) {
        return new CommandResponse().attachStatsHeader(id);
    }
}
