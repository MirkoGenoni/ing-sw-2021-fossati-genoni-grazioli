package it.polimi.ingsw.events.servertoclient;

/**
 * This event represents the ping that verifies that the client is connected.
 *
 * @author Stefano Fossati
 */
public class PingToClient extends EventToClient{
    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
