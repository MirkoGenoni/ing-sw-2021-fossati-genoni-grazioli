package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.events.serverToClient.supportClass.DevelopmentCardToClient;
import it.polimi.ingsw.events.serverToClient.supportClass.MarketToClient;
import it.polimi.ingsw.events.serverToClient.supportClass.PlayerInformationToClient;

import java.util.Map;

public class EndGameToClient extends EventToClient {
    private final String message;
    private final Map<String, Integer> playersPoint;
    private final Map<String, PlayerInformationToClient> playerInformation;
    private final boolean lorenzo;
    private final int lorenzoPosition;
    private final DevelopmentCardToClient[][] devCard;
    private final MarketToClient market;

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

    public String getMessage() {
        return message;
    }

    public Map<String, Integer> getPlayersPoint() {
        return playersPoint;
    }

    public Map<String, PlayerInformationToClient> getPlayerInformation() {
        return playerInformation;
    }

    public boolean isLorenzo() {
        return lorenzo;
    }

    public int getLorenzoPosition() {
        return lorenzoPosition;
    }

    public DevelopmentCardToClient[][] getDevCard() {
        return devCard;
    }

    public MarketToClient getMarket() {
        return market;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
