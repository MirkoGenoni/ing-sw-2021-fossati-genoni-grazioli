package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.MarketToClient;
import it.polimi.ingsw.Model.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.Map;

// this event ask at the client what type of turn want to do
public class NewTurnToClient extends EventToClient{
    private final int turnNumber;
    private final MarketToClient market;
    private final DevelopmentCardToClient[][] developmentCards;
    private final ArrayList<Resource> depositState;
    private final Map<Resource, Integer> strongbox;
    private final ArrayList<SendLeaderCardToClient> leaderCarsActive;
    private final ArrayList<DevelopmentCardToClient> developmentCard;

    private final  ArrayList<Integer> popeFavorTiles;
    private final int faithMarkerPosition;

    public NewTurnToClient(int turnNumber, MarketToClient market, DevelopmentCardToClient[][] developmentCards,
                           ArrayList<Resource> depositState, Map<Resource, Integer> strongbox, ArrayList<SendLeaderCardToClient> leaderCarsActive,
                           ArrayList<DevelopmentCardToClient> developmentCard, ArrayList<Integer> popeFavorTiles, int faithMarkerPosition) {
        this.turnNumber = turnNumber;
        this.market = market;
        this.developmentCards = developmentCards;
        this.depositState = depositState;
        this.strongbox = strongbox;
        this.leaderCarsActive = leaderCarsActive;
        this.developmentCard = developmentCard;
        this.popeFavorTiles = popeFavorTiles;
        this.faithMarkerPosition = faithMarkerPosition;
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

    public ArrayList<Resource> getDepositState() {
        return depositState;
    }

    public Map<Resource, Integer> getStrongbox() {
        return strongbox;
    }

    public ArrayList<SendLeaderCardToClient> getLeaderCarsActive() {
        return leaderCarsActive;
    }

    public ArrayList<DevelopmentCardToClient> getDevelopmentCard() {
        return developmentCard;
    }

    public ArrayList<Integer> getPopeFavorTiles() {
        return popeFavorTiles;
    }

    public int getFaithMarkerPosition() {
        return faithMarkerPosition;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor){
        visitor.visit(this);
    }
}
