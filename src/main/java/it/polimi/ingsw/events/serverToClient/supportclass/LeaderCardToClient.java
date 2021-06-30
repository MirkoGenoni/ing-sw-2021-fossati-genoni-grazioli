package it.polimi.ingsw.events.serverToClient.supportclass;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains the information of the leader card to send to the client.
 * This object could be only read from the client.
 * @see it.polimi.ingsw.model.leadercard.LeaderCard
 * @see Serializable
 *
 * @author Stefano Fossati
 */
public class LeaderCardToClient implements Serializable {
    private final String nameCard;
    private final ArrayList<String> requirement;
    private final int victoryPoint;
    private final String effect;
    private final String resourceType;

    /**
     * Constructs the leader card to send to the client with its information.
     * @param nameCard The ID of the card.
     * @param requirement The requirements for the activation of the leader card.
     * @param victoryPoint The victory points that this card give at the end of the match, to the player that activate the leader card.
     * @param effect The effect of this leader card.
     * @param resourceType the resource type that the effect of this card use. This type depend from the effect.
     */
    public LeaderCardToClient(String nameCard, ArrayList<String> requirement, int victoryPoint, String effect, String resourceType) {
        this.nameCard = nameCard;
        this.requirement = requirement;
        this.victoryPoint = victoryPoint;
        this.effect = effect;
        this.resourceType = resourceType;
    }

    /**
     * Getter that returns the Id of the leader card.
     * @return the Id of the leader card.
     */
    public String getNameCard() {
        return nameCard;
    }

    /**
     * Getter that returns the requirements for the activation of the leader card.
     * @return the requirements for the activation of the leader card.
     */
    public ArrayList<String> getRequirement() {
        return requirement;
    }

    /**
     * Getter that returns the victory points of the leader card.
     * @return the victory points of the leader card.
     */
    public int getVictoryPoint() {
        return victoryPoint;
    }

    /**
     * Getter that returns the effect of the leader card.
     * @return the effect of the leader card.
     */
    public String getEffect() {
        return effect;
    }

    /**
     * Getter that returns the resource type that the effect of this card use.
     * @return the resource type that the effect of this card use.
     */
    public String getResourceType() {
        return resourceType;
    }
}
