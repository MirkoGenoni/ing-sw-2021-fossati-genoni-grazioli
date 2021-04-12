package it.polimi.ingsw.Model.Gameboard;

import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCardHandler;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.LeaderCard.LeaderCardHandler;
import it.polimi.ingsw.Model.Resource.Resource;
import it.polimi.ingsw.Model.Resource.ResourceHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Gameboard {
    ResourceHandler resourceHandler;
    LeaderCardHandler leaderCardHandler;
    DevelopmentCardHandler developmentCardHandler;
    ArrayList<Integer> popeFavorTiles;

    public Gameboard(ArrayList<LeaderCard> leaderCardGiven) throws LeaderCardException {
        this.resourceHandler = new ResourceHandler();
        this.leaderCardHandler = new LeaderCardHandler(leaderCardGiven);
        this.developmentCardHandler = new DevelopmentCardHandler();
        this.popeFavorTiles = new ArrayList<>();

        for (int i = 2; i < 5; i++) {
            popeFavorTiles.add(i);
        }
    }

    public ResourceHandler getResourceHandler() {
        return resourceHandler;
    }

    public LeaderCardHandler getLeaderCardHandler() {
        return leaderCardHandler;
    }

    public DevelopmentCardHandler getDevelopmentCardHandler() {
        return developmentCardHandler;
    }

    public void removePopeFavorTiles(int section) {
        popeFavorTiles.set(section, 0);
    }

    public ArrayList<Integer> getPopeFavorTilesState() {
        return new ArrayList<>(this.popeFavorTiles);
    }

    public void baseProductionPower(Resource resource1, Resource resource2, Resource selected) throws ResourceException {

        Map<Resource, Integer> toTake = new HashMap<>();

        if (!resource1.equals(resource2)) {
            toTake.put(resource1, 1);
            toTake.put(resource2, 1);
        } else {
            toTake.put(resource1, 2);
        }

        if (resourceHandler.checkMaterials(toTake)) {
            resourceHandler.checkMaterials(toTake);
            resourceHandler.takeMaterials(toTake);
            Map<Resource, Integer> give = new HashMap<>();
            give.put(selected, 1);
            resourceHandler.addMaterialStrongbox(give);
        }

    }
}