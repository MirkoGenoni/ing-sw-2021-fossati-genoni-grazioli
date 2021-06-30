package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.turns.*;
import it.polimi.ingsw.model.developmentcard.ProductedMaterials;
import it.polimi.ingsw.model.exceptions.LeaderCardException;
import it.polimi.ingsw.model.exceptions.ResourceException;
import it.polimi.ingsw.model.exceptions.StartGameException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.MultiPlayerGame;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.game.SinglePlayerGame;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.server.ConnectionToClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
   modifica direttamente il model
 */
public class ControllerToModel {
    private Player[] players;
    private final Map<String, ConnectionToClient> connections;
    private final ArrayList<String> playerDisconnected;
    private final ArrayList<String> orderPlayerConnections;
    private MultiPlayerGame multiGame;
    private SinglePlayerGame singleGame;
    private Game game;
    private Player activePlayer;
    private int turnNumber;
    private int currentPlayerIndex;
    private int numPlayer;
    private int firstPlayer;

    private final ArrayList<String> playerWithInitialResource;

    // type of turn
    private MarketTurn marketTurn;
    private BuyDevelopmentCardTurn buyDevelopmentCardTurn;
    private LeaderCardTurn leaderCardTurn;
    private ActivateProductionTurn activateProductionTurn;
    private EndGame endGame;

    //Single Player turn
    private LorenzoTurn lorenzoTurn;


    public ControllerToModel(Map<String, ConnectionToClient> connections) {
        this.connections = connections;
        this.playerDisconnected = new ArrayList<>();
        this.orderPlayerConnections = new ArrayList<>();
        numPlayer = 0;
        playerWithInitialResource = new ArrayList<>();
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public ArrayList<String> getPlayerDisconnected() {
        return playerDisconnected;
    }

    public ArrayList<String> getOrderPlayerConnections() {
        return orderPlayerConnections;
    }

    public Game getGame() {
        return game;
    }

    public Map<String, ConnectionToClient> getConnections() {
        return connections;
    }

    public ArrayList<String> getPlayerWithInitialResource() {
        return playerWithInitialResource;
    }

    // -------------------------------------------------------
    // METHODS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------
    public void addPlayerNameOrder(String name){
        orderPlayerConnections.add(name);
    }

    public void setNumPlayer(int numPlayer) {
        System.out.println("ho settato il numero di giocatori");
        this.numPlayer = numPlayer;
    }





    // -------------------------------------------
    // METHODS FOR THE START OF THE MATCH
    // -------------------------------------------
    public void startMatch() throws StartGameException {
        connections.forEach((k,v) -> v.sendNotify("AllPlayersConnected"));
        //connectionsToClient.forEach(cc -> cc.sendNotify("AllPlayersConnected"));
        currentPlayerIndex = connections.keySet().size();
        if(numPlayer > 1){
            multiGame = new MultiPlayerGame(numPlayer);
            players = new Player[numPlayer];
            for(int i=0; i<orderPlayerConnections.size(); i++){
                Player tmpP = new Player(orderPlayerConnections.get(i));
                players[i] = tmpP;
                multiGame.addPlayer(tmpP);
            }
            game = multiGame; // riguardo
            multiGame.startGame();
            // per barare
            currentPlayerIndex = (int) (Math.random()*numPlayer);
            firstPlayer=currentPlayerIndex;
            System.out.println("il primo giocatre ha indirizzo: " + currentPlayerIndex);
            initialResources(currentPlayerIndex);
            activePlayer = players[currentPlayerIndex] ;
            /*
            for(int i=0; i<connectionsToClient.size(); i++){
                try{
                    connectionsToClient.get(i).sendArrayLeaderCards(multiGame.getPlayers()[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(), true, players[i]);
                } catch (LeaderCardException e) {
                    e.printStackTrace();
                }
            }

             */
            turnNumber = 0;
            // create classes of type of turn
            createTurns();
            //newTurn();



        }else if(numPlayer == 1){
            players = new Player[numPlayer];
            Player tmpP = new Player(orderPlayerConnections.get(0));
            players[0] = tmpP;
            activePlayer = tmpP;
            currentPlayerIndex=0;
            singleGame = new SinglePlayerGame(tmpP);
            game = singleGame;
            singleGame.startGame();
            try{
                connections.get(activePlayer.getName()).sendArrayLeaderCards(singleGame.getPlayer().getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(), true, players[0], false);
            } catch (LeaderCardException e) {
                e.printStackTrace();
            }
            this.activePlayer = players[0];
            turnNumber = 0;
            createTurns();
            System.out.println("creo lorenzo");
            lorenzoTurn = new LorenzoTurn(this, singleGame);

            //TODO PER CHEATTARE
            if(false) {
                Map<Resource, Integer> cheatMap = new HashMap<>();
                for (Resource r : Resource.values())
                    cheatMap.put(r, 99);
                players[0].getPlayerBoard().getResourceHandler().addMaterialStrongbox(cheatMap);
            }


            // nuovo turno da rivedere
            //newTurn();
        }
    }


    private void createTurns(){
        marketTurn = new MarketTurn(this);
        buyDevelopmentCardTurn = new BuyDevelopmentCardTurn(this);
        leaderCardTurn = new LeaderCardTurn(this);
        activateProductionTurn = new ActivateProductionTurn(this);
        endGame = new EndGame(this);
    }

    // questi metodi servono per il multiPlayer



    public void newTurn(boolean nextPlayer){
        System.out.println("è iniziato un nuovo turno");
        if(nextPlayer){
            activePlayer = nextPlayer();
        }

        int connectedPlayer=0;
        // quando si disconnettono tutti i giocatori tranne uno e poi si riconnettono il turno passa dal gioratore successivo in modo errato, al posto che di quello che è rimasto connesso
        while(connections.get(activePlayer.getName())==null || connectedPlayer==numPlayer){
            activePlayer = nextPlayer();
            connectedPlayer++;
            if(connections.size()==0){
                return;
            }
        }

        if(connections.size()<=1 && numPlayer>=2){
            connections.forEach((k,v) -> v.sendNotify("other player are all disconnected, wait that they rejoin the match"));
            return;
        }

        if((currentPlayerIndex == firstPlayer && endGame.endGameCheck() && connections.size()==numPlayer) || (connections.size()<numPlayer && endGame.endGameCheck())){
            Map<String, Integer> playersPoint = new HashMap<>();
            int winnerPoint=0;
            String winnerName="";
            for(int i=0; i<players.length-1; i++ ){
                //String name = connectionsToClient.get(i).getNamePlayer();
                String name = players[i].getName();
                int playerPoints = endGame.calculatePoints(i);
                playersPoint.put(name, playerPoints);
                if(playerPoints>winnerPoint){
                    winnerPoint = playerPoints;
                    winnerName = name;
                }
            }
            String message = "GAME ENDED: " +winnerName + " wins with " + winnerPoint + " points";
            if(numPlayer==1){
                connections.forEach((k,v) -> v.sendEndGame(message, playersPoint, players, game.getPlayersFaithTrack(), true,
                        game.getPlayersFaithTrack().getPosition(1), game.getDevelopmentCardsAvailable(), game.getMarketBoard()));
            }else{
                connections.forEach((k,v) -> v.sendEndGame(message, playersPoint, players, game.getPlayersFaithTrack(), false,
                        0, game.getDevelopmentCardsAvailable(), game.getMarketBoard()));
            }

            System.out.println("Game end");

        }else{
            if(numPlayer!=1){
                for(String s : connections.keySet()){
                    if(s.equals(activePlayer.getName())){
                        connections.get(s).sendNotify("It's your turn");
                    }else{
                        connections.get(s).sendNotify("It's " + activePlayer.getName() + "'s turn");
                    }
                }
            }

            try {
                connections.get(activePlayer.getName()).sendArrayLeaderCards(players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(),false, players[currentPlayerIndex], false);
            } catch (LeaderCardException e) {
                turnToView();
            }
            turnNumber++;
        }


    }




    public void initialResourcesChoose(ArrayList<Resource> initialDepositState, String playerName){
        for(int i=0; i<players.length; i++){
            if(players[i].getName().equals(playerName)){
                try {
                    players[i].getPlayerBoard().getResourceHandler().newDepositState(initialDepositState);
                } catch (ResourceException e) {
                    e.printStackTrace();
                }
            }
        }
        playerWithInitialResource.remove(playerName);
        System.out.println(playerWithInitialResource + "  sggdfgdfd");
        if(playerWithInitialResource.size()==0){
            for(int i =0; i< players.length; i++){
                if(!playerDisconnected.contains(players[i].getName())){
                    try {
                        connections.get(players[i].getName()).sendArrayLeaderCards(multiGame.getPlayers()[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(), true, players[i], false);
                    } catch (LeaderCardException e) {
                        e.printStackTrace();
                    }
                }else{
                    discardInitialLeaderCards(players[i].getName(), 0, 1);
                }
            }
        }
    }

    public void discardInitialLeaderCards(String playerName, int leaderCard1, int leaderCard2){
        for(int i =0; i< players.length; i++){
            if(playerName.equals(players[i].getName())){
                try{
                    System.out.println("rimuovo le carte di: " + playerName);
                    players[i].getPlayerBoard().getLeaderCardHandler().removeInitialLeaderCard(leaderCard1, leaderCard2);
                } catch (LeaderCardException e) {
                    e.printStackTrace();
                }
            }
        }
        // il gioco parte solo quando tutti i giocatori hanno scartato le carte leader iniziali
        int playerDiscard = 0;
        for(int i=0; i<players.length; i++){
            try {
                if(players[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable().size()<=2){
                    playerDiscard++;
                }
            } catch (LeaderCardException e) {
                e.printStackTrace();
            }
        }
        if(playerDiscard==numPlayer){
            newTurn(false);
        }

    }

    public void leaderCardTurn(String playerName, ArrayList<Integer> actions, boolean isFinal){
        leaderCardTurn.leaderTurns(playerName, actions, isFinal);
    }



    // -------------------------------------------
    // METHODS FOR THE MARKET TURN
    // -------------------------------------------
    public void marketChooseLine(String namePlayer, int line, ArrayList<Boolean> leaderMarketWhiteChange){
        marketTurn.marketChooseLine(namePlayer, line, leaderMarketWhiteChange);
    }

    public void saveNewDepositState(ArrayList<Resource> newDepositState, int discardResources, boolean isAdditional, ArrayList<Resource> additionalDepositState){
        if(marketTurn.saveNewDepositState(newDepositState, discardResources, isAdditional, additionalDepositState)){
            //newTurn(true);
            sendLeaderCardTurnFinal(true);
        }

    }

    // -------------------------------------------
    // METHODS FOR THE BUY DEVELOPMENT CARD TURN
    // -------------------------------------------
    public void buyDevelopmentCard(int color, int level){
        buyDevelopmentCardTurn.buyDevelopmentCard(color, level);
    }

    public void spaceDevelopmentCard(int space){
        if(buyDevelopmentCardTurn.spaceDevelopmentCard(space)){
            //newTurn(true);
            sendLeaderCardTurnFinal(true);
        }
    }


    // -------------------------------------------
    // METHODS FOR THE BUY DEVELOPMENT CARD TURN
    // -------------------------------------------
    public void activateProduction(boolean useBaseProduction, Resource resourceRequested1, Resource resourceRequested2,
                                   ProductedMaterials resourceGranted, ArrayList<Boolean> useLeaders, ArrayList<Resource> materialLeaders,
                                   ArrayList<Boolean> useDevelop, String playerName){

        boolean tmpB = activateProductionTurn.productionsActivation(useBaseProduction, resourceRequested1, resourceRequested2, resourceGranted,
                                    useLeaders, materialLeaders, useDevelop);
        if(tmpB){  // vedi checklorenzo
            //newTurn(true);
            sendLeaderCardTurnFinal(true);
        }


    }

    // -------------------------------------------
    // METHODS FOR THE MANAGE OF THE FAITH TRACK
    // -------------------------------------------
    public void controlPlayerPath (int numPlayer, int section){

        //int section = game.getPlayersFaithTrack().getSection(numPlayer);
        int sectionToCheck;

        for(int i=0; i<players.length; i++){
            if(i!=numPlayer){ //it is an other Player
                sectionToCheck = game.getPlayersFaithTrack().getSection(i); //control players' path
                if (sectionToCheck!=section){ //devo rimuovergli il tagliando
                    players[i].getPlayerBoard().removePopeFavorTiles(section);
                }

            }
        }
    }

    public void turnToView(){
        for(String s : connections.keySet()){
            if(s.equals(activePlayer.getName())){
                connections.get(s).sendNewTurn(turnNumber, game.getMarketBoard(), game.getDevelopmentCardsAvailable(), players, game.getPlayersFaithTrack(), true);
            }else{
                connections.get(s).sendNewTurn(turnNumber, game.getMarketBoard(), game.getDevelopmentCardsAvailable(), players, game.getPlayersFaithTrack(), false);
            }
        }

    }

    private void sendLeaderCardTurnFinal(boolean isFinal){
        try {
            connections.get(activePlayer.getName()).sendArrayLeaderCards(activePlayer.getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(),false, activePlayer, isFinal);
        } catch (LeaderCardException e) {
            if(checkMultiplayer()){
                newTurn(true);
            }

        }
    }

    public boolean checkMultiplayer(){
        //TODO metodo da mettere private dopo aver tolto il turn dalle opzioni di scelta!!!!!!
        if(turnNumber != 0 && players.length == 1){
            if(lorenzoTurn.playLorenzo()){
                connections.get(players[currentPlayerIndex].getName()).sendEndGame("You lost, LORENZO WINS!!", null, players,
                        game.getPlayersFaithTrack(), true, game.getPlayersFaithTrack().getPosition(1), game.getDevelopmentCardsAvailable(), game.getMarketBoard());
            }
            return false; // se è single game
        }else{
            return true; // se è multiplayer
        }
    }

    private void initialResources(int firstPlayer){
        int current = firstPlayer;
        for(int i=0; i<numPlayer; i++) {
            if(i == 1){
                connections.get(players[current].getName()).sendInitialResources(1, players[current].getPlayerBoard().getResourceHandler().getDepositState());
            }else if(i == 2){
                if(game.getPlayersFaithTrack().forwardPos(current)){
                    //controlPlayerPath(current); //not strictly necessary
                }
                connections.get(players[current].getName()).sendInitialResources(1, players[current].getPlayerBoard().getResourceHandler().getDepositState());
            }else if(i == 3){
                if(game.getPlayersFaithTrack().forwardPos(current)){
                    //controlPlayerPath(current); //not strictly necessary
                }
                connections.get(players[current].getName()).sendInitialResources(2, players[current].getPlayerBoard().getResourceHandler().getDepositState());
            }
            if(i>0){
                playerWithInitialResource.add(players[current].getName());
            }

            if(current < players.length-1){
                current ++;
            }else{
                current =0;
            }
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
