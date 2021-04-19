package it.polimi.ingsw.Events.ServerToClient;

// this event notify the start of the game to the client
public class StartMatchToClient extends EventToClient{
    private final String message;

    public StartMatchToClient(String message) {
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
