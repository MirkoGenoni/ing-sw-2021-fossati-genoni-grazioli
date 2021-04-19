package it.polimi.ingsw.Client;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final String serverAddress;
    private final int serverPort;
    private Socket socket;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void startClient() throws IOException {
        socket = new Socket(serverAddress, serverPort);
        ConnectionToServer connectionToServer = new ConnectionToServer(socket);
        new Thread(connectionToServer).start();
    }
}
