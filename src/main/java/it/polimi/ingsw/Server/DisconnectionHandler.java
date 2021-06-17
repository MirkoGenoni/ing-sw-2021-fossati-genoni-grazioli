package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Resource.Resource;
import it.polimi.ingsw.Server.RoomHandler.Room;

import java.util.ArrayList;
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

        //check if the player have choose initial resource
        if(controllerToModel.getPlayerWithInitialResource().contains(name)){
            ArrayList<Resource> tmpR = new ArrayList();
            for(int i=0; i<6; i++){
                tmpR.add(null);
            }
            controllerToModel.initialResourcesChoose(tmpR ,name);
        }

        // check if the player haven't discard the initial leader card
        for(int i=0; i<controllerToModel.getPlayers().length; i++){
            if(controllerToModel.getPlayers()[i].getName().equals(name)){
                try {
                    if(controllerToModel.getPlayers()[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable().size()!=0 &&
                            controllerToModel.getPlayers()[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable().size()>2){
                        controllerToModel.discardInitialLeaderCards(name, 0, 1);
                    }
                } catch (LeaderCardException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }
        }

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

    public synchronized void removeRoom(){
        room.eliminateRoom();
    }


}
