package it.polimi.ingsw.Events.ClientToServer;

import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

// list of the event that the client could send to the server
public interface EventToServerNotifier {

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE SERVER
    // -------------------------------------------------------
    void sendNumPlayer(int numPlayer);
    void sendNewPlayerName(String newPlayerName);

    // -------------------------------------------
    // EVENTS FOR THE LEADER CARD INTERACTION
    // -------------------------------------------
    void sendDiscardInitialLeaderCards(int leaderCard1, int leaderCard2);

    // -------------------------------------------
    // EVENTS FOR THE MARKET TURN INTERACTION
    // -------------------------------------------
    void sendChooseLine(int numLine);
    void sendNewDepositState(ArrayList<Resource> newDepositState, int discardResources);

    // ------------------------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN INTERACTION
    // ------------------------------------------------------
    void sendSelectedDevelopmentCard(int color, int level);

    // ------------------------------------------------------
    // EVENT FOR NEW TURN INTERACTION
    // ------------------------------------------------------
    void sendTurnPlayed(String turnType);

}
