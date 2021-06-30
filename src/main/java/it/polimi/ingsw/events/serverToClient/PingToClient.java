package it.polimi.ingsw.events.serverToClient;

/**
 * This event represents the ping that verifies that the client is connected.
 */
public class PingToClient extends EventToClient{
    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}