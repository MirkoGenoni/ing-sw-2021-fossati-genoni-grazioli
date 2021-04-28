package it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;

public class SendDevelopmentCardAvailableToClient extends EventToClient {

    private final SendDevelopmentCardToClient[][] developmentCardsAvailable;

    public SendDevelopmentCardAvailableToClient(SendDevelopmentCardToClient[][] developmentCardsAvailable) {
        this.developmentCardsAvailable = developmentCardsAvailable;
    }

    public SendDevelopmentCardToClient[][] getDevelopmentCardsAvailable() {
        return developmentCardsAvailable;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
