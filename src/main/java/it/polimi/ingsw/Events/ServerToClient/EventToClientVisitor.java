package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendSpaceDevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendNamePlayerRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendNumPlayerRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendRoomRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.SendReorganizeDepositToClient;

// list of the event that the client receive from the server
public interface EventToClientVisitor {

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------
    void visit(SendRoomRequestToClient roomRequest);
    void visit(SendNamePlayerRequestToClient nameRequest);
    void visit(SendNumPlayerRequestToClient numPlayer);

    // ----------------------------------
    // EVENT FOR THE INITIAL RESOURCES
    // ----------------------------------
    void visit(SendInitialResourcesToClient numResources);

    // -------------------------------------------
    // EVENTS THAT RECEIVE LEADER CARD INFORMATION
    // -------------------------------------------
    void visit(SendArrayLeaderCardsToClient leaderCardArray);

    // -------------------------------------------------------------------
    // EVENT FOR THE NEW TURN, THIS EVENT UPDATE THE CLIENT INFORMATION
    // -------------------------------------------------------------------
    void visit(NewTurnToClient newTurn);

    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------
    void visit(SendReorganizeDepositToClient newResources);

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------
    void visit(TurnReselection message);
    void visit(SendSpaceDevelopmentCardToClient developmentCardSpace);

    // ----------------------------------
    // EVENT FOR NOTIFY THE CLIENT
    // ----------------------------------
    void visit(NotifyToClient message);


    // ----------------------------------
    // FINAL EVENT
    // ----------------------------------
    void visit(EndGameToClient message);

    // ----------------------------------
    // EVENT FOR THE SINGLE GAME
    // ----------------------------------
    void visit(LorenzoActionToClient lorenzoAction);

    void visit(PingToClient ping);
}
