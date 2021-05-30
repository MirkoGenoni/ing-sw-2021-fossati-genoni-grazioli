package it.polimi.ingsw.Server.RoomHandler;

import it.polimi.ingsw.Controller.ControllerConnection;
import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Server.ConnectionToClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private final int roomNumber;
    private final Map<String, ConnectionToClient> connections;
    private ArrayList<ConnectionToClient> connectionsToClient = new ArrayList<>();
    private boolean sendNumPlayer;
    private boolean start;
    private int numPlayer;


    private final ControllerToModel controllerToModel;
    private final ControllerConnection controllerConnection;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.connections = new HashMap<>();
        this.numPlayer = -1;
        this.start = false;
        this.sendNumPlayer = false;
        controllerToModel = new ControllerToModel();
        controllerConnection = new ControllerConnection(controllerToModel);
    }

    public synchronized int getRoomNumber() {
        return roomNumber;
    }

    public Map<String, ConnectionToClient> getConnections() {
        return connections;
    }

    public synchronized int getNumPlayer() {
        return numPlayer;
    }

    public ControllerConnection getControllerConnection() {
        return controllerConnection;
    }

    public synchronized ArrayList<ConnectionToClient> getConnectionsToClient() {
        return connectionsToClient;
    }

    public synchronized boolean isSendNumPlayer() {
        return sendNumPlayer;
    }

    public synchronized void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
        controllerToModel.setNumPlayer(numPlayer);
    }

    public synchronized void setSendNumPlayer(boolean sendNumPlayer) {
        this.sendNumPlayer = sendNumPlayer;
    }

    public boolean isStart() {
        return start;
    }

    public synchronized void addConnectionToClient(String name, ConnectionToClient connection){
        if( connectionsToClient.size()==0 || connectionsToClient.size()<numPlayer){
            System.out.println(connection.getNamePlayer() + " aggiungo la connessione");
            //connections.put(name, connection);
            connectionsToClient.add(connection);
            controllerToModel.addConnectionToClient(connection);
        }else{
            System.out.println(connection.getNamePlayer() + " errore");
        }



    }

    public void startGame(){
        controllerToModel.getConnectionsToClient().forEach(c -> c.setObserveConnectionToClient(controllerConnection));
        System.out.println("inizia il gioco");
        start = true;
        try {
            controllerToModel.startMatch();
        } catch (StartGameException e) {
            e.printStackTrace();
        }
    }


}
