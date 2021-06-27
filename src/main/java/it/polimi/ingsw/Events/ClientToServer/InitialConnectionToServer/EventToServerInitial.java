package it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer;

import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServerVisitor;
import it.polimi.ingsw.Server.RoomHandler.EventToServerInitialVisitor;

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
