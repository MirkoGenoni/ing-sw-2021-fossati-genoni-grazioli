package it.polimi.ingsw.events.servertoclient;

import java.io.Serializable;

/**
 * This abstract class is used to generalize the event to sent from the server to the client.
 * @see Serializable
 *
 * @author Stefano Fossati
 */
public abstract class EventToClient implements Serializable {
    /**
     * This method allows the class that implements EventToClientVisitor in the client side to read and recognise this class as event send from the server.
     * @param visitor the class that implements EventToClientVisitor interface.
     */
    public abstract void acceptVisitor(EventToClientVisitor visitor);
}
