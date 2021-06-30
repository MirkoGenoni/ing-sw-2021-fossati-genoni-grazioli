package it.polimi.ingsw.events.clienttoserver.initialconnectiontoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;
import it.polimi.ingsw.server.roomhandler.EventToServerInitialVisitor;

import java.io.Serializable;

/**
 * This abstract class is used to generalize the initial event to sent from the client to the server.
 * @see Serializable
 *
 * @author Stefano Fossati
 */
public abstract class EventToServerInitial extends EventToServer {
    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
    }

    /**
     * This method allows the class that implements EventToServerInitialVisitor in the server side to read and recognise this class as initial event send from the client.
     * @param visitor the class that implements EventToServerInitialVisitor interface.
     */
    public abstract void acceptInitialServerVisitor(EventToServerInitialVisitor visitor);
}
