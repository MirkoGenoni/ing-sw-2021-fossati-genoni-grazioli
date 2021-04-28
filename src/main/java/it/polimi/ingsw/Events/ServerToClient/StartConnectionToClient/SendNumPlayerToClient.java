package it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;

public class SendNumPlayerToClient extends EventToClient {
    private final String message;

    public SendNumPlayerToClient(String message) {
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
