package it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer;

import it.polimi.ingsw.Server.RoomHandler.EventToServerInitialVisitor;

/**
 * This class represents the event to send to the server for the initial connection. In particular this event is the room answer send from the client to the server.
 *
 * @author Stefano Fossati
 */
public class SendRoomToServer extends EventToServerInitial{
    private final int roomNumber;
    private final boolean newRoom;

    /**
     * Constructs the event.
     * @param roomNumber The room number choose by the player.
     * @param newRoom Is true if the room is new, is false if the room already exists.
     */
    public SendRoomToServer(int roomNumber, boolean newRoom) {
        this.roomNumber = roomNumber;
        this.newRoom = newRoom;
    }

    /**
     * Getter of the room number choose by the player.
     * @return The room number choose by the player.
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Getter of the boolean that indicates in the room is new or already exists.
     * @return The boolean that indicates in the room is new or already exists.
     */
    public boolean isNewRoom() {
        return newRoom;
    }

    @Override
    public void acceptInitialServerVisitor(EventToServerInitialVisitor visitor) {
        visitor.visit(this);
    }
}
