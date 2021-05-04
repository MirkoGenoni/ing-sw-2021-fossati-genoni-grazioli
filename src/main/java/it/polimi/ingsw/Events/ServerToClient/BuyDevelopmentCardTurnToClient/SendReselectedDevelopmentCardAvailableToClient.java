package it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;

public class SendReselectedDevelopmentCardAvailableToClient extends EventToClient {
    private final String message;

    public SendReselectedDevelopmentCardAvailableToClient(String message) {
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
