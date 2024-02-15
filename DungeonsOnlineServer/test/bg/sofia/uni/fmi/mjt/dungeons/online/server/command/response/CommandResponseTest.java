package bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
