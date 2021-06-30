package it.polimi.ingsw.controller.turns;

import it.polimi.ingsw.controller.ControllerToModel;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.DevelopmentCardException;
import it.polimi.ingsw.model.exceptions.LeaderCardException;
import it.polimi.ingsw.model.exceptions.ResourceException;
import it.polimi.ingsw.model.exceptions.StartGameException;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.gameboard.Gameboard;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class to manage the turn to buy development cards
 */
public class BuyDevelopmentCardTurn {
    private final ControllerToModel controllerToModel;

    // tmp attributes
    private int color;
    private int level;

    /**
     * constructor of the class
     * @param controllerToModel controller to model that manages the game for players
     */
    public BuyDevelopmentCardTurn(ControllerToModel controllerToModel) {
        this.controllerToModel = controllerToModel;
    }

    /**
     * method to check if a player can buy a development card of a specified color and level from the development cards' market
     * @param color color of development card a player want to buy
     * @param level level of development card a player want to buy
     */
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

    /**
     * Method that activate the card taking the necessary resources and put in that in a specified space
     * @param space the space where the player wants to put the development card
     * @return TODO NON SO COSA SIA
     */
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
        //controllerToModel.getConnections().get(activePlayer.getName()).sendNotify("Card correctly Activated");
        return true;

    }

    /**
     * Private method to check if a player could have a discount from buy development cards
     * @param requirements map of required resource to buy the development card
     * @param leaderCardActive Array of the player's active leaders
     */
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
