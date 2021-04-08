package it.polimi.ingsw.Model.DevelopmentCard;

import java.util.HashMap;
import java.util.Map;

/**
 * DevelopmentCard is a class who represent a DevelopmentCard
 * @author Davide
 */

public class DevelopmentCard {
    private CardColor color;
    private final int level;
    private final Map<String, Integer> cost;
    private final int victoryPoint;
    private final Map<String, Integer> materialRequired;
    private final Map<String, Integer> productionResult;

    /**
     * Constructor of the class
     *
     * @param color specify the color of the developmentCard
     * @param level specify the level (min 1, max 3) of the developmentCard
     * @param cost specify the cost of the card a player have to pay to play with this specific card. The cost is given by a 5 elements HashMap
     * @param victoryPoint specify the victory points this card grants at the end of the game
     * @param materialRequired specify with an HashMap what the card need to start the develop
     * @param productionResult specify with an HashMap what the card grant at the end of the develop
     */
    public DevelopmentCard(CardColor color, int level, Map<String, Integer> cost, int victoryPoint,
                           Map<String, Integer> materialRequired, Map<String, Integer> productionResult) {
        this.color = color;
        this.level = level;
        this.cost = cost;
        this.victoryPoint = victoryPoint;
        this.materialRequired = materialRequired;
        this.productionResult = productionResult;
    }

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
    public Map<String, Integer> getCost() {

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
    public Map<String, Integer> getMaterialRequired() {

        return new HashMap<>(materialRequired);
    }

    /**
     *
     * @return with an HashMap the number of resources the card grant at the end of the develop (number of: Coins, Servants, Shields, Stones, FaithPoints)
     */
    public Map<String, Integer> getProductionResult() {

        return new HashMap<>(productionResult);
    }
}
