package it.polimi.ingsw.Events.ServerToClient.SupportClass;

import java.io.Serializable;
import java.util.ArrayList;

public class LeaderCardToClient implements Serializable {
    private final String nameCard;
    private final ArrayList<String> requirement;
    private final int victoryPoint;
    private final String effect;
    private final String resourceType;

    public LeaderCardToClient(String nameCard, ArrayList<String> requirement, int victoryPoint, String effect, String resourceType) {
        this.nameCard = nameCard;
        this.requirement = requirement;
        this.victoryPoint = victoryPoint;
        this.effect = effect;
        this.resourceType = resourceType;
    }

    public String getNameCard() {
        return nameCard;
    }

    public ArrayList<String> getRequirement() {
        return requirement;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }

    public String getEffect() {
        return effect;
    }

    public String getResourceType() {
        return resourceType;
    }
}
