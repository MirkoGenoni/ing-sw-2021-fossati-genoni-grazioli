package it.polimi.ingsw.events.serverToClient.supportClass;

import it.polimi.ingsw.model.developmentCard.ProductedMaterials;
import it.polimi.ingsw.model.resource.Resource;

import java.io.Serializable;
import java.util.Map;


/**
 * This class contains the information of the development card to send to the client.
 * This object could be only read from the client.
 * @see it.polimi.ingsw.model.developmentCard.DevelopmentCard
 * @see Serializable
 *
 * @author Stefano Fossati
 */
public class DevelopmentCardToClient implements Serializable {
    private final String cardID;
    private final String color;
    private final int level;
    private final Map<Resource, Integer> cost;
    private final int victoryPoint;
    private final Map<Resource, Integer> materialRequired;
    private final Map<ProductedMaterials, Integer> productionResult;

    /**
     * Constructs the development card to send to the client with its information.
     * @param cardID The ID of the card.
     * @param color The color of the development card.
     * @param level The level of the development card
     * @param cost The cost to buy this development card.
     * @param victoryPoint The victory points that this card give at the end of the match, to the player that buy this development card.
     * @param materialRequired The material required to activate the production  of this  development card.
     * @param productionResult The material give by the activation of the development card production.
     */
    public DevelopmentCardToClient(String cardID, String color, int level, Map<Resource, Integer> cost, int victoryPoint,
                                   Map<Resource, Integer> materialRequired, Map<ProductedMaterials, Integer> productionResult) {
        this.cardID = cardID;
        this.color = color;
        this.level = level;
        this.cost = cost;
        this.victoryPoint = victoryPoint;
        this.materialRequired = materialRequired;
        this.productionResult = productionResult;
    }

    /**
     * Getter that returns the Id of the development card.
     * @return the Id of the development card.
     */
    public String getCardID() {
        return cardID;
    }

    /**
     * Getter that returns the color of the development card.
     * @return the color of the development card.
     */
    public String getColor() {
        return color;
    }

    /**
     * Getter that returns the leve of the development card.
     * @return the leve of the development card.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Getter that returns the cost to buy the development card.
     * @return the cost to buy the development card.
     */
    public Map<Resource, Integer> getCost() {
        return cost;
    }

    /**
     * Getter that returns the victory points of the development card.
     * @return the victory points of the development card.
     */
    public int getVictoryPoint() {
        return victoryPoint;
    }

    /**
     * Getter that returns the materials required to activate the production of the development card.
     * @return the material required to activate the production of the development card.
     */
    public Map<Resource, Integer> getMaterialRequired() {
        return materialRequired;
    }

    /**
     * Getter that returns the material produced by the activation of the development card production.
     * @return material produced by the activation of the development card production.
     */
    public Map<ProductedMaterials, Integer> getProductionResult() {
        return productionResult;
    }
}
