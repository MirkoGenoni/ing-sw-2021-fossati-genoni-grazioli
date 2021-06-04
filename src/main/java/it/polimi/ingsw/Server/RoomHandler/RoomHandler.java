package it.polimi.ingsw.Server.RoomHandler;

import it.polimi.ingsw.Controller.ObserveConnectionToClient;
import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.EventToServerInitial;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendNumPlayerToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendPlayerNameToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendRoomToServer;
import it.polimi.ingsw.Server.ConnectionToClient;

import java.util.Map;

public class RoomHandler implements EventToServerInitialVisitor, ObserveConnectionToClient{
    private final Map<Integer, Room> multiGames;
    private boolean active;

    private Room tmpRoom;
    private ConnectionToClient tmpConnection;


    public RoomHandler(Map<Integer, Room> multiGames){
        this.multiGames = multiGames;
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
            Room newRoom = new Room(room.getRoomNumber());
            multiGames.put((Integer) newRoom.getRoomNumber(), newRoom);
            this.tmpRoom = newRoom;
            tmpConnection.sendNamePlayerRequest("write your name");
        }else if(room.isNewRoom() && existRoom(room.getRoomNumber())){ // se la stanza è nuova ma esiste gia mando errore
            tmpConnection.sendRoomRequestToClient("the room already exist");
        }else if(!room.isNewRoom() && !existRoom(room.getRoomNumber())){ // se la stanza non è nuova e non esiste mando errore
            tmpConnection.sendRoomRequestToClient("this room doesn't exist");
        }else if(!room.isNewRoom() && existRoom(room.getRoomNumber())){ // se la stanza non è nuova ed esiste proseguo
            this.tmpRoom = multiGames.get((Integer) room.getRoomNumber());
            tmpConnection.sendNamePlayerRequest("write your name");
        }else if(existRoom(room.getRoomNumber()) && multiGames.get(room.getRoomNumber()).isStart()){
            tmpConnection.sendRoomRequestToClient("the match in this room has already start: select another room");
        }




    }

    @Override
    public void visit(SendPlayerNameToServer playerName) {
        System.out.println("il nome è" + playerName.getPlayerName());
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
                    tmpRoom.addConnectionToClient(tmpConnection.getNamePlayer(), tmpConnection);
                    if(tmpRoom.getConnections().size()==tmpRoom.getNumPlayer()){
                        tmpRoom.startGame();
                    }
                }else{
                    tmpConnection.sendRoomRequestToClient("this game is full of player, choose another room");
                }
            }else if(!tmpRoom.isSendNumPlayer()){   // è il primo a connettersi quindi deve dire il numero di giocatori
                tmpRoom.addConnectionToClient(tmpConnection.getNamePlayer(), tmpConnection);
                System.out.println("mando numero");
                tmpConnection.sendNumPlayerRequest("You are the first, write number of player");
                tmpRoom.setSendNumPlayer(true);
            }else if(tmpRoom.getNumPlayer()>1 && tmpRoom.getConnections().size()<tmpRoom.getNumPlayer()){
                tmpRoom.addConnectionToClient(tmpConnection.getNamePlayer(), tmpConnection);
                if(tmpRoom.getConnections().size()==tmpRoom.getNumPlayer()){
                    tmpRoom.startGame();
                }
            }else if(tmpRoom.isStart()){ // se la partita è già iniziata
                tmpConnection.sendRoomRequestToClient("the match in this room has already start: select another room");
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
        if(multiGames.containsKey((Integer) room)){
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

}


