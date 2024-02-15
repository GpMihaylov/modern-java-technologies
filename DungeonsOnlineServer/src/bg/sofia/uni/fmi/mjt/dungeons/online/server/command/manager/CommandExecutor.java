package bg.sofia.uni.fmi.mjt.dungeons.online.server.command.manager;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.Attack;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.Inventory;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.Move;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.PickUp;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.Send;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.Stats;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.Use;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;

import java.nio.channels.SocketChannel;

public class CommandExecutor {

    private static final String MOVE = "move";
    private static final String PICK_UP = "pickup";
    private static final String USE = "use";
    private static final String ATTACK = "attack";
    private static final String STATS = "stats";
    private static final String INVENTORY = "inventory";
    private static final String SEND = "send";
    private static final String INVALID_COMMAND = "No such command exists!" + System.lineSeparator();

    public CommandResponse execute(Command cmd, SocketChannel socketChannel) {
        String id = socketChannel.socket().getRemoteSocketAddress().toString();

        return switch (cmd.command().toLowerCase().strip()) {
            case MOVE -> Move.execute(id, cmd.arguments());
            case PICK_UP -> PickUp.execute(id);
            case USE -> Use.execute(id, cmd.arguments());
            case ATTACK -> Attack.execute(id);
            case STATS -> Stats.execute(id);
            case INVENTORY -> Inventory.execute(id);
            case SEND -> Send.execute(id, cmd.arguments());
            default -> CommandResponse.of(id, INVALID_COMMAND);
        };
    }

}
