package bg.sofia.uni.fmi.mjt.dungeons.online.server.command;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Player;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor.Position;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;

public class Move {

    public static String execute(String id, String... args) {
        if (areArgsValid(args)) {
            return "Missing command argument\n";
        }

        Player player = DungeonMap.getInstance().getPlayer(id);
        String direction = args[0].strip();

        Position oldPosition = new Position(player.getPosition().getX(),
            player.getPosition().getY());

        Position newPosition = calculateNewPosition(player, direction);
        if (newPosition == null) {
            return "Invalid command\n";
        }

        if (isValidPosition(newPosition)) {
            player.setPosition(newPosition);
            DungeonMap.getInstance().updatePlayerPosition(id, oldPosition, newPosition);

            return "Moved player " + id + " " + direction + "\n";
        } else {
            return "Cannot move in that direction\n";
        }
    }

    private static boolean areArgsValid(String... args) {
        return args.length < 1;
    }

    private static Position calculateNewPosition(Player player, String direction) {
        int currX = player.getPosition().getX();
        int currY = player.getPosition().getY();

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
        return position.getX() >= 0 && position.getX() < DungeonMap.WIDTH &&
            position.getY() >= 0 && position.getY() < DungeonMap.HEIGHT &&
            !DungeonMap.getInstance().isObstacle(position.getX(), position.getY());
    }

}

