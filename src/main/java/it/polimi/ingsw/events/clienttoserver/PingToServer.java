package it.polimi.ingsw.events.clienttoserver;

/**
 * This event represents the ping that verifies the connection with the server.
 *
 * @author Stefano Fossati
 */
public class PingToServer extends EventToServer{
    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
    }
}
