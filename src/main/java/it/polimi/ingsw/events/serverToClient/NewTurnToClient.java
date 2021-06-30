package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.events.serverToClient.supportclass.DevelopmentCardToClient;
import it.polimi.ingsw.events.serverToClient.supportclass.MarketToClient;
import it.polimi.ingsw.events.serverToClient.supportclass.PlayerInformationToClient;

import java.util.Map;

/**
 * This class represents the event to client for the notification of a new turn and for the update of information.
 *
 * @author Stefano Fossati
 */
public class NewTurnToClient extends EventToClient{
    private final int turnNumber;
    private final MarketToClient market;
    private final DevelopmentCardToClient[][] developmentCards;
    private final Map<String, PlayerInformationToClient> players;
    private final boolean yourTurn;

    /**
     * Constructs the event
     * @param turnNumber The number of the turn.
     * @param market The structure of the market of the match.
     * @param developmentCards The structure of the development card on the table of the match.
     * @param players The players with their information.
     * @param yourTurn Indicates if is the turn on the client that receive this event or not.
     */
    public NewTurnToClient(int turnNumber, MarketToClient market, DevelopmentCardToClient[][] developmentCards, Map<String, PlayerInformationToClient> players, boolean yourTurn) {
        this.turnNumber = turnNumber;
        this.market = market;
        this.developmentCards = developmentCards;
        this.players = players;
        this.yourTurn = yourTurn;
    }

    /**
     * Getter of the number of the turn.
     * @return The number od the turn.
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Getter of the market structure.
     * @return The market structure.
     */
    public MarketToClient getMarket() {
        return market;
    }

    /**
     * Getter of the development card structure.
     * @return The development card structure.
     */
    public DevelopmentCardToClient[][] getDevelopmentCards() {
        return developmentCards;
    }

    /**
     * Getter of the structure of the players with all their information.
     * @return The structure of the players with all their information.
     */
    public Map<String, PlayerInformationToClient> getPlayers() {
        return players;
    }

    /**
     * Getter of the boolean that indicates if is the turn on the client that receive this event or not.
     * @return The boolean of the turn.
     */
    public boolean isYourTurn() {
        return yourTurn;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor){
        visitor.visit(this);
    }
}
