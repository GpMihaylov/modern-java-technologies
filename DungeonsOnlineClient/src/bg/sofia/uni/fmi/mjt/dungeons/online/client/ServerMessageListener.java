package bg.sofia.uni.fmi.mjt.dungeons.online.client;

import java.io.BufferedReader;
import java.io.IOException;

public class ServerMessageListener implements Runnable {

    private final BufferedReader reader;

    public ServerMessageListener(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        String reply;
        while (true) {
            try {
                if ((reply = reader.readLine()) != null) {
                    //TODO how to handle the reply?
                    System.out.println(reply);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server");
                break;
            }
        }
    }
}
