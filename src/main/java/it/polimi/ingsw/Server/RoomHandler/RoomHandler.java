package it.polimi.ingsw.Server.RoomHandler;

import it.polimi.ingsw.Controller.ObserveConnectionToClient;
import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.EventToServerInitial;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendNumPlayerToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendPlayerNameToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendRoomToServer;
import it.polimi.ingsw.Server.ConnectionToClient;
import it.polimi.ingsw.Server.Server;


public class RoomHandler implements EventToServerInitialVisitor, ObserveConnectionToClient{
    private final Server server;
    private boolean active;

    private Room tmpRoom;
    private ConnectionToClient tmpConnection;


    public RoomHandler(Server server){
        this.server = server;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setTmpConnection(ConnectionToClient tmpConnection) {
        this.tmpConnection = tmpConnection;

    }


    @Override
    public void visit(SendRoomToServer room) {
        if(room.isNewRoom() && !existRoom(room.getRoomNumber())){  // se la stanza è nuova e non esiste già la creo e proseguo
            Room newRoom = new Room(room.getRoomNumber(), server);
            server.getRooms().put((Integer) newRoom.getRoomNumber(), newRoom);
            this.tmpRoom = newRoom;
            tmpConnection.sendNamePlayerRequest("write your nickname");
        }else if(room.isNewRoom() && existRoom(room.getRoomNumber())){ // se la stanza è nuova ma esiste gia mando errore
            tmpConnection.sendRoomRequestToClient("the room already exist");
        }else if(!room.isNewRoom() && !existRoom(room.getRoomNumber())){ // se la stanza non è nuova e non esiste mando errore
            tmpConnection.sendRoomRequestToClient("this room doesn't exist");
        }else if(!room.isNewRoom() && existRoom(room.getRoomNumber())){ // se la stanza non è nuova ed esiste proseguo e non è iniziata
            this.tmpRoom = server.getRooms().get((Integer) room.getRoomNumber());
            if(!tmpRoom.isStart()){
                tmpConnection.sendNamePlayerRequest("write your nickname");
            }else if(tmpRoom.isStart() && tmpRoom.isFullPlayer()){
                tmpConnection.sendRoomRequestToClient("the match in this room has already start: select another room");
            }else if(tmpRoom.isStart() && !tmpRoom.isFullPlayer()){
                tmpConnection.sendNamePlayerRequest("write your old nickname to reconnect at this match");
            }

        }




    }

    @Override
    public void visit(SendPlayerNameToServer playerName) {
        System.out.println("il nome è " + playerName.getPlayerName());
        if(checkDoublePlayerName(playerName.getPlayerName())){
            tmpConnection.setNamePlayer(playerName.getPlayerName());


            if(tmpRoom.getNumPlayer()<0 && tmpRoom.isSendNumPlayer()){
                while(tmpRoom.getNumPlayer()<0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(tmpRoom.getNumPlayer()>1 && tmpRoom.getConnections().size()<tmpRoom.getNumPlayer()){
                    tmpRoom.addConnectionToClient(tmpConnection.getNamePlayer(), tmpConnection, false);
                    if(tmpRoom.getConnections().size()==tmpRoom.getNumPlayer()){
                        tmpRoom.startGame();
                    }
                }else{
                    tmpConnection.sendRoomRequestToClient("this game is full of player, choose another room");
                }
            }else if(!tmpRoom.isSendNumPlayer()){   // è il primo a connettersi quindi deve dire il numero di giocatori
                tmpRoom.addConnectionToClient(tmpConnection.getNamePlayer(), tmpConnection, false);
                System.out.println("mando numero");
                tmpConnection.sendNumPlayerRequest("You are the first, write number of player");
                tmpRoom.setSendNumPlayer(true);
            }else if(tmpRoom.getNumPlayer()>1 && tmpRoom.getConnections().size()<tmpRoom.getNumPlayer() && !tmpRoom.isStart()){
                tmpRoom.addConnectionToClient(tmpConnection.getNamePlayer(), tmpConnection, false);
                if(tmpRoom.getConnections().size()==tmpRoom.getNumPlayer()){
                    tmpRoom.startGame();
                }
            }else if(tmpRoom.isStart() && tmpRoom.isFullPlayer()){ // se la partita è già iniziata
                tmpConnection.sendRoomRequestToClient("the match in this room has already start: select another room");
            }else if(tmpRoom.isStart() && !tmpRoom.isFullPlayer()){
                if(checkReconnection(playerName.getPlayerName())){
                    System.out.println("try reconnection");
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
            System.out.println("inizia il gioco della stanza " + tmpRoom.getRoomNumber());
        }

    }

    @Override
    public void observeEvent(EventToServer event) {
        ((EventToServerInitial)event).acceptInitialServerVisitor(this);
    }


    private boolean existRoom(int room){
        if(server.getRooms().containsKey((Integer) room)){
            return true;
        }
        return false;
    }

    private boolean checkDoublePlayerName(String name){
        for(String s : tmpRoom.getConnections().keySet()){
            if(s.equals(name)){
                return false;
            }
        }
        return true;
    }

    private boolean checkReconnection(String namePlayer){ // return true se si può riconnettere
        return tmpRoom.getDisconnectionHandler().checkReconnection(namePlayer);
    }

}


