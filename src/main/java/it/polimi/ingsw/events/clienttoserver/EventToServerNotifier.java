package it.polimi.ingsw.events.clienttoserver;

import it.polimi.ingsw.model.developmentcard.ProductedMaterials;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

// list of the event that the client could send to the server
/**
 * This interface contains the list of methods that the client could use to send different events to the server.
 *
 * @author Stefano Fossati
 */
public interface EventToServerNotifier {
    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE SERVER
    // -------------------------------------------------------

    /**
     * Sends the choice of the room number choose by the client to the server.
     * @param room The room number choose by the player.
     * @param newRoom Is true if the room is new, so the server has to create the room, is false id the room already exists.
     */
    void sendRoom(int room, boolean newRoom);

    /**
     * Sends the choice of the name by the client to the server.
     * @param playerName The name of the player.
     */
    void sendPlayerName(String playerName);

    /**
     * Sends the number of player, that the client decides to set for this match, to the server.
     * @param num The number of player decided by the first client connected.
     */
    void sendNumPlayer(int num);

    // -------------------------------------------
    // EVENTS FOR THE INITIAL RESOURCES SELECTION
    // -------------------------------------------

    /**
     * Sends the initial deposit state choose by the player to the server.
     * @param newInitialDepositState The initial deposit state choose by the player.
     */
    void sendInitialDepositState(ArrayList<Resource> newInitialDepositState);

    // -------------------------------------------
    // EVENTS FOR THE LEADER CARD INTERACTION
    // -------------------------------------------

    /**
     * Sends the initial leader card that the player decides to discard to the server.
     * @param leaderCard1 The first initial leader card that the player decides to discard.
     * @param leaderCard2 The second initial leader card that the player decides to discard.
     */
    void sendDiscardInitialLeaderCards(int leaderCard1, int leaderCard2);

    /**
     * Sends the actions on the leader cards choose by the player to the server.
     * @param actions The actions on the leader cards choose by the player.
     * @param isFinal Is true if the event is sent at the final stage of the turn, is false if the event is sent at the initial stage of the turn.
     */
    void sendLeaderCardActions(ArrayList<Integer> actions, boolean isFinal);

    // -------------------------------------------
    // EVENTS FOR THE MARKET TURN INTERACTION
    // -------------------------------------------

    /**
     * Sends the line choose of the market choose by the player to the server.
     * @param numLine The number line of the market choose by the player.
     * @param leaderMarketWhiteChange Indicates if the player has active one or more leader cards market white change.
     */
    void sendChooseLine(int numLine, ArrayList<Boolean> leaderMarketWhiteChange);

    /**
     * Sends to the server the new deposit state and the discarded resources from the market that the player has managed in the market turn.
     * @param newDepositState The new deposit state choose by the player.
     * @param discardResources The number of resources from the market that the player has discarded.
     * @param isAdditional Indicates if the player has some active additional deposit.
     * @param additionalDepositState The new state of the active additional deposit of the player.
     */
    void sendNewDepositState(ArrayList<Resource> newDepositState, int discardResources , boolean isAdditional, ArrayList<Resource> additionalDepositState);

    // ------------------------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN INTERACTION
    // ------------------------------------------------------

    /**
     * Sends to the server the development card that the player wants to buy. The development card is recognised by level and color.
     * @param color The color of the development card choose by the player.
     * @param level The level og the development card choose by the player.
     */
    void sendSelectedDevelopmentCard(int color, int level);

    /**
     * Sends to the server the space in which the player decides to put the development bought.
     * @param space The space in which the player decides to put the development bought.
     */
    void sendSelectedDevelopmentCardSpace(int space);

    // ------------------------------------------------------
    // EVENTS FOR THE USE DEVELOPMENT CARD
    // ------------------------------------------------------

    /**
     * Sends to the server all the production that the player decided to activate.
     * @param useBaseProduction Is true if the player wants to use the base production power, else is false
     * @param resourceRequested1 The first material choose by the player to use for the base production power.
     * @param resourceRequested2 The second material choose by the player to use for the base production power.
     * @param resourceGranted The resource that the player wants to generate from the base production power.
     * @param useLeaders The position of the leader card is true if the player wants to use a leader card additional production, else false. All the leader card that are not additional production are set on false in this ArrayList.
     * @param materialLeaders The material chooses by the player to use for the additional production power of the leader cards.
     * @param useDevelop The development card that the player decides to activate.
     */
    void sendSelectedProductionDevelopmentCard(boolean useBaseProduction, Resource resourceRequested1, Resource resourceRequested2,
                                               ProductedMaterials resourceGranted, ArrayList<Boolean> useLeaders,
                                               ArrayList<Resource> materialLeaders, ArrayList<Boolean> useDevelop);

    // ------------------------------------------------------
    // EVENT FOR NEW TURN INTERACTION
    // ------------------------------------------------------

    /**
     * Sends to the server the type of the turn that the player choose. This method is used only for the debug and test.
     * If the player on the CLI types 'turn' the player skip the turn.
     * @param turnType The type of turn choose by the player.
     */
    void sendTurnPlayed(String turnType);


    // ----------------------------------
    // EVENT FOR THE SINGLE GAME
    // ----------------------------------

    /**
     * Sends to the server the replay of the lorenzo action event.
     */
    void sendReplayLorenzoAction();

    // ----------------------------------
    // PING EVENT
    // ----------------------------------

    /**
     * Sends a ping to the server.
     */
    void sendPing();

}
