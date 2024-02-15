package bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.PlayerNotFoundException;
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
        if (responses.containsKey(id)) {
            attachResponse(id, response);
        } else {
            responses.put(id, response);
        }
        return this;
    }

    private void attachResponse(String id, String response) {
        String existingResponse = responses.getOrDefault(id, "");
        responses.put(id, existingResponse + response);
    }

    public Map<String, String> getResponses() {
        return responses;
    }

    public CommandResponse attachStatsHeader(String id) throws PlayerNotFoundException {
        if (DungeonMap.getInstance().getPlayerStats(id) != null) {
            String stats = DungeonMap.getInstance().getPlayerStats(id).toString();
            return addResponse(id, stats);
        } else {
            throw new PlayerNotFoundException("Player with this id does not exist");
        }
    }
}
