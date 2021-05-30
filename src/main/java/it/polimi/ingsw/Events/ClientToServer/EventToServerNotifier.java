package it.polimi.ingsw.Events.ClientToServer;

import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

// list of the event that the client could send to the server
public interface EventToServerNotifier {

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE SERVER
    // -------------------------------------------------------


    // -------------------------------------------
    // EVENTS FOR THE LEADER CARD INTERACTION
    // -------------------------------------------
    void sendDiscardInitialLeaderCards(int leaderCard1, int leaderCard2);
    void sendLeaderCardTurn(ArrayList<Integer> positions);

    // -------------------------------------------
    // EVENTS FOR THE MARKET TURN INTERACTION
    // -------------------------------------------
    void sendChooseLine(int numLine, ArrayList<Boolean> leaderMarketWhiteChange);
    void sendNewDepositState(ArrayList<Resource> newDepositState, int discardResources , boolean isAdditional, ArrayList<Resource> additionalDepositState);

    // ------------------------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN INTERACTION
    // ------------------------------------------------------
    void sendSelectedDevelopmentCard(int color, int level);
    void sendSelectedDevelopmentCardSpace(int space);

    // ------------------------------------------------------
    // EVENTS FOR THE USE DEVELOPMENT CARD
    // ------------------------------------------------------
    void sendSelectedProductionDevelopmentCard(boolean useBaseProduction, Resource resourceRequested1, Resource resourceRequested2,
                                               ProductedMaterials resourceGranted, ArrayList<Boolean> useLeaders,
                                               ArrayList<Resource> materialLeaders, ArrayList<Boolean> useDevelop);

    // ------------------------------------------------------
    // EVENT FOR NEW TURN INTERACTION
    // ------------------------------------------------------
    void sendTurnPlayed(String turnType);
    void sendInitialDepositState(ArrayList<Resource> newInitialDepositState);

    // ------------------------------------------------------
    // EVENT FOR LORENZO TURN
    // ------------------------------------------------------
    void sendReplayLorenzoAction();

    void sendRoom(int room, boolean newRoom);
    void sendPlayerName(String playerName);
    void sendNumPlayer(int num);

}
