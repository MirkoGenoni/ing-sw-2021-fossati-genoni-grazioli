package it.polimi.ingsw.events.servertoclient;

import it.polimi.ingsw.events.servertoclient.supportclass.LeaderCardToClient;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents the event to send to the client for the leader card turn.
 *
 * @author Stefano Fossati
 */
public class SendArrayLeaderCardsToClient extends EventToClient{
    private final ArrayList<LeaderCardToClient> leaderCardArray;
    private final Map<String, Integer> numberOfDevelopmentCards;
    private final boolean initialLeaderCards;
    private final boolean isFinal;

    /**
     * Constructs the event.
     * @param leaderCardArray The leader cards of the player.
     * @param numberOfDevelopmentCards The total number of the development card of the player.
     * @param initialLeaderCards If the event is for the choice of the initial leader cards or not.
     * @param isFinal Is true if the event is sent at the final stage of the turn, is false if the event is sent at the initial stage of the turn.
     */
    public SendArrayLeaderCardsToClient(ArrayList<LeaderCardToClient> leaderCardArray, Map<String, Integer> numberOfDevelopmentCards, boolean initialLeaderCards, boolean isFinal) {
        this.leaderCardArray = leaderCardArray;
        this.numberOfDevelopmentCards = numberOfDevelopmentCards;
        this.initialLeaderCards = initialLeaderCards;
        this.isFinal = isFinal;
    }


    /**
     * Getter of the leader cards of the player.
     * @return The leader cards of the player.
     */
    public ArrayList<LeaderCardToClient> getLeaderCardArray() {
        return leaderCardArray;
    }
    /**
     * Getter of the total number of the development card of the player.
     * @return The total number of the development card of the player.
     */
    public Map<String, Integer> getNumberOfDevelopmentCards() {
        return numberOfDevelopmentCards;
    }
    /**
     * Getter of boolean that indicates if the event is for the choice of the initial leader cards or not.
     * @return The boolean that indicates if the event is for the choice of the initial leader cards or not.
     */
    public boolean isInitialLeaderCards() {
        return initialLeaderCards;
    }

    /**
     * Getter that returns the boolean that indicates if the event is sent from the server at the final stage or the initial stage of the turn.
     * @return The boolean that indicates if the event is sent from the server at the final stage or the initial stage of the turn.
     */
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
