package it.polimi.ingsw.events.servertoclient;

import it.polimi.ingsw.events.servertoclient.supportclass.DevelopmentCardToClient;
import it.polimi.ingsw.events.servertoclient.supportclass.MarketToClient;
import it.polimi.ingsw.events.servertoclient.supportclass.PlayerInformationToClient;

import java.util.Map;

/**
 * This class represents the end game event to send to the client.
 * The event contains all the information of the final state of the game and the information of the players' points.
 *
 * @author Stafano Fossati
 */
public class EndGameToClient extends EventToClient {
    private final String message;
    private final Map<String, Integer> playersPoint;
    private final Map<String, PlayerInformationToClient> playerInformation;
    private final boolean lorenzo;
    private final int lorenzoPosition;
    private final DevelopmentCardToClient[][] devCard;
    private final MarketToClient market;

    /**
     * Constructs the event.
     * @param message The massage to send to the client.
     * @param playersPoint The points of all players.
     * @param playerInformation The information of all player.
     * @param lorenzo Is true if is a single player match, else is false.
     * @param lorenzoPosition The position of lorenzo in the faith track.
     * @param devCard The developments cards on the table.
     * @param market The state of the market of the match.
     */
    public EndGameToClient(String message, Map<String, Integer> playersPoint, Map<String, PlayerInformationToClient> playerInformation,
                           boolean lorenzo, int lorenzoPosition, DevelopmentCardToClient[][] devCard, MarketToClient market) {
        this.message = message;
        this.playersPoint = playersPoint;
        this.playerInformation = playerInformation;
        this.lorenzo = lorenzo;
        this.lorenzoPosition = lorenzoPosition;
        this.devCard = devCard;
        this.market = market;
    }

    /**
     * Getter of the massage to send to the client.
     * @return The massage to send to the client.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter of the map structure with the points of all player.
     * @return The map structure with the points of all player.
     */
    public Map<String, Integer> getPlayersPoint() {
        return playersPoint;
    }

    /**
     * Getter of the map structure with the information of all player.
     * @return The map structure with the information of all player.
     */
    public Map<String, PlayerInformationToClient> getPlayerInformation() {
        return playerInformation;
    }

    /**
     * Getter of the boolean that indicates if there is lorenzo or not.
     * @return The boolean that indicates if there is lorenzo or not.
     */
    public boolean isLorenzo() {
        return lorenzo;
    }

    /**
     * Getter of the position of lorenzo in the faith track.
     * @return The position of lorenzo in the faith track.
     */
    public int getLorenzoPosition() {
        return lorenzoPosition;
    }

    /**
     * Getter of the developments cards on the table.
     * @return The developments cards on the table.
     */
    public DevelopmentCardToClient[][] getDevCard() {
        return devCard;
    }

    /**
     * Getter of the state of the market of the match.
     * @return The state of the market of the match.
     */
    public MarketToClient getMarket() {
        return market;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
