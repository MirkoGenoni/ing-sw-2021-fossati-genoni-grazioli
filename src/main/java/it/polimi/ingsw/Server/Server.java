package it.polimi.ingsw.Server;

import it.polimi.ingsw.Server.RoomHandler.Room;
import it.polimi.ingsw.Server.RoomHandler.RoomHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server implements Runnable{
    private ServerSocket serverSocket;

    private int port;

    private final Map<Integer, Room> rooms;

    public Server(){
        port = 12345;  //TODO trovare porta piu intelligente
        rooms = new HashMap<>();
    }

    @Override
    public void run() {
        System.out.println("INSERT PORT (write nothing to choose the default port)");
        Scanner input = new Scanner(System.in);
        try{
            String newPort = input.nextLine();
            if(!newPort.toLowerCase().equals("default")){
                port = Integer.parseInt(newPort);
            }
        }catch (IllegalStateException e){
            System.out.println("the default port is: " + port);

        }


        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server is running...");

        while(true){

            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("playerConnesso");
                ConnectionToClient connectionToClient = new ConnectionToClient(clientSocket);
                RoomHandler roomHandler = new RoomHandler(this);
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

    public synchronized Map<Integer, Room> getRooms() {
        return rooms;
    }
}
