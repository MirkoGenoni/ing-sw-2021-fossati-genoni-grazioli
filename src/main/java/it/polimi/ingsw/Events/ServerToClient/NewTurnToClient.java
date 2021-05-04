package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.MarketToClient;

// this event ask at the client what type of turn want to do
public class NewTurnToClient extends EventToClient{
    private final int turnNumber;
    private final MarketToClient market;
    private final DevelopmentCardToClient[][] developmentCards;

    public NewTurnToClient(int turnNumber, MarketToClient market, DevelopmentCardToClient[][] developmentCards) {
        this.turnNumber = turnNumber;
        this.market = market;
        this.developmentCards = developmentCards;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public MarketToClient getMarket() {
        return market;
    }

    public DevelopmentCardToClient[][] getDevelopmentCards() {
        return developmentCards;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor){
        visitor.visit(this);
    }
}
