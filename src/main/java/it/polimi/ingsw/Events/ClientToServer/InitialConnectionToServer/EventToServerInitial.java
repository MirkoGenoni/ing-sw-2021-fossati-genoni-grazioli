package it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer;

import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServerVisitor;
import it.polimi.ingsw.Server.RoomHandler.EventToServerInitialVisitor;

public abstract class EventToServerInitial extends EventToServer {
    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {

    }

    public abstract void acceptInitialServerVisitor(EventToServerInitialVisitor visitor);
}
