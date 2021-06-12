package it.polimi.ingsw.Controller.Turns;

import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.DevelopmentCard.CardColor;
import it.polimi.ingsw.Model.Exceptions.DevelopmentCardException;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Gameboard.Gameboard;
import it.polimi.ingsw.Model.LeaderCard.*;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderCardTurn {
    private final ControllerToModel controllerToModel;

    public LeaderCardTurn(ControllerToModel controllerToModel) {
        this.controllerToModel = controllerToModel;
    }

    public void leaderTurns(String playerName, ArrayList<Integer> positions) {
        int currentPlayerIndex = controllerToModel.getCurrentPlayerIndex();
        Player activePlayer = controllerToModel.getActivePlayer();
        Game game = controllerToModel.getGame();
        for(int i=positions.size()-1; i>=0; i--){
            switch (positions.get(i)){
                case 2:
                    try {
                        activePlayer.getPlayerBoard().getLeaderCardHandler().discardLeaderCard(i);
                        if(game.getPlayersFaithTrack().forwardPos(currentPlayerIndex)){
                            controllerToModel.controlPlayerPath(currentPlayerIndex, controllerToModel.getGame().getPlayersFaithTrack().getSection(currentPlayerIndex));
                        }
                    } catch (LeaderCardException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        LeaderCard leaderToActivate = activePlayer.getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable().get(i);

                        switch (leaderToActivate.getSpecialAbility().getEffect()) {
                            case "additionalProduction":
                                additionalProductionLeaderCard(leaderToActivate, i);
                                break;
                            case "biggerDeposit":
                                biggerDepositLeaderCard(leaderToActivate, i);
                                break;
                            case "costLess":
                                costlessLeaderCard(leaderToActivate, i);
                                break;
                            case "marketWhiteChange":
                                marketWhiteChangeLeaderCard(leaderToActivate, i);
                                break;
                        }
                    }
                    catch (LeaderCardException | DevelopmentCardException e){
                        System.out.println("eccezione tirata" + e.getMessage());
                        e.printStackTrace();
                    }
            }
        }

        controllerToModel.turnToView();
    }

    private void additionalProductionLeaderCard(LeaderCard leaderToActivate, int i) throws DevelopmentCardException, LeaderCardException {
        Gameboard actualPlayerBoard = controllerToModel.getActivePlayer().getPlayerBoard();
        //cast
        AdditionalProduction currentLeaderAbility = (AdditionalProduction) leaderToActivate.getSpecialAbility();
        System.out.println("additionalProduction");

        CardColor color = CardColor.valueOf(currentLeaderAbility.getCardRequired());
        ArrayList<CardColor> colorRequired = new ArrayList<>();
        ArrayList<Integer> level = new ArrayList<>();
        colorRequired.add(color); //add color in ArrayList
        Integer levelRequired = currentLeaderAbility.getLevelRequired();
        level.add(levelRequired); //add level in Arraylist
        if (actualPlayerBoard.getDevelopmentCardHandler().checkDevelopmentCard(colorRequired, level)) {
            actualPlayerBoard.getLeaderCardHandler().activateLeaderCard(i);
            System.out.println("Activated!");
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Activate the "+ i + " leader");
            controllerToModel.getConnections().get(controllerToModel.getActivePlayer().getName()).sendNotify("Activate the "+ i + " leader");
        } else {
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Cannot Activate the "+ i + " leader");
            controllerToModel.getConnections().get(controllerToModel.getActivePlayer().getName()).sendNotify("Cannot Activate the "+ i + " leader");
            System.out.println("Cannot Activate the selected leader");
        }
    }

    private void biggerDepositLeaderCard(LeaderCard leaderToActivate, int i) throws LeaderCardException {
        Gameboard actualPlayerBoard = controllerToModel.getActivePlayer().getPlayerBoard();
        //cast
        BiggerDeposit currentLeaderAbility = (BiggerDeposit) leaderToActivate.getSpecialAbility();
        System.out.println("biggerDeposit");

        Resource resource = Resource.valueOf(currentLeaderAbility.getMaterialRequired());
        Integer resourceQuantity = currentLeaderAbility.getQuantityRequired();
        Map<Resource, Integer> requires = new HashMap<>();
        requires.put(resource, resourceQuantity);
        if (actualPlayerBoard.getResourceHandler().checkMaterials(requires)) {
            actualPlayerBoard.getLeaderCardHandler().activateLeaderCard(i);
            try {
                actualPlayerBoard.getResourceHandler().addAdditionalDeposit(currentLeaderAbility.getMaterialType());
            } catch (ResourceException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            System.out.println("Activated!");
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Activate the "+ i + " leader");
            controllerToModel.getConnections().get(controllerToModel.getActivePlayer().getName()).sendNotify("Activate the "+ i + " leader");
        } else {
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Cannot Activate the "+ i + " leader");
            controllerToModel.getConnections().get(controllerToModel.getActivePlayer().getName()).sendNotify("Cannot Activate the "+ i + " leader");
        }
    }

    private void costlessLeaderCard(LeaderCard leaderToActivate, int i) throws LeaderCardException {
        Gameboard actualPlayerBoard = controllerToModel.getActivePlayer().getPlayerBoard();
        //cast
        CostLess currentLeaderAbility = (CostLess) leaderToActivate.getSpecialAbility();
        System.out.println("costLess");

        ArrayList<CardColor> colorsRequired = new ArrayList<>();

        colorsRequired.add(CardColor.valueOf(currentLeaderAbility.getCardRequired1()));
        int quantity1 = currentLeaderAbility.getQuantityRequired1();
        colorsRequired.add(CardColor.valueOf(currentLeaderAbility.getCardRequired2()));
        int quantity2 = currentLeaderAbility.getQuantityRequired2();

        if (actualPlayerBoard.getDevelopmentCardHandler().checkCountDevelopmentCard(colorsRequired.get(0), quantity1) &&
                actualPlayerBoard.getDevelopmentCardHandler().checkCountDevelopmentCard(colorsRequired.get(1), quantity2)) {
            actualPlayerBoard.getLeaderCardHandler().activateLeaderCard(i);
            System.out.println("Card Activated!");
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Activate the "+ i + " leader");
            controllerToModel.getConnections().get(controllerToModel.getActivePlayer().getName()).sendNotify("Activate the "+ i + " leader");
        } else {
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Cannot Activate the "+ i + " leader");
            controllerToModel.getConnections().get(controllerToModel.getActivePlayer().getName()).sendNotify("Cannot Activate the "+ i + " leader");
        }
    }

    private void marketWhiteChangeLeaderCard(LeaderCard leaderToActivate, int i) throws LeaderCardException {
        ArrayList<String> requirements;
        Gameboard actualPlayerBoard = controllerToModel.getActivePlayer().getPlayerBoard();
        //cast
        MarketWhiteChange currentLeaderAbility = (MarketWhiteChange) leaderToActivate.getSpecialAbility();
        System.out.println("marketWhiteChange");


        CardColor color1 = CardColor.valueOf(currentLeaderAbility.getCardRequired1()); //TWICE
        CardColor color2 = CardColor.valueOf(currentLeaderAbility.getCardRequired2()); //ONCE
        int quantity1 = currentLeaderAbility.getCardQuantityRequired1();
        int quantity2 = currentLeaderAbility.getCardQuantityRequired2();

        if (actualPlayerBoard.getDevelopmentCardHandler().checkCountDevelopmentCard(color1, quantity1) &&
                actualPlayerBoard.getDevelopmentCardHandler().checkCountDevelopmentCard(color2, quantity2)) {
            actualPlayerBoard.getLeaderCardHandler().activateLeaderCard(i);
            System.out.println("Card Activated!");
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Activate the "+ i + " leader");
            controllerToModel.getConnections().get(controllerToModel.getActivePlayer().getName()).sendNotify("Activate the "+ i + " leader");
        } else {
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Cannot Activate the "+ i + " leader");
            controllerToModel.getConnections().get(controllerToModel.getActivePlayer().getName()).sendNotify("Cannot Activate the "+ i + " leader");
        }
    }
}
