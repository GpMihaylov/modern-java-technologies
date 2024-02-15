package bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.exception.PlayerNotFoundException;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommandResponseTest {

    @Test
    public void testAddResponse() {
        CommandResponse response = new CommandResponse();
        response.addResponse("id1", "message1");
        response.addResponse("id2", "message2");

        assertEquals("message1", response.getResponses().get("id1"));
        assertEquals("message2", response.getResponses().get("id2"));
    }

    @Test
    public void testCommandResponseAddResponseWithSameId() {
        CommandResponse response = new CommandResponse();
        response.addResponse("id", "message1");
        response.addResponse("id", "message2");

        assertEquals("message1message2", response.getResponses().get("id"));
    }

}
