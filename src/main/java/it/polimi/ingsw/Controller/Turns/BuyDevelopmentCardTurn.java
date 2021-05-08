package it.polimi.ingsw.Controller.Turns;

import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.Exceptions.DevelopmentCardException;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
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

    public void developmentCardTurn(int currentPlayerIndex){
        System.out.println("mando array delle carte disponibili");

        //connectionsToClient.get(currentPlayerIndex).sendDevelopmentCards(availableToSend);

    }

    public void buyDevelopmentCard(int color, int level, int currentPlayerIndex){
        DevelopmentCard buyDevelopmentCard = controllerToModel.getGame().getDevelopmentCardsAvailable()[color][level];
        System.out.println(buyDevelopmentCard.getColor() + buyDevelopmentCard.getCost().toString());
        this.color = color;
        this.level = level; // questo livello è la posizione delle development card nel doppio array del game

        System.out.println("faccio il check delle risorse e degli spazi development card");
        // qui ci sarebbe da rimettere ccomunque il controllo sulle leadercard cost less
        if (controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getResourceHandler().checkMaterials(buyDevelopmentCard.getCost())
                && controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().checkBoughtable(level+1).contains(true)) {
            controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendDevelopmentCardSpace(controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().checkBoughtable(level+1));
        } else {
            controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendReselectedDevelopmentCards("You can't bought the selected card, please select an other card");

        }


    }

    public boolean spaceDevelopmentCard(int space, int currentPlayerIndex){
        DevelopmentCard buyDevelopmentCard = controllerToModel.getGame().getDevelopmentCardsAvailable()[color][level];
        Map<Resource, Integer> requirements = buyDevelopmentCard.getCost();
        ArrayList<LeaderCard> leaderCardActive;

        try{
            leaderCardActive = controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive();
        } catch (LeaderCardException e) {
            leaderCardActive = null;
        }

        if(leaderCardActive!=null){
            for (LeaderCard card : leaderCardActive){          //control if there's an active LeaderCard costless
                if (card.getSpecialAbility().getEffect().equals("costLess")) {
                    Resource discount = card.getSpecialAbility().getMaterialType();
                    if(requirements.containsKey(discount)){
                        requirements.put(discount, requirements.get(discount)-1);
                    }
                }
            }
        }

        try{
            controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getResourceHandler().takeMaterials(requirements);
            controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().setActiveDevelopmentCard(controllerToModel.getGame().buyDevelopmentCard(color, level), space);
        } catch (ResourceException | StartGameException | DevelopmentCardException e) {
            e.printStackTrace();
        }
        controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Card correctly Activated");
        return true;

    }
}