package bg.sofia.uni.fmi.mjt.dungeons.online.server.command.manager;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import static bg.sofia.uni.fmi.mjt.dungeons.online.server.command.manager.CommandExecutor.INVALID_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandExecutorTest {

    @Mock
    private Socket socketMock;
    @Mock
    private SocketChannel socketChannelMock;
    @Mock
    private SocketAddress socketAddressMock;

    @Test
    public void testCommandExecutorWithNonExistingCommand() {
        CommandExecutor executor = new CommandExecutor();

        String[] args = {"sample", "command", "args"};
        Command cmd = new Command("noCommand", args);

        when(socketChannelMock.socket()).thenReturn(socketMock);
        when(socketMock.getRemoteSocketAddress()).thenReturn(socketAddressMock);
        when(socketAddressMock.toString()).thenReturn("id");

        CommandResponse response = executor.execute(cmd, socketChannelMock);

        assertEquals(INVALID_COMMAND, response.getResponses().get("id"));
    }


}
