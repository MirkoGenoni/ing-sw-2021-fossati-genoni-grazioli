package it.polimi.ingsw.server;

import it.polimi.ingsw.server.roomhandler.Room;
import it.polimi.ingsw.server.roomhandler.RoomHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class represents the server.
 * Implements Runnable interface.
 * @see Runnable
 *
 * @author Stefano Fossati
 */
public class Server implements Runnable{
    private ServerSocket serverSocket;

    private int port;

    private final Map<Integer, Room> rooms;

    /**
     * Constructor of the server class.
     *
     * @author Stefano Fossati
     */
    public Server(){
        port = 49521;  //TODO trovare porta piu intelligente
        rooms = new HashMap<>();
    }

    @Override
    public void run() {
        System.out.println("INSERT PORT (write nothing to choose the default port)");
        Scanner input = new Scanner(System.in);
        try{
            String newPort = input.nextLine();
            if(newPort.length()!=0 && !newPort.toLowerCase().equals("default")){
                port = Integer.parseInt(newPort);
            }
        }catch (IllegalStateException e){
            System.out.println("the default port is: " + port);

        }
        System.out.println("the server port is : " + port);

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO
        System.out.println("server is running...");

        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                ConnectionToClient connectionToClient = new ConnectionToClient(clientSocket);
                RoomHandler roomHandler = new RoomHandler(this);
                roomHandler.setTmpConnection(connectionToClient);
                new Thread(connectionToClient).start();
                connectionToClient.setObserveConnectionToClient(roomHandler);
                connectionToClient.sendRoomRequestToClient("write the number of the game room");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Getter of the map of the rooms.
     * @return The map of the rooms.
     */
    public synchronized Map<Integer, Room> getRooms() {
        return rooms;
    }

    /**
     * Main method to start the server.
     * @param args The argument of the main.
     */
    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start();
    }

}
