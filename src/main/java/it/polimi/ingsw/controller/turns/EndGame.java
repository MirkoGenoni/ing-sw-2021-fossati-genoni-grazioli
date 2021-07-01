package it.polimi.ingsw.controller.turns;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.LeaderCardException;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class to manage and control the end of the game
 * @author davide grazioli
 */
public class EndGame {
    private final Controller controller;

    /**
     * constructor of the class
     * @param controller controller to model that manages the game for players
     */
    public EndGame(Controller controller) {
        this.controller = controller;
    }

    /**
     * method that check if the game is ended
     * @return true if are present the condition to end the game
     */
    public boolean endGameCheck(){
        if(checkFaithTrackPosition() || checkDevelopmentCardNumber()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Method that calculate the acquired points a specified player
     * @param playerIndex The index of the player to calculate the total of points
     * @return the amount of acquired points
     */
    public int calculatePoints(int playerIndex){
        Player player = controller.getPlayers()[playerIndex];
        FaithTrack faithTrack = controller.getGame().getPlayersFaithTrack();

        int totalVictoryPoints = 0;
        // victory point of development cards
        for(DevelopmentCard card : player.getPlayerBoard().getDevelopmentCardHandler().getDevelopmentCardColl()){
            totalVictoryPoints = totalVictoryPoints + card.getVictoryPoint();
        }
        // victory points of leader cards active
        try{
            for(LeaderCard card : player.getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive()){
                totalVictoryPoints = totalVictoryPoints + card.getSpecialAbility().getVictoryPoints();
            }
        } catch (LeaderCardException e) {
            System.out.println(e.getMessage());
        }
        // victory point of faith track position
        totalVictoryPoints = totalVictoryPoints + faithTrack.getTrack()[faithTrack.getPosition(playerIndex)].getPointPosition();
        // victory points of pope favor pass
        for(int i=0; i < player.getPlayerBoard().getPopeFavorTilesState().size(); i++){
            totalVictoryPoints = totalVictoryPoints + player.getPlayerBoard().getPopeFavorTilesState().get(i);
        }
        // victory points of resources
        totalVictoryPoints = totalVictoryPoints + calculateResources(playerIndex)/5;
        return totalVictoryPoints;
    }

    /**
     * Private method that check if a player reach the last position of the faith track
     * @return true if a player is on the twenty-fourth box on the faith track
     */
    private boolean checkFaithTrackPosition(){
        boolean tmp = false;
        for(int i = 0; i < controller.getPlayers().length; i++){
            if(controller.getGame().getPlayersFaithTrack().getPosition(i) == 24){
                tmp = true;
            }
        }
        return tmp;
    }

    /**
     * Private method that check if a player has bought the seventh development card
     * @return true if a player has seven development cards
     */
    private boolean checkDevelopmentCardNumber(){
        boolean tmp = false;
        for(int i = 0; i < controller.getPlayers().length; i++){
            if(controller.getPlayers()[i].getPlayerBoard().getDevelopmentCardHandler().getDevelopmentCardColl().size()==7){
                tmp = true;
            }
        }
        return tmp;
    }

    /**
     * Private method that calculate the amount of point from each resource a player has
     * @param playerIndex The index of the player to calculate the points from the resources
     * @return the amount of the point collected from the resources for a player  
     */
    private int calculateResources(int playerIndex){
        ArrayList<Resource> depositResources = controller.getPlayers()[playerIndex].getPlayerBoard().getResourceHandler().getDepositState();
        ArrayList<Resource> additionalDepositResources = controller.getPlayers()[playerIndex].getPlayerBoard().getResourceHandler().getAdditionalDeposit();
        Map<Resource, Integer> strongboxResources = controller.getPlayers()[playerIndex].getPlayerBoard().getResourceHandler().getStrongboxState();
        int resources = 0;
        // resources from deposit
        for(int i = 0; i< depositResources.size(); i++){
            if(depositResources.get(i)!=null){
                resources++;
            }
        }
        // resources from additional deposit
        for(int i=0; i< additionalDepositResources.size(); i++){
            if(additionalDepositResources.get(i)!=null){
                resources++;
            }
        }
        for(Resource r : Resource.values()){
            resources = resources + strongboxResources.get(r);
        }
        return resources;
    }


}
