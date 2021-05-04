package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.MarketToClient;
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
    void sendArrayLeaderCards(ArrayList<LeaderCard> leaderCards, boolean initialLeaderCards);

    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------
    void sendReorganizeDeposit(ArrayList<Resource> marketResources, ArrayList<Resource> depositState);

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------
    void sendReselectedDevelopmentCards(String message);
    void sendDevelopmentCardSpace(ArrayList<Boolean> developmentCardSpace);

    // ----------------------------------
    // OTHER EVENTS
    // ----------------------------------
    void sendNotify(String message);
    void sendNewTurn(int turnNumber, MarketToClient market, DevelopmentCardToClient[][] developmentCards);

}
