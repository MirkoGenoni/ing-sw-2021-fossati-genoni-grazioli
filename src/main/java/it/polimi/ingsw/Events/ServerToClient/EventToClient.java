package it.polimi.ingsw.Events.ServerToClient;

import java.io.Serializable;

public abstract class EventToClient implements Serializable {
    public abstract void acceptVisitor(EventToClientVisitor visitor);
}
