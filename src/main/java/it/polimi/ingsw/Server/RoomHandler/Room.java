package it.polimi.ingsw.Server.RoomHandler;

import it.polimi.ingsw.Controller.ControllerConnection;
import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Server.ConnectionToClient;
import it.polimi.ingsw.Server.DisconnectionHandler;
import it.polimi.ingsw.Server.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This class represents the room of a game. It has all the connection with the clients, the controller and model of one match.
 *
 * @author Stefano Fossati
 */
public class Room {
    private final Server server;
    private final int roomNumber;
    private final Map<String, ConnectionToClient> connections;
    private DisconnectionHandler disconnectionHandler;
    private Thread ping;

    private boolean sendNumPlayer;
    private boolean start;
    private boolean fullPlayer;
    private boolean finish;
    private int numPlayer;

    private final ControllerToModel controllerToModel;
    private final ControllerConnection controllerConnection;

    /**
     * Constructs the room initialising default attributes and creates controllers of connection and of the game.
     * @param roomNumber The number of the room of the match.
     * @param server The server of the game.
     */
    public Room(int roomNumber, Server server) {
        this.server = server;
        this.roomNumber = roomNumber;
        this.connections = new HashMap<>();
        this.numPlayer = -1;
        this.start = false;
        this.sendNumPlayer = false;
        this.finish = false;
        controllerToModel = new ControllerToModel(connections);
        controllerConnection = new ControllerConnection(controllerToModel);
        ping = new Thread(() -> {
            while(!finish){
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connections.forEach((k,v) -> v.sendPing());
            }
        });
        ping.start();
    }


    //---------------------
    //  SETTERS
    //---------------------

    /**
     * Sets the number of players of the match in this room.
     * @param numPlayer The number of player of the game.
     */
    public synchronized void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
        controllerToModel.setNumPlayer(numPlayer);
    }

    /**
     * Sets the disconnection handler of this room.
     * @param disconnectionHandler The disconnection handler of this room.
     */
    public void setDisconnectionHandler(DisconnectionHandler disconnectionHandler) {
        this.disconnectionHandler = disconnectionHandler;
    }

    /**
     * Sets if the number of player request for this room is already send to the first client connected or not.
     * @param sendNumPlayer The boolean that indicates if the number player request is already send.
     */
    public synchronized void setSendNumPlayer(boolean sendNumPlayer) {
        this.sendNumPlayer = sendNumPlayer;
    }

    /**
     * Sets if all the client are connected in this match.
     * @param fullPlayer Is set true if all the player are connected to this room, else is set false.
     */
    public void setFullPlayer(boolean fullPlayer) {
        this.fullPlayer = fullPlayer;
    }

    //---------------------
    //  GETTER
    //---------------------

    /**
     * Getter of the number of this room.
     * @return The number of this room.
     */
    public synchronized int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Getter synchronized of all the connection with the clients currently connected to this room.
     * @return The map with all the current connected players.
     */
    public synchronized Map<String, ConnectionToClient> getConnections() {
        return connections;
    }

    /**
     * Getter of the number of player of this room.
     * @return The number of player of this room.
     */
    public synchronized int getNumPlayer() {
        return numPlayer;
    }

    /**
     * Getter of the disconnection handler of this room.
     * @return The disconnection handler of this room.
     */
    public DisconnectionHandler getDisconnectionHandler() {
        return disconnectionHandler;
    }

    /**
     * Getter of the boolean that indicates if the number player request is already send.
     * @return It is true if the number player request is already send, else is false.
     */
    public synchronized boolean isSendNumPlayer() {
        return sendNumPlayer;
    }

    /**
     * Getter of the boolean that indicates if the game has already started.
     * @return It is true if the game is already started, else false.
     */
    public boolean isStart() {
        return start;
    }

    /**
     * Getter of the boolean that indicates if all the client/player are connected with this room.
     * @return True if all the player are connected, else false.
     */
    public boolean isFullPlayer() {
        return fullPlayer;
    }

    /**
     * Adds the new connection with a client to the list of all the actualy connections with this room.
     * @param name The name of the player of the connection.
     * @param connection Teh connection with the client.
     * @param reconnection The boolean that indicates if the client has done a reconnection, or not.
     */
    public synchronized void addConnectionToClient(String name, ConnectionToClient connection, boolean reconnection){
        if(!reconnection){  // if is a reconnection in a room where there is a game already started
            if( connections.size()==0 || connections.size()<numPlayer){
                System.out.println(connection.getNamePlayer() + " add connection");
                connections.put(name, connection);
                controllerToModel.addPlayerNameOrder(connection.getNamePlayer());
            }else{
                System.out.println(connection.getNamePlayer() + " errore");
            }
        }else{
            System.out.println("reconnection of " + name);
            disconnectionHandler.clientReconnected(connection.getNamePlayer());
            connections.put(name, connection);
            connection.setObserveConnectionToClient(controllerConnection);
            connection.setDisconnectionHandler(disconnectionHandler);
            if(connections.size()==numPlayer){
                fullPlayer = true;
            }
            if((numPlayer>1 && connections.size()==2) || (numPlayer==1 && connections.size()==1)){
                controllerToModel.newTurn(false);
            }else if(numPlayer>1 && connections.size()==1){
                connection.sendNotify("other player are all disconnected, wait that they rejoin the match");
            }
        }


    }

    /**
     * Starts the match when all the players/clients are connected.
     */
    public void startGame(){
        setFullPlayer(true);
        disconnectionHandler = new DisconnectionHandler(controllerToModel, this);
        controllerToModel.getConnections().forEach((k,v) -> v.setObserveConnectionToClient(controllerConnection));
        controllerToModel.getConnections().forEach((k, v) -> v.setDisconnectionHandler(disconnectionHandler));
        setDisconnectionHandler(disconnectionHandler);
        System.out.println("Start the game in room " + roomNumber);
        start = true;
        try {
            controllerToModel.startMatch();
        } catch (StartGameException e) {
            e.printStackTrace();
        }
    }

    /**
     * Eliminates the room from the map of all the rooms in the server.
     */
    public synchronized void eliminateRoom(){
        if(!finish){
            finish = true;
            server.getRooms().remove(roomNumber);
            ping.interrupt();
        }
    }


}
