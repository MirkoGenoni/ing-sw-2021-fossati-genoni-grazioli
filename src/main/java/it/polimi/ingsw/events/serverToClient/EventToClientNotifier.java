package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.faithTrack.FaithTrack;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.lorenzo.SoloAction;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Map;


/**
 * This interface contains the list of methods that the server could use to send different events to the client.
 *
 * @author Stefano Fossati
 */
public interface EventToClientNotifier {

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------

    /**
     * Sends the request to choose the room to the client.
     * @param message The message to sent to the client.
     */
    void sendRoomRequestToClient(String message);

    /**
     * Sends the request to choose the own nickname for the match to the client.
     * @param message The message to send to the client. It contains some instructions.
     */
    void sendNamePlayerRequest(String message);

    /**
     * Sends the request to choose the number of player for the match. Only the first player that access to the room could choose the number of player.
     * @param message The message to send to the client.
     */
    void sendNumPlayerRequest(String message);

    // ----------------------------------
    // EVENT FOR THE INITIAL RESOURCES
    // ----------------------------------

    /**
     * Sends to the client/player the request to choose the initial resource of the match set up.
     * @param numResources The number of resources that the player have to choose.
     * @param depositState The initial deposit state of the player.
     */
    void sendInitialResources(int numResources, ArrayList<Resource> depositState);

    // ----------------------------------------
    // EVENTS THAT SEND LEADER CARD INFORMATION
    // ----------------------------------------

    /**
     * Sends to the client/player the information of the leader cards.
     * @param leaderCards The leader cards of the player.
     * @param initialLeaderCards Indicates the phase of the match in which the leader card are send.
     *                          If is true the leader cards are to choose for the set up of the match.
     *                          If is false the leader cards are to manage like in a normal turn.
     * @param currentPlayer The player send his own leader cards to.
     */
    void sendArrayLeaderCards(ArrayList<LeaderCard> leaderCards, boolean initialLeaderCards, Player currentPlayer);

    // -------------------------------------------------------------------
    // EVENT FOR THE NEW TURN, THIS EVENT UPDATE THE CLIENT INFORMATION
    // -------------------------------------------------------------------

    /**
     * Sends all the needed information to the client.
     * @param turnNumber The number of the turn.
     * @param market The structure of the market.
     * @param developmentCards The structure of the development cards on the table with its information.
     * @param players The players with their public information.
     * @param faithTrack The state of the faith track.
     * @param yourTurn Indicates if the next turn is of the player to which is sent this event.
     *                 If is true the next turn is of the client to which is sent this event.
     *                 If is false the next turn is of another client.
     */
    void sendNewTurn(int turnNumber, Market market, DevelopmentCard[][] developmentCards, Player[] players, FaithTrack faithTrack, boolean yourTurn);

    // ----------------------------------
    // EVENT FOR THE MARKET TURN
    // ----------------------------------

    /**
     * Sends the request to reorganize the deposit to the client.
     * @param marketResources The resources take from the market.
     * @param depositState The deposit state of the player that have to reorganize the deposit.
     * @param isAdditional Indicates if there is or there are additional deposits send.
     * @param additionalType The type of the additional deposits send.
     * @param additionalState The state of the additional deposits send.
     */
    void sendReorganizeDeposit(ArrayList<Resource> marketResources, ArrayList<Resource> depositState, boolean isAdditional,
                               ArrayList<Resource> additionalType, ArrayList<Resource> additionalState);

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------

    /**
     * Sends the reselection of the turn to the client.
     * @param message The information for which this event is send.
     */
    void sendTurnReselection(String message);

    /**
     * Sends the request to select the development card space in which the player want to position the development card bought.
     * @param developmentCardSpace The state of the development card space of the player.
     */
    void sendDevelopmentCardSpace(ArrayList<Boolean> developmentCardSpace);

    // ----------------------------------
    // EVENT FOR NOTIFY THE CLIENT
    // ----------------------------------

    /**
     * Sends a notify to the client.
     * @param message The information of the notify.
     */
    void sendNotify(String message);

    // ----------------------------------
    // FINAL EVENT
    // ----------------------------------

    /**
     * Sends the notify that the game ended to the client.
     * @param message The massage to send to the client.
     * @param playersPoint The points of all players.
     * @param faithTrack The faith track of the match.
     * @param lorenzo Indicates if is a single player match.
     * @param lorenzoPosition Lorenzo position if is a single player match.
     * @param market The market of the match.
     */
    void sendEndGame(String message, Map<String, Integer> playersPoint, Player[] players, FaithTrack faithTrack,
                     boolean lorenzo, int lorenzoPosition, DevelopmentCard[][] devCard, Market market);

    // ----------------------------------
    // EVENT FOR THE SINGLE GAME
    // ----------------------------------

    /**
     * Sends the action that Lorenzo done in the single player match to the client.
     * @param lorenzoAction The type of action done by Lorenzo.
     * @param lorenzoPosition The position of lorenzo in the faith track.
     */
    void sendLorenzoTurn(SoloAction lorenzoAction, int lorenzoPosition);

    // ----------------------------------
    // PING EVENT
    // ----------------------------------
    /**
     * Sends a ping to the client.
     */
    void sendPing();

}

