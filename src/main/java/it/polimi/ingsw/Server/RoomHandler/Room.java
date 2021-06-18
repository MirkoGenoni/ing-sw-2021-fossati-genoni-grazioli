package it.polimi.ingsw.Server.RoomHandler;

import it.polimi.ingsw.Controller.ControllerConnection;
import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Server.ConnectionToClient;
import it.polimi.ingsw.Server.DisconnectionHandler;
import it.polimi.ingsw.Server.Server;

import java.util.HashMap;
import java.util.Map;

public class Room {
    private final Server server;
    private final int roomNumber;
    private final Map<String, ConnectionToClient> connections;
    private DisconnectionHandler disconnectionHandler;

    private boolean sendNumPlayer;
    private boolean start;
    private boolean fullPlayer;
    private boolean finish;
    private int numPlayer;


    private final ControllerToModel controllerToModel;
    private final ControllerConnection controllerConnection;

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

    public DisconnectionHandler getDisconnectionHandler() {
        return disconnectionHandler;
    }

    public synchronized boolean isSendNumPlayer() {
        return sendNumPlayer;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isFullPlayer() {
        return fullPlayer;
    }

    public synchronized void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
        controllerToModel.setNumPlayer(numPlayer);
    }

    public void setDisconnectionHandler(DisconnectionHandler disconnectionHandler) {
        this.disconnectionHandler = disconnectionHandler;
    }

    public synchronized void setSendNumPlayer(boolean sendNumPlayer) {
        this.sendNumPlayer = sendNumPlayer;
    }

    public void setFullPlayer(boolean fullPlayer) {
        this.fullPlayer = fullPlayer;
    }



    public synchronized void addConnectionToClient(String name, ConnectionToClient connection, boolean reconnection){
        if(!reconnection){
            if( connections.size()==0 || connections.size()<numPlayer){
                System.out.println(connection.getNamePlayer() + " aggiungo la connessione");
                connections.put(name, connection);
                controllerToModel.addPlayerNameOrder(connection.getNamePlayer());
            }else{
                System.out.println(connection.getNamePlayer() + " errore");
            }
        }else{
            System.out.println("reconnection " + name);
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

    public void startGame(){
        setFullPlayer(true);
        disconnectionHandler = new DisconnectionHandler(controllerToModel, this);
        controllerToModel.getConnections().forEach((k,v) -> v.setObserveConnectionToClient(controllerConnection));
        controllerToModel.getConnections().forEach((k, v) -> v.setDisconnectionHandler(disconnectionHandler));
        setDisconnectionHandler(disconnectionHandler);
        System.out.println("inizia il gioco");
        start = true;
        try {
            controllerToModel.startMatch();
        } catch (StartGameException e) {
            e.printStackTrace();
        }
    }

    public synchronized void eliminateRoom(){
        if(!finish){
            finish = true;
            server.getRooms().remove(roomNumber);
        }
        System.out.println(server.getRooms() + " mappa stanze");
    }


}
