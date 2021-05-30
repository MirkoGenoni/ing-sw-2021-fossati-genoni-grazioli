package it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer;

import it.polimi.ingsw.Server.RoomHandler.EventToServerInitialVisitor;

public class SendRoomToServer extends EventToServerInitial{
    private final int roomNumber;
    private final boolean newRoom;

    public SendRoomToServer(int roomNumber, boolean newRoom) {
        this.roomNumber = roomNumber;
        this.newRoom = newRoom;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isNewRoom() {
        return newRoom;
    }

    @Override
    public void acceptInitialServerVisitor(EventToServerInitialVisitor visitor) {
        visitor.visit(this);
    }
}
