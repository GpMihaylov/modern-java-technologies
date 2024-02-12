package bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;

import java.util.HashMap;
import java.util.Map;

public class CommandResponse {
    private Map<String, String> responses;

    public CommandResponse() {
        this.responses = new HashMap<>();
    }

    public static CommandResponse of(String id, String message) {
        CommandResponse commandResponse = new CommandResponse();
        commandResponse.addResponse(id, message);
        return commandResponse;
    }

    public CommandResponse addResponse(String id, String response) {
        responses.put(id, response);
        return this;
    }

    public Map<String, String> getResponses() {
        return responses;
    }

    public CommandResponse attachHeader(String id) {
        String stats = DungeonMap.getInstance().getPlayerStats(id).toString();
        String existingResponse = responses.getOrDefault(id, "");
        String newResponse = existingResponse + stats;
        return addResponse(id, newResponse);
    }
}
