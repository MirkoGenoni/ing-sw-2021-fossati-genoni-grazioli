package it.polimi.ingsw.Model.DevelopmentCard;

import it.polimi.ingsw.Model.Resource.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * DevelopmentCard is a class who represent a DevelopmentCard
 * @author Davide
 */

public class DevelopmentCard {
    private CardColor color;
    private int level;
    private Map<Resource, Integer> cost;
    private int victoryPoint;
    private Map<Resource, Integer> materialRequired;
    private Map<ProductedMaterials, Integer> productionResult;


    /**
     *
     * @return the color of the card
     */
    public CardColor getColor() {
        return color;
    }

    /**
     *
     * @return the level of the card
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return the cost of the card: HashMap with their cost, with no FaithPoint
     */
    public Map<Resource, Integer> getCost() {

        return new HashMap<>(cost);
    }

    /**
     *
     * @return the number of victoryPoint this card grant
     */
    public int getVictoryPoint() {
        return victoryPoint;
    }

    /**
     *
     * @return with an HashMap the number of each material needed to develop (number of: Coins, Servants, Shields, Stones, FaithPoints)
     */
    public Map<Resource, Integer> getMaterialRequired() {

        return new HashMap<>(materialRequired);
    }

    /**
     *
     * @return with an HashMap the number of resources the card grant at the end of the develop (number of: Coins, Servants, Shields, Stones, FaithPoints)
     */
    public Map<ProductedMaterials, Integer> getProductionResult() {

        return new HashMap<>(productionResult);
    }
}
