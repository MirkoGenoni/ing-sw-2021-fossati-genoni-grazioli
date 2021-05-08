package it.polimi.ingsw.Events.ServerToClient;

public class EndGameToClient extends EventToClient {
    private final String message;

    public EndGameToClient(String message) {
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