package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.response.CommandResponse;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.util.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;

public class Move {

    private static final String MISSING_ARGUMENTS = "Missing command argument" + System.lineSeparator();
    private static final String INVALID_COMMAND = "Invalid move command" + System.lineSeparator();
    private static final String SUCCESS = "You successfully moved %s" + System.lineSeparator();
    private static final String FAILURE = "Cannot move in that direction" + System.lineSeparator();

    public static CommandResponse execute(String id, String... args) {
        if (areArgsValid(args)) {
            return CommandResponse.of(id, MISSING_ARGUMENTS);
        }

        Player player = DungeonMap.getInstance().getPlayer(id);
        String direction = args[0].strip();

        Position oldPosition = new Position(player.getPosition().x(),
            player.getPosition().y());

        Position newPosition = calculateNewPosition(player, direction);
        if (newPosition == null) {
            return CommandResponse.of(id, INVALID_COMMAND);
        }

        if (isValidPosition(newPosition)) {
            player.setPosition(newPosition);
            DungeonMap.getInstance().updatePlayerPosition(id, oldPosition, newPosition);

            return CommandResponse.of(id, String.format(SUCCESS, direction));
        } else {
            return CommandResponse.of(id, FAILURE);
        }
    }

    private static boolean areArgsValid(String... args) {
        return args.length < 1;
    }

    private static Position calculateNewPosition(Player player, String direction) {
        int currX = player.getPosition().x();
        int currY = player.getPosition().y();

        int newX = currX;
        int newY = currY;

        switch (direction.toLowerCase()) {
            case "up" -> newX--;
            case "down" -> newX++;
            case "left" -> newY--;
            case "right" -> newY++;
            default -> {
                return null;
            }
        }
        return new Position(newX, newY);
    }

    private static boolean isValidPosition(Position position) {
        return position.x() >= 0 && position.x() < DungeonMap.WIDTH &&
            position.y() >= 0 && position.y() < DungeonMap.HEIGHT &&
            !DungeonMap.getInstance().isObstacle(position);
    }

}

