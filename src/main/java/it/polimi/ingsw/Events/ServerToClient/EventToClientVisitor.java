package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendSpaceDevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendNamePlayerRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendNumPlayerRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendRoomRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.SendReorganizeDepositToClient;

/**
 * This interface contains the list of the event that the client could receive from the server.
 * This interface uses the overloading of the method to accept events.
 *
 * @author Stefano Fossati
 */
public interface EventToClientVisitor {

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------

    /**
     * Visit method of the room request event.
     * @param roomRequest The request of the room that arrived to the client.
     * @see SendRoomRequestToClient
     */
    void visit(SendRoomRequestToClient roomRequest);

    /**
     * Visit method of the player name request event.
     * @param nameRequest The request of player name that arrived to the client.
     * @see SendNamePlayerRequestToClient
     */
    void visit(SendNamePlayerRequestToClient nameRequest);

    /**
     * Visit method of the number of players request event.
     * @param numPlayer The request of the number of players that arrived to the client.
     * @see SendNumPlayerRequestToClient
     */
    void visit(SendNumPlayerRequestToClient numPlayer);

    // ----------------------------------
    // EVENT FOR THE INITIAL RESOURCES
    // ----------------------------------

    /**
     * Visit method of the initial resource event.
     * @param numResources The initial resource event that arrived to the client.
     * @see SendInitialResourcesToClient
     */
    void visit(SendInitialResourcesToClient numResources);

    // -------------------------------------------
    // EVENTS THAT RECEIVE LEADER CARD INFORMATION
    // -------------------------------------------

    /**
     * Visit method of the leader cards event.
     * @param leaderCardArray The leader cards event that arrived to the client.
     * @see SendArrayLeaderCardsToClient
     */
    void visit(SendArrayLeaderCardsToClient leaderCardArray);

    // -------------------------------------------------------------------
    // EVENT FOR THE NEW TURN, THIS EVENT UPDATE THE CLIENT INFORMATION
    // -------------------------------------------------------------------

    /**
     * Visit method of the new turn event.
     * @param newTurn The new turn event that arrived to the client.
     * @see NewTurnToClient
     */
    void visit(NewTurnToClient newTurn);

    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------

    /**
     * Visit method of the reorganize deposit event.
     * @param newResources The reorganize deposit event that arrived to the client.
     * @see SendReorganizeDepositToClient
     */
    void visit(SendReorganizeDepositToClient newResources);

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------

    /**
     * Visit method of the turn reselection event.
     * @param message The  turn reselection event that arrived to the client.
     * @see TurnReselection
     */
    void visit(TurnReselection message);

    /**
     * Visit method of the development card space event.
     * @param developmentCardSpace The development card space event that arrived to the client.
     * @see SendSpaceDevelopmentCardToClient
     */
    void visit(SendSpaceDevelopmentCardToClient developmentCardSpace);

    // ----------------------------------
    // EVENT FOR NOTIFY THE CLIENT
    // ----------------------------------

    /**
     * Visit method of the notify event.
     * @param message The notify event that arrived to the client.
     * @see NotifyToClient
     */
    void visit(NotifyToClient message);


    // ----------------------------------
    // FINAL EVENT
    // ----------------------------------

    /**
     * Visit method of the end game event.
     * @param message The end game event that arrived to the client.
     * @see EndGameToClient
     */
    void visit(EndGameToClient message);

    // ----------------------------------
    // EVENT FOR THE SINGLE GAME
    // ----------------------------------

    /**
     * Visit method of the lorenzo action event.
     * @param lorenzoAction The lorenzo action event event that arrived to the client.
     * @see LorenzoActionToClient
     */
    void visit(LorenzoActionToClient lorenzoAction);

    // ----------------------------------
    // PING EVENT
    // ----------------------------------
    /**
     * Visit method of the ping event.
     * @param ping The ping event that arrived to the client.
     * @see PingToClient
     */
    void visit(PingToClient ping);
}
