package it.polimi.ingsw.Events.ClientToServer;

import it.polimi.ingsw.Events.ClientToServer.BuyDevelopmentCardToServer.SelectedDevelopmentCardSpaceToServer;
import it.polimi.ingsw.Events.ClientToServer.BuyDevelopmentCardToServer.SelectedDevelopmentCardToBuyToServer;
import it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer.ChooseLineToServer;
import it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer.NewDepositStateToServer;
import it.polimi.ingsw.Events.ClientToServer.StartConnectionToServer.NumPlayerToServer;
import it.polimi.ingsw.Events.ClientToServer.StartConnectionToServer.PlayerNameToServer;
import it.polimi.ingsw.Events.ClientToServer.StartGameToServer.DiscardInitialLeaderCards;
import it.polimi.ingsw.Events.ClientToServer.StartGameToServer.InitialResourcesChoose;

// list of the event that the server receive form client
public interface EventToServerVisitor {

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE SERVER
    // -------------------------------------------------------
    void visit(NumPlayerToServer numPlayer);
    void visit(PlayerNameToServer newPlayerName);

    // -------------------------------------------
    // EVENTS FOR THE LEADER CARD INTERACTION
    // -------------------------------------------
    void visit(DiscardInitialLeaderCards leaderCards);
    void visit(SendLeaderCardTurnToServer leaderCardTurn);

    // -------------------------------------------
    // EVENTS FOR THE MARKET TURN INTERACTION
    // -------------------------------------------
    void visit(ChooseLineToServer numLine);
    void visit(NewDepositStateToServer newDepositState);

    // ------------------------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN INTERACTION
    // ------------------------------------------------------
    void visit(SelectedDevelopmentCardToBuyToServer selectedDevelopmentCard);
    void visit(SelectedDevelopmentCardSpaceToServer selectedDevelopmentCardSpace);

    // ------------------------------------------------------
    // EVENTS FOR THE USE DEVELOPMENT CARD
    // ------------------------------------------------------
    void visit(SelectedProductionDevelopmentCardToServer sendProductionDevelopmentCard);

    // ------------------------------------------------------
    // EVENTS FOR NEW TURN INTERACTION
    // ------------------------------------------------------
    void visit(TurnPlayedToServer turn);
    void visit(InitialResourcesChoose newInitialDepositState);

    // ------------------------------------------------------
    // EVENT FOR LORENZO TURN
    // ------------------------------------------------------
    void visit(ReplayLorenzoActionToServer replayLorenzoAction);

}
