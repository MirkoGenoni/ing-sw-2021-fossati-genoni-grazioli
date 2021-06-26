package it.polimi.ingsw.Events.ServerToClient;

/**
 * This class represents the event to send to the client for the reselection of the turn.
 * With this event the controller notify to the client that the turn is not valid.
 *
 * @author Stefano Fossati
 */
public class TurnReselectionToClient extends EventToClient {
    private final String message;

    /**
     * Constructs the event.
     * @param message The message to notify to the client.
     */
    public TurnReselectionToClient(String message) {
        this.message = message;
    }

    /**
     * Getter of the message to notify to the client.
     * @return The message to notify to the client.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
