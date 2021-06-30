package it.polimi.ingsw.events.serverToClient.initialconnectiontoclient;

import it.polimi.ingsw.events.serverToClient.EventToClient;
import it.polimi.ingsw.events.serverToClient.EventToClientVisitor;

/**
 * This class represents the event to send to the client for the initial connection. In particular this event is the player name request send to the client.
 *
 * @author Stefano Fossati
 */
public class SendNamePlayerRequestToClient extends EventToClient {
    private final String request;

    /**
     * Constructs the event.
     * @param request The message to send to the client for the choice of the own name.
     */
    public SendNamePlayerRequestToClient(String request) {
        this.request = request;
    }

    /**
     * Getter of the message to send to the client for the choice of the own name.
     * @return The message to send to the client for the choice of the own name.
     */
    public String getRequest() {
        return request;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
