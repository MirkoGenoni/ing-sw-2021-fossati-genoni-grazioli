package it.polimi.ingsw.Events.ClientToServer;

import java.io.Serializable;

public abstract class EventToServer implements Serializable {
    public abstract void acceptServerVisitor(EventToServerVisitor visitor);
}
