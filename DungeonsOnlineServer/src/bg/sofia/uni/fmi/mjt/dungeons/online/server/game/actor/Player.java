package bg.sofia.uni.fmi.mjt.dungeons.online.server.game.actor;

import java.nio.channels.SocketChannel;

public class Player {

    String id;

    private Position position;

    public Player(String id) {
        this.id = id;
        position = new Position(0, 0);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        position.setX(newPosition.getX());
        position.setY(newPosition.getY());
    }

    public String getId() {
        return id;
    }

}
