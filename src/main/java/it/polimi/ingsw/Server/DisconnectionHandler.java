package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Server.RoomHandler.Room;

import java.util.Map;

public class DisconnectionHandler {
    private final ControllerToModel controllerToModel;
    private final Room room;

    public DisconnectionHandler(ControllerToModel controllerToModel, Room room) {
        this.controllerToModel = controllerToModel;
        this.room = room;
    }

    public synchronized void setNullConnection(String name){
        controllerToModel.getPlayerDisconnected().add(name);
        controllerToModel.getConnections().remove(name);
        room.setFullPlayer(false);
        System.out.println(name + " is disconnected");
        if(name.equals(controllerToModel.getActivePlayer().getName())){
            controllerToModel.newTurn();
        }
    }

    public synchronized boolean checkReconnection(String playerName){
        if(controllerToModel.getPlayerDisconnected().contains(playerName)){
            return true;
        }else{
            return false;
        }
    }

    public synchronized void clientReconnected(String name){
        controllerToModel.getPlayerDisconnected().remove(name);
    }

    //get runtime


}
