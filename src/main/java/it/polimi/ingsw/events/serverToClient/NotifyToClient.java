package it.polimi.ingsw.events.serverToClient;

/**
 * This class represents the event to send to client used for a notify.
 *
 * @author Stefano Fossati.
 */
public class NotifyToClient extends EventToClient {
    private final String message;

    /**
     * Constructs the event.
     * @param message The message to send to the client.
     */
    public NotifyToClient(String message) {
        this.message = message;
    }

    /**
     * Getter of the message sent to the client.
     * @return The message sent to the client.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
