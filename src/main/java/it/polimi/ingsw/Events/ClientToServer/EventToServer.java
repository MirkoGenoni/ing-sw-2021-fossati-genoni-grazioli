package it.polimi.ingsw.Events.ClientToServer;

import java.io.Serializable;

/**
 * This abstract class is used to generalize the event to sent from the client to the server.
 * @see Serializable
 *
 * @author Stefano Fossati
 */
public abstract class EventToServer implements Serializable {
    /**
     * This method allows the class that implements EventToServerVisitor in the server side to read and recognise this class as event send from the client.
     * @param visitor the class that implements EventToServerVisitor interface.
     */
    public abstract void acceptServerVisitor(EventToServerVisitor visitor);
}
