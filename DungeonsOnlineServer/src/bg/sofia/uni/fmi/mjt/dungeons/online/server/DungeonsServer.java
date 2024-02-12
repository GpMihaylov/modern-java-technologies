package bg.sofia.uni.fmi.mjt.dungeons.online.server;

import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.manager.CommandCreator;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.command.manager.CommandExecutor;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.DungeonMap;
import bg.sofia.uni.fmi.mjt.dungeons.online.server.game.map.MapVisualizer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class DungeonsServer {

    private static final int BUFFER_SIZE = 1024;
    private static final String HOST = "localhost";
    private static final int SERVER_PORT = 1337;

    private final CommandExecutor commandExecutor;

    private final int port;
    private boolean isServerWorking;

    private ByteBuffer buffer;
    private Selector selector;

    private final CopyOnWriteArrayList<SocketChannel> connectedClients;

    public DungeonsServer(int port, CommandExecutor commandExecutor) {
        this.port = port;
        this.commandExecutor = commandExecutor;
        connectedClients = new CopyOnWriteArrayList<>();
    }

    public static void main(String... args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        new DungeonsServer(SERVER_PORT, commandExecutor).start();
    }

    public void start() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            selector = Selector.open();
            configureServerSocketChannel(serverSocketChannel, selector);
            this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
            isServerWorking = true;
            while (isServerWorking) {
                try {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) {
                        continue;
                    }

                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isReadable()) {
                            SocketChannel clientChannel = (SocketChannel) key.channel();
                            String clientInput = getClientInput(clientChannel);

                            if (clientInput == null) {
                                continue;
                            }

                            String output = commandExecutor
                                .execute(CommandCreator.newCommand(clientInput), clientChannel);
                            writeClientOutput(clientChannel, output);

                            broadcastMap();
                        } else if (key.isAcceptable()) {
                            accept(selector, key);
                        }
                        keyIterator.remove();
                    }
                } catch (IOException e) {
                    System.out.println("Error occurred while processing client request: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to start server", e);
        }
    }

    public void stop() {
        this.isServerWorking = false;
        if (selector.isOpen()) {
            selector.wakeup();
        }
    }

    private void configureServerSocketChannel(ServerSocketChannel channel, Selector selector) throws IOException {
        channel.bind(new InetSocketAddress(HOST, this.port))
            .configureBlocking(false)
            .register(selector, SelectionKey.OP_ACCEPT);
    }

    private String getClientInput(SocketChannel clientChannel) throws IOException {
        buffer.clear();

        int readBytes = clientChannel.read(buffer);
        if (readBytes < 0) {
            disconnectClient(clientChannel);
            return null;
        }

        buffer.flip();

        byte[] clientInputBytes = new byte[buffer.remaining()];
        buffer.get(clientInputBytes);

        return new String(clientInputBytes, StandardCharsets.UTF_8);
    }

    private void disconnectClient(SocketChannel clientChannel) throws IOException {
        System.out.println(clientChannel.socket().getRemoteSocketAddress() + " disconnected from server!");
        clientChannel.close();
        connectedClients.remove(clientChannel);

        String id = clientChannel.socket().getRemoteSocketAddress().toString();
        DungeonMap.getInstance().removePlayer(id);
    }

    private void writeClientOutput(SocketChannel clientChannel, String output) throws IOException {
        buffer.clear();
        buffer.put(output.getBytes());
        buffer.flip();

        clientChannel.write(buffer);
    }

    private void accept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();

        System.out.println(accept.socket().getRemoteSocketAddress() + " connected to server!");

        accept.configureBlocking(false)
            .register(selector, SelectionKey.OP_READ);

        connectedClients.add(accept);

        String id = accept.socket().getRemoteSocketAddress().toString();
        DungeonMap.getInstance().addPlayer(id);
    }

    private void broadcastMap() {
        String mapAsString = MapVisualizer.getMapAsString();
        byte[] mapBytes = mapAsString.getBytes(StandardCharsets.UTF_8);

        buffer.clear();
        buffer.put(mapBytes);
        buffer.flip();

        for (SocketChannel clientChannel : connectedClients) {
            try {
                clientChannel.write(buffer);
            } catch (IOException e) {
                System.out.println("Error broadcasting map to client: " + e.getMessage());
            }
            buffer.rewind();
        }

    }

}
