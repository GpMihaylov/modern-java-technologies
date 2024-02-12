package bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response;

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

    public void addResponse(String playerId, String response) {
        responses.put(playerId, response);
    }

    public Map<String, String> getResponses() {
        return responses;
    }
}
