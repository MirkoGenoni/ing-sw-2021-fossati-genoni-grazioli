package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Controller.Turns.BuyDevelopmentCardTurn;
import it.polimi.ingsw.Controller.Turns.MarketTurn;
import it.polimi.ingsw.Model.DevelopmentCard.CardColor;
import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
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
        while(true) {
            try {
                if ((players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable().size()<=2))
                    break;
            } catch (LeaderCardException e) {
                break;
            }
        }
        connectionsToClient.forEach(cc -> cc.sendNotify("è il turno di " + activePlayer.getName()));
        try {
            connectionsToClient.get(currentPlayerIndex).sendArrayLeaderCards(players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(),false);
        } catch (LeaderCardException e) {
            turnToView();
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

        turnToView();
    }

    // -------------------------------------------
    // METHODS FOR THE MARKET TURN
    // -------------------------------------------

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


    // -------------------------------------------
    // METHODS FOR THE BUY DEVELOPMENT CARD TURN
    // -------------------------------------------
    public void activateProduction(boolean useBaseProduction, Resource resourceRequested1, Resource resourceRequested2,
                                   ProductedMaterials resourceGranted, ArrayList<Boolean> useLeaders, ArrayList<Resource> materialLeaders,
                                   ArrayList<Boolean> useDevelop, String playerName){


        Map<Resource,Integer> materialRequested = new HashMap<>();
        for(Resource r : Resource.values())
            materialRequested.put(r,0);

        Map<ProductedMaterials, Integer> materialGranted = new HashMap<>();
        for(ProductedMaterials p : ProductedMaterials.values())
            materialGranted.put(p,0);

        Gameboard actualPlayerBoard = players[currentPlayerIndex].getPlayerBoard();

        if(useBaseProduction){
            materialRequested.put(resourceRequested1,materialRequested.get(resourceRequested1)+1);
            materialRequested.put(resourceRequested2,materialRequested.get(resourceRequested2)+1);
            materialGranted.put(resourceGranted, materialGranted.get(resourceGranted)+1);
        }




        if(useLeaders.contains(true)) {
            ArrayList<ProductedMaterials> productedAdapter = new ArrayList<>();
            for (Resource r : materialLeaders) {
                    productedAdapter.add(ProductedMaterials.valueOf(r.name()));
            }
            try {
                ArrayList<LeaderCard> activeLeaders = actualPlayerBoard.getLeaderCardHandler().getLeaderCardsActive();

                for (int i = 0; i < activeLeaders.size(); i++) {
                    if (useLeaders.get(i) && materialLeaders.get(i)!=null) {
                        if(activeLeaders.get(i).getSpecialAbility().getEffect().equals("additionalProduction")) {
                            Resource request = activeLeaders.get(i).getSpecialAbility().getMaterialType();
                            materialRequested.put(request, materialRequested.get(request) + 1);
                            materialGranted.put(productedAdapter.get(i),materialGranted.get(productedAdapter.get(i))+1);
                            materialGranted.put(ProductedMaterials.FAITHPOINT, materialGranted.get(ProductedMaterials.FAITHPOINT)+1);
                        }
                        else{
                            System.out.println("leader "+i+" not additionalProduction");
                        }
                    }
                }

            }catch (LeaderCardException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }


        if(useDevelop.contains(true)) {

            ArrayList<DevelopmentCard> activeDevelopment = actualPlayerBoard.getDevelopmentCardHandler().getActiveDevelopmentCard();

            for(int i=0; i < activeDevelopment.size(); i++){
                if (activeDevelopment.get(i)!=null && useDevelop.get(i)){
                    System.out.println("Active prod number "+ i +" ?");

                    Map<Resource,Integer> mapRequest = activeDevelopment.get(i).getMaterialRequired();
                    for(Resource r : mapRequest.keySet())
                        materialRequested.put(r,materialRequested.get(r) + mapRequest.get(r));

                    Map<ProductedMaterials,Integer> mapProd = activeDevelopment.get(i).getProductionResult();
                    for(ProductedMaterials prod : mapProd.keySet())
                        materialGranted.put(prod, materialGranted.get(prod) + mapProd.get(prod));
                }
                else {
                    System.out.println("Have not active DevelopmentCard in space " + i);
                }
            }
        }


        if(actualPlayerBoard.getResourceHandler().checkMaterials(materialRequested)){
            try {
                actualPlayerBoard.getResourceHandler().takeMaterials(materialRequested);
            } catch (ResourceException e) {
                e.printStackTrace();
            }
            Map<Resource, Integer> materialForStrongBox = new HashMap<>();
            for(Resource r : Resource.values())
                materialForStrongBox.put(r, materialGranted.get(ProductedMaterials.valueOf(r.name())));
            actualPlayerBoard.getResourceHandler().addMaterialStrongbox(materialForStrongBox);
            int faithPoints = materialGranted.get(ProductedMaterials.FAITHPOINT); //TODO AGGIORNARE FAITHTRACK
            System.out.println("Risorse aggiunte correttamente alla strongbox");
            connectionsToClient.get(currentPlayerIndex).sendNotify("Risorse aggiunte correttamente alla strongbox");
        }
        else{
            System.out.println("non ci sono abbastanza risorse");
            connectionsToClient.get(currentPlayerIndex).sendNotify("NON HAI ABBASTANZA RISORSE!!!");
        }

        newTurn();

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

    private void turnToView(){
        try{
            connectionsToClient.get(currentPlayerIndex).sendNewTurn(turnNumber, game.getMarketBoard(), game.getDevelopmentCardsAvailable(),
                    players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getDepositState(),
                    players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getStrongboxState(),
                    players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive(),
                    players[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().getActiveDevelopmentCard()
            );
        } catch (LeaderCardException er) {
            connectionsToClient.get(currentPlayerIndex).sendNewTurn(turnNumber, game.getMarketBoard(), game.getDevelopmentCardsAvailable(),
                    players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getDepositState(),
                    players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getStrongboxState(),
                    null,
                    players[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().getActiveDevelopmentCard()
            );
        }
    }



}
