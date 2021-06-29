package it.polimi.ingsw.events.serverToClient.initialConnectionToClient;

import it.polimi.ingsw.events.serverToClient.EventToClient;
import it.polimi.ingsw.events.serverToClient.EventToClientVisitor;


/**
 * This class represents the event to send to the client for the initial connection. In particular this event is the room request send to the client.
 *
 * @author Stefano Fossati
 */
public class SendRoomRequestToClient extends EventToClient {
    private final String message;

    /**
     * Constructs the event.
     * @param message The message to send to the client for the choice of the room in which join.
     */
    public SendRoomRequestToClient(String message){
        this.message = message;
    }

    /**
     * Getter of the message to send to the client for the choice of the room in which join.
     * @return The message to send to the client for the choice of the room in which join.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
