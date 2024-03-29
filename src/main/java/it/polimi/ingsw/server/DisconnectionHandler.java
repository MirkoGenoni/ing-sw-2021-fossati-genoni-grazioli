package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.exceptions.LeaderCardException;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.server.roomhandler.Room;

import java.util.ArrayList;

/**
 * This class handle the disconnection of the client from the room.
 *
 * @author Stefano Fossati.
 */
public class DisconnectionHandler {
    private final Controller controller;
    private final Room room;

    /**
     * Constructs the class with the room and it controller.
     * @param controller The controller of the room.
     * @param room The room of the match.
     */
    public DisconnectionHandler(Controller controller, Room room) {
        this.controller = controller;
        this.room = room;
    }

    /**
     * Updates the map of the connection of clients.
     * Also, in case that the client have to do an action to continue the game, this method manage some logic to allow players to continue the game.
     * @param name The name of the client/player that has disconnected.
     */
    public synchronized void setNullConnection(String name){
        controller.getConnections().remove(name);
        if(room.isStart()){
            controller.getPlayerDisconnected().add(name);

            room.setFullPlayer(false);

            //check if the player have choose initial resource: true --> doesn't choose resources
            if(controller.getPlayerWithInitialResource().contains(name)){
                ArrayList<Resource> tmpR = new ArrayList();
                for(int i=0; i<6; i++){
                    tmpR.add(null);
                }
                controller.initialResourcesChoose(tmpR ,name);
            }

            // check if the player haven't discard the initial leader card: true --> discard random card
            for(int i = 0; i< controller.getPlayers().length; i++){
                if(controller.getPlayers()[i].getName().equals(name)){
                    try {
                        if(controller.getPlayers()[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable().size()!=0 &&
                                controller.getPlayers()[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable().size()>2){
                            controller.discardInitialLeaderCards(name, 0, 1);
                        }
                    } catch (LeaderCardException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
            }
            if(name.equals(controller.getActivePlayer().getName())){
                controller.newTurn(true);
            }
        }

        if(room.isSendNumPlayer() && room.getNumPlayer()==-1 && room.getPlayerSendNumPlayer().equals(name)){
            room.setPlayerSendNumPlayer(null);
            room.setSendNumPlayer(false);
        }

        if(!room.isStart()){
            controller.getOrderPlayerConnections().remove(name);
            if(controller.getConnections().size()==0){
                removeRoom();
            }
        }

        System.out.println(name + " is disconnected");
    }

    /**
     * Checks that the name of the player who try to reconnect is the player in the list of disconnected players.
     * @param playerName The name of the player to compare.
     * @return True if the name of the player is the list of disconnected players, else return false.
     */
    public synchronized boolean checkReconnection(String playerName){
        if(controller.getPlayerDisconnected().contains(playerName)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Updates the list of the player name disconnected.
     * @param name The name of the client/player that rejoin the match in the room.
     */

    public synchronized void clientReconnected(String name){
        controller.getPlayerDisconnected().remove(name);
    }

    /**
     * Removes the room from the all room map of the server.
     * It is synchronized to avoid accesses at the same time.
     */
    public synchronized void removeRoom(){
        room.eliminateRoom();
    }


}
