package it.polimi.ingsw.server.roomhandler;

import it.polimi.ingsw.controller.ObserveConnectionToClient;
import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.initialconnectiontoserver.EventToServerInitial;
import it.polimi.ingsw.events.clienttoserver.initialconnectiontoserver.SendNumPlayerToServer;
import it.polimi.ingsw.events.clienttoserver.initialconnectiontoserver.SendPlayerNameToServer;
import it.polimi.ingsw.events.clienttoserver.initialconnectiontoserver.SendRoomToServer;
import it.polimi.ingsw.server.ConnectionToClient;
import it.polimi.ingsw.server.Server;

/**
 * This class handle the initial connection of the client with the room, so with the game.
 * It is an implementation of the EventToServerInitialVisitor interface and of the ObserveConnectionToClient interface.
 * @see EventToServerInitialVisitor
 * @see ObserveConnectionToClient
 *
 * @author Stefano Fossati
 */
public class RoomHandler implements EventToServerInitialVisitor, ObserveConnectionToClient{
    private final Server server;

    private Room tmpRoom;
    private ConnectionToClient tmpConnection;

    /**
     * Constructs the RoomHandler with the server parameter.
     * @param server The server.
     */
    public RoomHandler(Server server){
        this.server = server;
    }

    /**
     * Sets the ConnectionToClient that this handler have to manage.
     * @param tmpConnection The connection with the client that this class have to manage.
     */
    public void setTmpConnection(ConnectionToClient tmpConnection) {
        this.tmpConnection = tmpConnection;

    }
    @Override
    public void visit(SendRoomToServer room) {
        if(room.isNewRoom() && !existRoom(room.getRoomNumber())){  //if the room doesn't exist, it was created;
            Room newRoom = new Room(room.getRoomNumber(), server);
            server.getRooms().put((Integer) newRoom.getRoomNumber(), newRoom);
            this.tmpRoom = newRoom;
            tmpConnection.sendNamePlayerRequest("write your nickname");
        }else if(room.isNewRoom() && existRoom(room.getRoomNumber())){ // if the room already exists --> reselect room
            tmpConnection.sendRoomRequestToClient("the room already exist");
        }else if(!room.isNewRoom() && !existRoom(room.getRoomNumber())){ // if the room already exist, so it isn't new --> reselect room
            tmpConnection.sendRoomRequestToClient("this room doesn't exist");
        }else if(!room.isNewRoom() && existRoom(room.getRoomNumber())){ // if the room already exist and it isn't new --> continue
            this.tmpRoom = server.getRooms().get((Integer) room.getRoomNumber());
            if(!tmpRoom.isStart()){  // if the match has already started
                tmpConnection.sendNamePlayerRequest("write your nickname");
            }else if(tmpRoom.isStart() && tmpRoom.isFullPlayer()){ // if the room is full of player
                tmpConnection.sendRoomRequestToClient("the match in this room has already start: select another room");
            }else if(tmpRoom.isStart() && !tmpRoom.isFullPlayer()){ // if the game is started but the room is not full of player --> someone is disconnected
                tmpConnection.sendNamePlayerRequest("write your old nickname to reconnect at this match");
            }

        }

    }

    @Override
    public void visit(SendPlayerNameToServer playerName) {
        //System.out.println("the name is " + playerName.getPlayerName());
        if(checkDoublePlayerName(playerName.getPlayerName())){ // if the room doesn't have another player with the same name of the player that try to reconnected.
            tmpConnection.setNamePlayer(playerName.getPlayerName());

            if(tmpRoom.getNumPlayer()<0 && tmpRoom.isSendNumPlayer()){ // if another client is deciding the number of the player of the match.
                while(tmpRoom.getNumPlayer()<0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(tmpRoom.getNumPlayer()>1 && tmpRoom.getConnections().size()<tmpRoom.getNumPlayer()){ // if the match isn't started and is not full of player.
                    tmpRoom.addConnectionToClient(tmpConnection.getNamePlayer(), tmpConnection, false);
                    if(tmpRoom.getConnections().size()==tmpRoom.getNumPlayer()){
                        tmpRoom.startGame();
                    }
                }else{
                    tmpConnection.sendRoomRequestToClient("this game is full of player, choose another room");
                }
            }else if(!tmpRoom.isSendNumPlayer()){   // if is the first player that is connected in the room he has to insert the number of player of the match.
                tmpRoom.addConnectionToClient(tmpConnection.getNamePlayer(), tmpConnection, false);
                //System.out.println("send number of player request to room " + tmpRoom.getRoomNumber());
                tmpConnection.sendNumPlayerRequest("You are the first, write number of player");
                tmpRoom.setSendNumPlayer(true);
                // TODO dovrei salvarmi anche il nome di chi lo ha mandato per poterlo rimandare in caso che non mi risponde
                tmpRoom.setPlayerSendNumPlayer(playerName.getPlayerName());
            }else if(tmpRoom.getNumPlayer()>1 && tmpRoom.getConnections().size()<tmpRoom.getNumPlayer() && !tmpRoom.isStart()){ //id is not the first player connected to the match.
                tmpRoom.addConnectionToClient(tmpConnection.getNamePlayer(), tmpConnection, false);
                if(tmpRoom.getConnections().size()==tmpRoom.getNumPlayer()){
                    tmpRoom.startGame();
                }
            }else if(tmpRoom.isStart() && tmpRoom.isFullPlayer()){ // if the match is start and is full of player
                tmpConnection.sendRoomRequestToClient("the match in this room has already start: select another room");
            }else if(tmpRoom.isStart() && !tmpRoom.isFullPlayer()){ // if the match is start but is not full of player --> player that try to rejoin the match.
                if(checkReconnection(playerName.getPlayerName())){
                    //System.out.println("try reconnection of player: " +playerName.getPlayerName() + " to room: " + tmpRoom.getRoomNumber());
                    tmpRoom.addConnectionToClient(tmpConnection.getNamePlayer(), tmpConnection, true);
                }else{
                    tmpConnection.sendNamePlayerRequest("This player is not disconnected, write the old nickname");
                }
            }
        }else{
            tmpConnection.sendNamePlayerRequest("Nickname choose already exist, choose another nickname");
        }

    }

    @Override
    public void visit(SendNumPlayerToServer numPlayer) {
        tmpRoom.setNumPlayer(numPlayer.getNumPlayer());
        if(tmpRoom.getConnections().size()==tmpRoom.getNumPlayer()){
            tmpRoom.startGame();
        }

    }

    @Override
    public void observeEvent(EventToServer event) {
        ((EventToServerInitial)event).acceptInitialServerVisitor(this);
    }

    /**
     * Checks id the room already exists or not.
     * @param room The room to check.
     * @return If the room already exists or not
     */
    private boolean existRoom(int room){
        if(server.getRooms().containsKey((Integer) room)){
            return true;
        }
        return false;
    }

    /**
     * Checks if to player wants a name already used.
     * @param name the name that the player/client want to choose.
     * @return If the name is already used or not.
     */
    private boolean checkDoublePlayerName(String name){
        for(String s : tmpRoom.getConnections().keySet()){
            if(s.equals(name)){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the player that want to rejoin the match is one of the player in the disconnected player list.
     * @param namePlayer The name of the player who wants to rejoin the match.
     * @return If the player is in the list of disconnected players.
     */
    private boolean checkReconnection(String namePlayer){ // return true if the player/client could rejoin the match
        return tmpRoom.getDisconnectionHandler().checkReconnection(namePlayer);
    }

}


