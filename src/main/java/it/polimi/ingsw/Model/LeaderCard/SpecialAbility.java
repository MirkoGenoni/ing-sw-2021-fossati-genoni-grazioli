package it.polimi.ingsw.Model.LeaderCard;

/**
 * Interface of the special ability of the leader cards
 *
 * @author Stefano Fossati
 */

public interface SpecialAbility {

    /**
     * Getter that returns the effect of the special ability of the leader card
     * @return The effect of the leader card
     */

    public String getEffect();

    /**
     * Getter that returns victory points of the leader card
     * @return Victory points of the leader card
     */

    public int getVictoryPoint();
}
