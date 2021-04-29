package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendDevelopmentCardToClient;
import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Market.Marble;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

// list of the event that the server could sent to the client
public interface EventToClientNotifier {

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------
    void sendPlayerName(String playerName);
    void sendNumPlayer(String message);

    // ----------------------------------------
    // EVENTS THAT SEND LEADER CARD INFORMATION
    // ----------------------------------------
    void sendLeaderCard(LeaderCard leaderCard);
    void sendArrayLeaderCards(ArrayList<LeaderCard> leaderCards);

    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------
    void sendMarket(ArrayList<Marble> grid, Marble outMarble);
    void sendReorganizeDeposit(ArrayList<Resource> marketResources, ArrayList<Resource> depositState);

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------
    void sendDevelopmentCard(DevelopmentCard developmentCard);
    void sendDevelopmentCards(SendDevelopmentCardToClient[][] availableDevelopmentCards);

    // ----------------------------------
    // OTHER EVENTS
    // ----------------------------------
    void sendNotify(String message);
    void sendNewTurn(int turnNumber);

}
