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


    // -------------------------------------------
    // EVENTS THAT RECEIVE LEADER CARD INFORMATION
    // -------------------------------------------
    void visit(SendArrayLeaderCardsToClient leaderCardArray);

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
    // OTHER EVENTS
    // ----------------------------------
    void visit(NotifyToClient message);
    void visit(NewTurnToClient newTurn);
    void visit(EndGameToClient message);
    void visit(SendInitialResourcesToClient numResources);
    void visit(LorenzoActionToClient lorenzoAction);

    void visit(SendRoomRequestToClient roomRequest);
    void visit(SendNamePlayerRequestToClient nameRequest);
    void visit(SendNumPlayerRequestToClient numPlayer);
}
