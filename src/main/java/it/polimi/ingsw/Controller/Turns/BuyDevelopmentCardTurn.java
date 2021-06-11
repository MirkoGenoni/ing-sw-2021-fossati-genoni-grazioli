package it.polimi.ingsw.Controller.Turns;

import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.Exceptions.DevelopmentCardException;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Gameboard.Gameboard;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.Map;

public class BuyDevelopmentCardTurn {
    private final ControllerToModel controllerToModel;

    // tmp attributes
    private int color;
    private int level;

    public BuyDevelopmentCardTurn(ControllerToModel controllerToModel) {
        this.controllerToModel = controllerToModel;
    }

    public void buyDevelopmentCard(int color, int level){
        Player activePlayer = controllerToModel.getActivePlayer();
        DevelopmentCard buyDevelopmentCard = controllerToModel.getGame().getDevelopmentCardsAvailable()[color][level];
        Gameboard activeGameBoard = activePlayer.getPlayerBoard();
        System.out.println(buyDevelopmentCard.getColor() + buyDevelopmentCard.getCost().toString());
        Map<Resource, Integer> requirements = buyDevelopmentCard.getCost();
        this.color = color;
        this.level = level; // questo livello è la posizione delle development card nel doppio array del game

        System.out.println("faccio il check delle risorse e degli spazi development card");
        try {
            checkCostlessLeaderCard(requirements, activePlayer.getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive());
        } catch (LeaderCardException e) {
            System.out.println(e.getMessage());
        }

        if (activeGameBoard.getResourceHandler().checkMaterials(requirements)
                && activeGameBoard.getDevelopmentCardHandler().checkBoughtable(level+1).contains(true)) {
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendDevelopmentCardSpace(controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().checkBoughtable(level+1));
            controllerToModel.getConnections().get(activePlayer.getName()).sendDevelopmentCardSpace(activeGameBoard.getDevelopmentCardHandler().checkBoughtable(level+1));
        } else {
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendTurnReselection("You can't bought the selected card, please select an other card");
            controllerToModel.getConnections().get(activePlayer.getName()).sendTurnReselection("You can't bought the selected card, please select an other card");


        }

    }

    public boolean spaceDevelopmentCard(int space){
        Player activePlayer = controllerToModel.getActivePlayer();
        DevelopmentCard buyDevelopmentCard = controllerToModel.getGame().getDevelopmentCardsAvailable()[color][level];
        Gameboard activeGameBoard = activePlayer.getPlayerBoard();
        Map<Resource, Integer> requirements = buyDevelopmentCard.getCost();

        try {
            checkCostlessLeaderCard(requirements, activeGameBoard.getLeaderCardHandler().getLeaderCardsActive());
        } catch (LeaderCardException e) {
            System.out.println(e.getMessage());
        }

        try{
            activeGameBoard.getResourceHandler().takeMaterials(requirements);
            activeGameBoard.getDevelopmentCardHandler().setActiveDevelopmentCard(controllerToModel.getGame().buyDevelopmentCard(color, level), space);
        } catch (ResourceException | StartGameException | DevelopmentCardException e) {
            e.printStackTrace();
        }
        //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Card correctly Activated");
        controllerToModel.getConnections().get(activePlayer.getName()).sendNotify("Card correctly Activated");
        return true;

    }

    private void checkCostlessLeaderCard(Map<Resource, Integer> requirements, ArrayList<LeaderCard> leaderCardActive){
        for (LeaderCard card : leaderCardActive){          //control if there's an active LeaderCard costless
            if (card.getSpecialAbility().getEffect().equals("costLess")) {
                Resource discount = card.getSpecialAbility().getMaterialType();
                if(requirements.containsKey(discount)){
                    requirements.put(discount, requirements.get(discount)-1);
                }
            }
        }
    }
}
