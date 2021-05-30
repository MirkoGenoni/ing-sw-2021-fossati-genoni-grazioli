package it.polimi.ingsw.Server;

import it.polimi.ingsw.Server.ConnectionToClient;
import it.polimi.ingsw.Server.RoomHandler.Room;
import it.polimi.ingsw.Server.RoomHandler.RoomHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server implements Runnable{
    private final ServerSocket serverSocket;


    private Map<Integer, Room> rooms;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(12345);
        rooms = new HashMap<>();

    }

    @Override
    public void run() {
        while(true){
            System.out.println("server is running...");
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("playerConnesso");
                ConnectionToClient connectionToClient = new ConnectionToClient(clientSocket);
                RoomHandler roomHandler = new RoomHandler(rooms);
                roomHandler.setTmpConnection(connectionToClient);
                new Thread(connectionToClient).start();
                connectionToClient.setObserveConnectionToClient(roomHandler);

                connectionToClient.sendRoomRequestToClient("write the number of the game room");
                System.out.println("wait...");

                // fa cose e la passa ad un nuovo room handler
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
