package it.polimi.ingsw.events.clienttoserver;

import it.polimi.ingsw.events.clienttoserver.buydevelopmentcardtoserver.SelectedDevelopmentCardSpaceToServer;
import it.polimi.ingsw.events.clienttoserver.buydevelopmentcardtoserver.SelectedDevelopmentCardToBuyToServer;
import it.polimi.ingsw.events.clienttoserver.marketturntoserver.ChooseLineToServer;
import it.polimi.ingsw.events.clienttoserver.marketturntoserver.NewDepositStateToServer;
import it.polimi.ingsw.events.clienttoserver.startgametoserver.DiscardInitialLeaderCards;
import it.polimi.ingsw.events.clienttoserver.startgametoserver.InitialResourcesChoose;

/**
 * This interface contains the list of the event that the server could receive form client.
 * This interface uses the overloading of the method to accept events.
 *
 * @author Stefano Fossati
 */
public interface EventToServerVisitor {

    // -------------------------------------------
    // EVENTS FOR THE INITIAL RESOURCES SELECTION
    // -------------------------------------------

    /**
     * Visit method of the new initial deposit event arrived from the client.
     * @param newInitialDepositState The new initial deposit event arrived from the client.
     * @see InitialResourcesChoose
     */
    void visit(InitialResourcesChoose newInitialDepositState);

    // -------------------------------------------
    // EVENTS FOR THE LEADER CARD INTERACTION
    // -------------------------------------------

    /**
     * Visit method of the discard initial leader cards event arrived from the client.
     * @param leaderCards The discard initial leader cards event arrived from the client.
     * @see DiscardInitialLeaderCards
     */
    void visit(DiscardInitialLeaderCards leaderCards);

    /**
     * Visit method of the leader card action event arrived from the client.
     * @param leaderCardTurn The leader card action event arrived from the client.
     * @see SendLeaderCardToServer
     */
    void visit(SendLeaderCardToServer leaderCardTurn);

    // -------------------------------------------
    // EVENTS FOR THE MARKET TURN INTERACTION
    // -------------------------------------------

    /**
     * Visit method of the choose line event arrived from the client.
     * @param numLine The choose line event arrived from the client.
     * @see ChooseLineToServer
     */
    void visit(ChooseLineToServer numLine);

    /**
     * Visit method of the new deposit state event arrived from the client.
     * @param newDepositState The new deposit state event arrived from the client.
     * @see NewDepositStateToServer
     */
    void visit(NewDepositStateToServer newDepositState);

    // ------------------------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN INTERACTION
    // ------------------------------------------------------

    /**
     * Visit method of the selected development card to buy event arrived from the client.
     * @param selectedDevelopmentCard The selected development card to buy event arrived from the client.
     * @see SelectedDevelopmentCardToBuyToServer
     */
    void visit(SelectedDevelopmentCardToBuyToServer selectedDevelopmentCard);

    /**
     * Visit method of the selected development card space event arrived from the client.
     * @param selectedDevelopmentCardSpace The selected development card space event arrived from the client.
     * @see SelectedDevelopmentCardSpaceToServer
     */
    void visit(SelectedDevelopmentCardSpaceToServer selectedDevelopmentCardSpace);

    // ------------------------------------------------------
    // EVENTS FOR THE ACTIVATION OF PRODUCTION
    // ------------------------------------------------------

    /**
     * Visit method of the selected production event arrived from the client.
     * @param sendProductionDevelopmentCard The selected production event arrived from the client.
     * @see SelectedProductionToServer
     */
    void visit(SelectedProductionToServer sendProductionDevelopmentCard);

    // ------------------------------------------------------
    // EVENTS FOR NEW TURN INTERACTION
    // ------------------------------------------------------

    /**
     * Visit method for the choose of the type of the turn that the player choose. This method is used only for the debug and test.
     * If the player on the CLI types 'turn' the player skip the turn.
     * @param turn The type of turn choose by the player.
     * @see TurnPlayedToServer
     */
    void visit(TurnPlayedToServer turn);


    // ----------------------------------
    // EVENT FOR THE SINGLE GAME
    // ----------------------------------

    /**
     * Visit method of the reply lorenzo action event arrived from the client.
     * @param replayLorenzoAction The reply lorenzo action event arrived from the client.
     * @see ReplayLorenzoActionToServer
     */
    void visit(ReplayLorenzoActionToServer replayLorenzoAction);

}
