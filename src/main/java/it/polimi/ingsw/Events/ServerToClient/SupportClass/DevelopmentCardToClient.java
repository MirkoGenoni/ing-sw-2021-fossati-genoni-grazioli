package it.polimi.ingsw.Events.ServerToClient.SupportClass;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Resource.Resource;

import java.io.Serializable;
import java.util.Map;

public class DevelopmentCardToClient implements Serializable {

    private final String color;
    private final int level;
    private final Map<Resource, Integer> cost;
    private final int victoryPoint;
    private final Map<Resource, Integer> materialRequired;
    private final Map<ProductedMaterials, Integer> productionResult;

    public DevelopmentCardToClient(String color, int level, Map<Resource, Integer> cost,
                                   int victoryPoint, Map<Resource, Integer> materialRequired, Map<ProductedMaterials, Integer> productionResult) {
        this.color = color;
        this.level = level;
        this.cost = cost;
        this.victoryPoint = victoryPoint;
        this.materialRequired = materialRequired;
        this.productionResult = productionResult;
    }

    public String getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public Map<Resource, Integer> getCost() {
        return cost;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }

    public Map<Resource, Integer> getMaterialRequired() {
        return materialRequired;
    }

    public Map<ProductedMaterials, Integer> getProductionResult() {
        return productionResult;
    }
}
