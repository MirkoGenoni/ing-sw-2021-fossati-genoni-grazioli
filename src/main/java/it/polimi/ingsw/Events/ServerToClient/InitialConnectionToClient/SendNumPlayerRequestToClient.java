package it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;

public class SendNumPlayerRequestToClient extends EventToClient {
    private final String message;

    public SendNumPlayerRequestToClient(String message) {
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
