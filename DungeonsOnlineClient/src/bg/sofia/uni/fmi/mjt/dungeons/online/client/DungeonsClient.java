package bg.sofia.uni.fmi.mjt.dungeons.online.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DungeonsClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 1337;

    private final String serverHost;
    private final int serverPort;

    public DungeonsClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public static void main(String[] args) {
        new DungeonsClient(SERVER_HOST, SERVER_PORT).start();
    }

    public void start() {
        try (SocketChannel socketChannel = SocketChannel.open();
             var reader = new BufferedReader(Channels.newReader(socketChannel, StandardCharsets.UTF_8));
             var writer = new PrintWriter(Channels.newWriter(socketChannel, StandardCharsets.UTF_8), true);
             Scanner scanner = new Scanner(System.in)
        ) {
            socketChannel.connect(new InetSocketAddress(serverHost, serverPort));

            System.out.println("Welcome to Dungeons online!");

            Thread.startVirtualThread(new ServerMessageListener(reader));

            while (true) {
                String message = scanner.nextLine();

                writer.println(message);

                if ("disconnect".equals(message)) {
                    System.out.println("Come back soon!");
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("A problem with the network communication occurred", e);
        }
    }

}
