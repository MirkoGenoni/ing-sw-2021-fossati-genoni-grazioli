package it.polimi.ingsw.Controller.Turns;

import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.Map;

public class EndGame {
    private final ControllerToModel controllerToModel;

    public EndGame(ControllerToModel controllerToModel) {
        this.controllerToModel = controllerToModel;
    }

    public boolean endGameNotify(){
        if(checkFaithTrackPosition() || checkDevelopmentCardNumber()){
            return true;
        }
        else{
            return false;
        }
    }

    public int calculatePoints(int playerIndex){
        Player player = controllerToModel.getPlayers()[playerIndex];
        FaithTrack faithTrack = controllerToModel.getGame().getPlayersFaithTrack();

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

    private boolean checkFaithTrackPosition(){
        boolean tmp = false;
        for(int i=0; i < controllerToModel.getPlayers().length; i++){
            if(controllerToModel.getGame().getPlayersFaithTrack().getPosition(i) == 24){
                tmp = true;
            }
        }
        return tmp;
    }

    private boolean checkDevelopmentCardNumber(){
        boolean tmp = false;
        for(int i=0; i < controllerToModel.getPlayers().length; i++){
            if(controllerToModel.getPlayers()[i].getPlayerBoard().getDevelopmentCardHandler().getDevelopmentCardColl().size()==7){
                tmp = true;
            }
        }
        return tmp;
    }

    private int calculateResources(int playerIndex){
        ArrayList<Resource> depositResources = controllerToModel.getPlayers()[playerIndex].getPlayerBoard().getResourceHandler().getDepositState();
        ArrayList<Resource> additionalDepositResources = controllerToModel.getPlayers()[playerIndex].getPlayerBoard().getResourceHandler().getAdditionalDeposit();
        Map<Resource, Integer> strongboxResources = controllerToModel.getPlayers()[playerIndex].getPlayerBoard().getResourceHandler().getStrongboxState();
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
