package it.polimi.ingsw.Events.ServerToClient;

// this event notify the client with a message
public class NotifyToClient extends EventToClient {
    private final String message;

    public NotifyToClient(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
