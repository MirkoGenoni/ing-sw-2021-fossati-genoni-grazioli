package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * Interface of the special ability of the leader cards.
 *
 * @author Stefano Fossati
 */

public interface SpecialAbility {

    /**
     * Getter that returns the effect of the special ability of the leader card.
     * @return The effect of the leader card.
     */

    String getEffect();

    /**
     * Getter that returns victory points of the leader card.
     * @return Victory points of the leader card.
     */

    int getVictoryPoints();

    /**
     * Getter that returns the requirements for the activation of the leader card.
     * @return The requirements need to activate the leader card.
     */
    ArrayList<String> getRequirements();

    /**
     * Getter that returns the material type of the special ability of the leader card.
     * @return The type of material of the special ability of the card.
     */

    Resource getMaterialType();
}
