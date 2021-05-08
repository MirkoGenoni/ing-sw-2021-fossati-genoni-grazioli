package it.polimi.ingsw.Controller.Turns;

import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.DevelopmentCard.CardColor;
import it.polimi.ingsw.Model.Exceptions.DevelopmentCardException;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Gameboard.Gameboard;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Resource.Resource;
import it.polimi.ingsw.Server.ConnectionToClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderCardTurn {
    private final ControllerToModel controllerToModel;

    public LeaderCardTurn(ControllerToModel controllerToModel) {
        this.controllerToModel = controllerToModel;
    }

    public void leaderTurns(String playerName, ArrayList<Integer> positions, int currentPlayerIndex) {
        Player[] players = controllerToModel.getPlayers();
        Game game = controllerToModel.getGame();
        ArrayList<ConnectionToClient> connectionsToClient = controllerToModel.getConnectionsToClient();
        for(int i=positions.size()-1; i>=0; i--){
            switch (positions.get(i)){
                case 2:
                    try {
                        players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().discardLeaderCard(i);
                        if(game.getPlayersFaithTrack().forwardPos(currentPlayerIndex)){
                            controllerToModel.controlPlayerPath(currentPlayerIndex);
                        }
                    } catch (LeaderCardException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {

                        LeaderCard LeaderToActivate = players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable().get(i);
                        ArrayList<String> requirements;
                        Gameboard actualPlayerBoard = players[currentPlayerIndex].getPlayerBoard();


                        switch (LeaderToActivate.getSpecialAbility().getEffect()) {
                            case "additionalProduction":
                                System.out.println("additionalProduction");
                                requirements = LeaderToActivate.getSpecialAbility().getRequirements();
                                CardColor color = CardColor.valueOf(requirements.get(0));
                                ArrayList<CardColor> colorRequired = new ArrayList<>();
                                ArrayList<Integer> level = new ArrayList<>();
                                colorRequired.add(color); //add color in ArrayList
                                level.add(2); //deve essere di livello 2 (forzatura)
                                if (actualPlayerBoard.getDevelopmentCardHandler().checkDevelopmentCard(colorRequired, level)) {
                                    actualPlayerBoard.getLeaderCardHandler().activateLeaderCard(i);
                                    System.out.println("Activated!");
                                    connectionsToClient.get(currentPlayerIndex).sendNotify("Activate the "+ i + " leader");
                                } else {
                                    connectionsToClient.get(currentPlayerIndex).sendNotify("Cannot Activate the "+ i + " leader");
                                    System.out.println("Cannot Activate the selected leader");
                                }
                                break;
                            case "biggerDeposit":
                                System.out.println("biggerDeposit");
                                requirements = LeaderToActivate.getSpecialAbility().getRequirements();
                                Resource resource = Resource.valueOf(requirements.get(0));
                                Map<Resource, Integer> requires = new HashMap<>();
                                requires.put(resource, 5); // PER FORZA 5 (FORZATURA!!)
                                if (actualPlayerBoard.getResourceHandler().checkMaterials(requires)) {
                                    actualPlayerBoard.getLeaderCardHandler().activateLeaderCard(i);
                                    try {
                                        actualPlayerBoard.getResourceHandler().addAdditionalDeposit(LeaderToActivate.getSpecialAbility().getMaterialType());
                                    } catch (ResourceException e) {
                                        System.out.println(e.getMessage());
                                        e.printStackTrace();
                                    }
                                    System.out.println("Activated!");
                                    connectionsToClient.get(currentPlayerIndex).sendNotify("Activate the "+ i + " leader");
                                } else {
                                    connectionsToClient.get(currentPlayerIndex).sendNotify("Cannot Activate the "+ i + " leader");
                                }
                                break;
                            case "costLess":
                                System.out.println("costLess");
                                requirements = LeaderToActivate.getSpecialAbility().getRequirements();
                                ArrayList<CardColor> colorsRequired = new ArrayList<>();
                                for (String s : requirements) {
                                    colorsRequired.add(CardColor.valueOf(s));
                                }
                                if (actualPlayerBoard.getDevelopmentCardHandler().checkCountDevelopmentCard(colorsRequired.get(0), 1) &&
                                        actualPlayerBoard.getDevelopmentCardHandler().checkCountDevelopmentCard(colorsRequired.get(1), 1)) {
                                    actualPlayerBoard.getLeaderCardHandler().activateLeaderCard(i);
                                    System.out.println("Card Activated!");
                                    connectionsToClient.get(currentPlayerIndex).sendNotify("Activate the "+ i + " leader");
                                } else {
                                    connectionsToClient.get(currentPlayerIndex).sendNotify("Cannot Activate the "+ i + " leader");
                                }
                                break;
                            case "marketWhiteChange":
                                System.out.println("marketWhiteChange");
                                requirements = LeaderToActivate.getSpecialAbility().getRequirements();
                                colorsRequired = new ArrayList<>();
                                colorsRequired.add(CardColor.valueOf(requirements.get(0))); //TWICE
                                colorsRequired.add(CardColor.valueOf(requirements.get(2))); //ONCE
                                if (actualPlayerBoard.getDevelopmentCardHandler().checkCountDevelopmentCard(colorsRequired.get(0), 2) &&
                                        actualPlayerBoard.getDevelopmentCardHandler().checkCountDevelopmentCard(colorsRequired.get(1), 1)) {
                                    actualPlayerBoard.getLeaderCardHandler().activateLeaderCard(i);
                                    System.out.println("Card Activated!");
                                    connectionsToClient.get(currentPlayerIndex).sendNotify("Activate the "+ i + " leader");
                                } else {
                                    connectionsToClient.get(currentPlayerIndex).sendNotify("Cannot Activate the "+ i + " leader");
                                }
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


}
