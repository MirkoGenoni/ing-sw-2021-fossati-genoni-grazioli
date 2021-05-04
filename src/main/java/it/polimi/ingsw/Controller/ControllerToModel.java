package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Controller.Turns.BuyDevelopmentCardTurn;
import it.polimi.ingsw.Controller.Turns.MarketTurn;
import it.polimi.ingsw.Model.DevelopmentCard.CardColor;
import it.polimi.ingsw.Model.Exceptions.DevelopmentCardException;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.Model.Game.MultiPlayerGame;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.SinglePlayerGame;
import it.polimi.ingsw.Model.Gameboard.Gameboard;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Resource.Resource;
import it.polimi.ingsw.Server.ConnectionToClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
   modifica direttamente il model
 */
public class ControllerToModel {
    private Player[] players;
    private final ArrayList<ConnectionToClient> connectionsToClient;
    private MultiPlayerGame multiGame;
    private SinglePlayerGame singleGame;
    private Game game;
    private Player activePlayer;
    private int turnNumber=1;
    private int currentPlayerIndex;
    private int numPlayer;

    // type of turn
    private MarketTurn marketTurn;
    private BuyDevelopmentCardTurn buyDevelopmentCardTurn;

    public ControllerToModel() {
        this.connectionsToClient = new ArrayList<>();
        numPlayer = 0;
    }

    // -------------------------------------------------------
    // METHODS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------
    public void addConnectionToClient(ConnectionToClient connectionToClient){
        connectionsToClient.add(connectionToClient);
    }

    public int getNumPlayer() {
        return numPlayer;
    }


    public void setNumPlayer(int numPlayer) {
        System.out.println("ho settato il numero di giocatori");
        this.numPlayer = numPlayer;
    }

    public void SetPlayerName(String newPlayerName, String oldPlayerName){
        for(int i=0; i< connectionsToClient.size(); i++){
            if(connectionsToClient.get(i).getNamePlayer().equals(oldPlayerName)){
                System.out.println("setto il nuovo nome");
                connectionsToClient.get(i).setNamePlayer(newPlayerName);
            }
        }
    }

    // -------------------------------------------
    // METHODS FOR THE START OF THE MATCH
    // -------------------------------------------
    public void startMatch() throws StartGameException {
        System.out.println("hai invocato start multiGame");
        connectionsToClient.forEach(cc -> cc.sendNotify("il gioco è iniziato"));
        currentPlayerIndex = connectionsToClient.size();
        if(numPlayer > 1){
            multiGame = new MultiPlayerGame(numPlayer);
            players = new Player[numPlayer];
            for(int i=0; i<connectionsToClient.size(); i++){
                Player tmpP = new Player(connectionsToClient.get(i).getNamePlayer());
                players[i] = tmpP;
                multiGame.addPlayer(tmpP);
            }
            game = multiGame; // riguardo
            multiGame.startGame();
            // Send initial leadercard to client
            for(int i=0; i<connectionsToClient.size(); i++){
                try{
                    connectionsToClient.get(i).sendArrayLeaderCards(multiGame.getPlayers()[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(), true);
                } catch (LeaderCardException e) {
                    e.printStackTrace();
                }
            // manca la distribuzione di risorse e dei punti fede all'inizio della partite
            // metodo private initialResources
            }
            this.activePlayer = players[0];
            turnNumber = 0;

            // create classes of type of turn
            this.marketTurn = new MarketTurn(players, connectionsToClient, multiGame);
            this.buyDevelopmentCardTurn = new BuyDevelopmentCardTurn(players, connectionsToClient, multiGame);
            newTurn();
        }else if(numPlayer == 1){
            players = new Player[numPlayer];
            Player tmpP = new Player(connectionsToClient.get(0).getNamePlayer());
            players[0] = tmpP;
            singleGame = new SinglePlayerGame(tmpP);
            game = singleGame;
            singleGame.startGame();
            this.activePlayer = players[0];
            turnNumber = 0;
            // nuovo turno da rivedere
        }
    }


    // questi metodi servono per il multiPlayer



    public void newTurn(){
        System.out.println("è iniziato un nuovo turno");
        activePlayer = nextPlayer();
        connectionsToClient.forEach(cc -> cc.sendNotify("è il turno di " + activePlayer.getName()));
        try {
            connectionsToClient.get(currentPlayerIndex).sendArrayLeaderCards(players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(),false);
        } catch (LeaderCardException e) {
            connectionsToClient.get(currentPlayerIndex).sendNewTurn(turnNumber);
        }
        turnNumber++;
    }

    public void discardInitialLeaderCards(String playerName, int leaderCard1, int leaderCard2){
        for(int i =0; i< players.length; i++){
            if(playerName.equals(players[i].getName())){
                try{
                    System.out.println("rimuovo le carte");
                    players[i].getPlayerBoard().getLeaderCardHandler().removeInitialLeaderCard(leaderCard1, leaderCard2);
                } catch (LeaderCardException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void leaderCardTurn(String playerName, ArrayList<Integer> positions) {
        for(int i=positions.size()-1; i>=0; i--){
            switch (positions.get(i)){
                case 2:
                    try {
                        players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().discardLeaderCard(i);
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

        connectionsToClient.get(currentPlayerIndex).sendNewTurn(turnNumber);
    }

    // -------------------------------------------
    // METHODS FOR THE MARKET TURN
    // -------------------------------------------
    public void marketTurn(){
        marketTurn.marketTurn(currentPlayerIndex);
    }

    public void marketChooseLine(String namePlayer, int line){
        marketTurn.marketChooseLine(namePlayer, line, currentPlayerIndex);
    }

    public void saveNewDepositState(ArrayList<Resource> newDepositState, int discardResources){
        boolean tmp = marketTurn.saveNewDepositState(newDepositState, discardResources, currentPlayerIndex);
        if(tmp){
            newTurn();
        }

    }

    // -------------------------------------------
    // METHODS FOR THE BUY DEVELOPMENT CARD TURN
    // -------------------------------------------

    public void developmentCardTurn(){
        buyDevelopmentCardTurn.developmentCardTurn(currentPlayerIndex);
    }

    public void buyDevelopmentCard(int color, int level){
        buyDevelopmentCardTurn.buyDevelopmentCard(color, level, currentPlayerIndex);
    }

    public void spaceDevelopmentCard(int space){
        boolean tmp = buyDevelopmentCardTurn.spaceDevelopmentCard(space, currentPlayerIndex);
        if(tmp){
            newTurn();
        }
    }


    private Player nextPlayer(){
        if(currentPlayerIndex < players.length-1) {
            currentPlayerIndex++;
            return players[currentPlayerIndex];
        }else{
            currentPlayerIndex = 0;
            return players[0];
        }
    }


}
