package it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;

public class SendNamePlayerRequestToClient extends EventToClient {
    private final String request;

    public SendNamePlayerRequestToClient(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
