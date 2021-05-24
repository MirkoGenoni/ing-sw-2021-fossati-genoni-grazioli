package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.MarketToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.PlayerInformationToClient;

import java.util.ArrayList;

// this event ask at the client what type of turn want to do
public class NewTurnToClient extends EventToClient{
    private final int turnNumber;
    private final MarketToClient market;
    private final DevelopmentCardToClient[][] developmentCards;
    private final ArrayList<PlayerInformationToClient> players;
    private final boolean yourTurn;

    public NewTurnToClient(int turnNumber, MarketToClient market, DevelopmentCardToClient[][] developmentCards, ArrayList<PlayerInformationToClient> players, boolean yourTurn) {
        this.turnNumber = turnNumber;
        this.market = market;
        this.developmentCards = developmentCards;
        this.players = players;
        this.yourTurn = yourTurn;
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

    public ArrayList<PlayerInformationToClient> getPlayers() {
        return players;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor){
        visitor.visit(this);
    }
}
