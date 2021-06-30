package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.developmentcard.DevelopmentCardHandler;
import it.polimi.ingsw.model.exceptions.LeaderCardException;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardHandler;
import it.polimi.ingsw.model.resource.ResourceHandler;

import java.util.ArrayList;

public class Gameboard {
    private ResourceHandler resourceHandler;
    private LeaderCardHandler leaderCardHandler;
    private DevelopmentCardHandler developmentCardHandler;
    private ArrayList<Integer> popeFavorTiles;

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
        popeFavorTiles.set(section - 1, 0);  // non può entrare delle section 0: le section sono 1 2 3;
    }

    public ArrayList<Integer> getPopeFavorTilesState() {
        return new ArrayList<>(this.popeFavorTiles);
    }
}