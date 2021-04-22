package it.polimi.ingsw.Events.ServerToClient;

import java.util.ArrayList;

public class SendLeaderCardToClient extends EventToClient{
    private final ArrayList<String> requirement;
    private final int victoryPoint;
    private final String effect;
    private final String resourceType;

    public SendLeaderCardToClient(ArrayList<String> requirement, int victoryPoint, String effect, String resourceType) {
        this.requirement = requirement;
        this.victoryPoint = victoryPoint;
        this.effect = effect;
        this.resourceType = resourceType;
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

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
