package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.developmentcard.DevelopmentCardHandler;
import it.polimi.ingsw.model.exceptions.LeaderCardException;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardHandler;
import it.polimi.ingsw.model.resource.ResourceHandler;

import java.util.ArrayList;

/**
 * This class manage the Pope favor tiles and represent the gameBoard and its elements through the game
 * @author Genoni Mirko
 */
public class Gameboard {
    private ResourceHandler resourceHandler;
    private LeaderCardHandler leaderCardHandler;
    private DevelopmentCardHandler developmentCardHandler;
    private ArrayList<Integer> popeFavorTiles;

    /**
     * Constructor of the class
     * @param leaderCardGiven the fourth initial leaderCards given to each player
     * @throws LeaderCardException if there is any problem in initializing Leaders
     */
    public Gameboard(ArrayList<LeaderCard> leaderCardGiven) throws LeaderCardException {
        this.resourceHandler = new ResourceHandler();
        this.leaderCardHandler = new LeaderCardHandler(leaderCardGiven);
        this.developmentCardHandler = new DevelopmentCardHandler();
        this.popeFavorTiles = new ArrayList<>();

        for (int i = 2; i < 5; i++) {
            popeFavorTiles.add(i);
        }
    }

    /**
     * getter of the Resource Handler
     * @return Resource handler that manages the player's resources
     */
    public ResourceHandler getResourceHandler() {
        return resourceHandler;
    }

    /**
     * getter of the LeaderCard Handler
     * @return LeaderCard handler that manages the player's LeaderCard
     */
    public LeaderCardHandler getLeaderCardHandler() {
        return leaderCardHandler;
    }

    /**
     * getter of the DevelopmentCard Handler
     * @return DevelopmentCard handler that manages the player's DevelopmentCard
     */
    public DevelopmentCardHandler getDevelopmentCardHandler() {
        return developmentCardHandler;
    }

    /**
     * Remove the player's PopeFavor tile of a specified section
     * @param section the section of the PopeFavor tile to remove
     */
    public void removePopeFavorTiles(int section) {
        popeFavorTiles.set(section - 1, 0);  // non può entrare delle section 0: le section sono 1 2 3;
    }

    /**
     * getter of the available PopeFavor tiles
     * @return an array with the representation of the PopeFavor tiles
     */
    public ArrayList<Integer> getPopeFavorTilesState() {
        return new ArrayList<>(this.popeFavorTiles);
    }
}