package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Lorenzo.SoloAction;
import it.polimi.ingsw.Model.Market.Market;
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
    void sendArrayLeaderCards(ArrayList<LeaderCard> leaderCards, boolean initialLeaderCards, Player currentPlayer);

    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------
    void sendReorganizeDeposit(ArrayList<Resource> marketResources, ArrayList<Resource> depositState, boolean isAdditional, ArrayList<Resource> additionalType, ArrayList<Resource> additionalState);

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------
    void sendTurnReselection(String message);
    void sendDevelopmentCardSpace(ArrayList<Boolean> developmentCardSpace);

    // ----------------------------------
    // OTHER EVENTS
    // ----------------------------------
    void sendNotify(String message);
    void sendNewTurn(int turnNumber, Market market, DevelopmentCard[][] developmentCards, Player[] players, FaithTrack faithTrack, boolean yourTurn);
    void sendEndGame(String message);
    void sendInitialResources(int numResources, ArrayList<Resource> depositState);
    void sendLorenzoTurn(SoloAction lorenzoAction, int lorenzoPosition);
}
