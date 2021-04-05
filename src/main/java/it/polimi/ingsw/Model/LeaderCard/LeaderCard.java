package it.polimi.ingsw.Model.LeaderCard;

/**
 * This class represents the leader card.
 *
 * @author Stefano Fossati
 */

public class LeaderCard {
    final private String name;
    final private SpecialAbility specialAbility;

    /**
     * Initializes the name and the special ability of the leader card.
     * @param name The name of the leader card.
     * @param ability The special ability of the leader card.
     */

    public LeaderCard(String name,SpecialAbility ability){
        this.name = name;
        this.specialAbility = ability;
    }

    /**
     * Getter that returns the name of the leader card.
     * @return The name of the leader card.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter that returns the special ability of the leader card.
     * @return the special ability of the leader card.
     */
    public SpecialAbility getSpecialAbility() {
        return specialAbility;
    }
}
