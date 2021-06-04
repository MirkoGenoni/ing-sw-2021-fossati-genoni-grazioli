package it.polimi.ingsw.Server.RoomHandler;

import it.polimi.ingsw.Controller.ControllerConnection;
import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Server.ConnectionToClient;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private final int roomNumber;
    private final Map<String, ConnectionToClient> connections;

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

    public synchronized Map<String, ConnectionToClient> getConnections() {
        return connections;
    }

    public synchronized int getNumPlayer() {
        return numPlayer;
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
        if( connections.size()==0 || connections.size()<numPlayer){
            System.out.println(connection.getNamePlayer() + " aggiungo la connessione");
            connections.put(name, connection);
            controllerToModel.addConnectionToClient(connection);
        }else{
            System.out.println(connection.getNamePlayer() + " errore");
        }



    }

    public void startGame(){
        controllerToModel.getConnections().forEach((k,v) -> v.setObserveConnectionToClient(controllerConnection));
        System.out.println("inizia il gioco");
        start = true;
        try {
            controllerToModel.startMatch();
        } catch (StartGameException e) {
            e.printStackTrace();
        }
    }


}
