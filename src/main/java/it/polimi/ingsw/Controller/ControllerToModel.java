package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.Turns.*;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.Model.Game.MultiPlayerGame;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.SinglePlayerGame;
import it.polimi.ingsw.Model.Resource.Resource;
import it.polimi.ingsw.Server.ConnectionToClient;

import java.util.ArrayList;

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
    private int turnNumber;
    private int currentPlayerIndex;
    private int numPlayer;
    private int firstPlayer;

    // type of turn
    private MarketTurn marketTurn;
    private BuyDevelopmentCardTurn buyDevelopmentCardTurn;
    private LeaderCardTurn leaderCardTurn;
    private ActivateProductionTurn activateProductionTurn;
    private EndGame endGame;

    //Single Player turn
    private LorenzoTurn lorenzoTurn;


    public ControllerToModel() {
        this.connectionsToClient = new ArrayList<>();
        numPlayer = 0;
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

    public Game getGame() {
        return game;
    }

    public ArrayList<ConnectionToClient> getConnectionsToClient() {
        return connectionsToClient;
    }

    // -------------------------------------------------------
    // METHODS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------
    public void addConnectionToClient(ConnectionToClient connectionToClient){
        connectionsToClient.add(connectionToClient);
    }

    public void setNumPlayer(int numPlayer) {
        System.out.println("ho settato il numero di giocatori");
        this.numPlayer = numPlayer;
    }

    public void SetPlayerName(String newPlayerName, String oldPlayerName){
        boolean tmp = false;
        for(int j=0; j< connectionsToClient.size(); j++){
            if(connectionsToClient.get(j).getNamePlayer().equals(newPlayerName)){
                tmp=true;
            }
        }
        for(int i=0; i< connectionsToClient.size(); i++){
            if(connectionsToClient.get(i).getNamePlayer().equals(oldPlayerName)){
                if(tmp){
                    System.out.println("due nomi uguali");
                    connectionsToClient.get(i).sendPlayerName(oldPlayerName);
                }else{
                    System.out.println("setto il nuovo nome");
                    connectionsToClient.get(i).setNamePlayer(newPlayerName);
                }

            }
        }
    }

    // -------------------------------------------
    // METHODS FOR THE START OF THE MATCH
    // -------------------------------------------
    public void startMatch() throws StartGameException {
        connectionsToClient.forEach(cc -> cc.sendNotify("AllPlayersConnected"));
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
            // per barare
            currentPlayerIndex = (int) (Math.random()*numPlayer);
            firstPlayer=currentPlayerIndex;
            System.out.println("il primo giocatre ha indirizzo: " + currentPlayerIndex);
            initialResources(currentPlayerIndex);
            for(int i=0; i<connectionsToClient.size(); i++){
                try{
                    connectionsToClient.get(i).sendArrayLeaderCards(multiGame.getPlayers()[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(), true);
                } catch (LeaderCardException e) {
                    e.printStackTrace();
                }
            }
            this.activePlayer = players[0];
            turnNumber = 0;
            currentPlayerIndex --; // serve perchè il nextTurn incrementa il numero di giocatori
            // create classes of type of turn
            createTurns();
            newTurn();

        }else if(numPlayer == 1){
            players = new Player[numPlayer];
            Player tmpP = new Player(connectionsToClient.get(0).getNamePlayer());
            players[0] = tmpP;
            currentPlayerIndex=0;
            singleGame = new SinglePlayerGame(tmpP);
            game = singleGame;
            singleGame.startGame();
            try{
                connectionsToClient.get(currentPlayerIndex).sendArrayLeaderCards(singleGame.getPlayer().getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(), true);
            } catch (LeaderCardException e) {
                e.printStackTrace();
            }
            this.activePlayer = players[0];
            turnNumber = 0;
            createTurns();
            System.out.println("creo lorenzo");
            lorenzoTurn = new LorenzoTurn(this, singleGame, 1);
            // nuovo turno da rivedere
            newTurn();
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



    public void newTurn(){
        System.out.println("è iniziato un nuovo turno");
        activePlayer = nextPlayer();

        if(currentPlayerIndex == firstPlayer && endGame.endGameNotify()){
            int winnerPoint=0;
            String winnerName = " ";
            connectionsToClient.forEach(c -> c.sendNotify(" IL GIOCO E' FINITOO!!!"));
            for(int i=0; i<connectionsToClient.size(); i++ ){
                String name = connectionsToClient.get(i).getNamePlayer();
                int playerPoints = endGame.calculatePoints(i);
                connectionsToClient.forEach(c -> c.sendNotify(name + "ha " + playerPoints + " punti"));
                if(playerPoints>winnerPoint){
                    winnerPoint = playerPoints;
                    winnerName = name;
                }
            }
            String finalWinnerName = winnerName;
            int finalWinnerPoint = winnerPoint;
            connectionsToClient.forEach(c -> c.sendEndGame(finalWinnerName + " ha vinto con " + finalWinnerPoint + " punti"));
            connectionsToClient.forEach(c -> c.setActive(false));
            System.out.println("il gioco è finito");
        }else{
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

    public void leaderCardTurn(String playerName, ArrayList<Integer> actions){
        leaderCardTurn.leaderTurns(playerName, actions, currentPlayerIndex);
    }


    // -------------------------------------------
    // METHODS FOR THE MARKET TURN
    // -------------------------------------------
    public void marketChooseLine(String namePlayer, int line, ArrayList<Boolean> leaderMarketWhiteChange){
        marketTurn.marketChooseLine(namePlayer, line, currentPlayerIndex, leaderMarketWhiteChange);
    }

    public void saveNewDepositState(ArrayList<Resource> newDepositState, int discardResources, boolean isAdditional, ArrayList<Resource> additionalDepositState){
        if(marketTurn.saveNewDepositState(newDepositState, discardResources, currentPlayerIndex, isAdditional, additionalDepositState) && checkMultiplayer()){
            newTurn();
        }

    }

    // -------------------------------------------
    // METHODS FOR THE BUY DEVELOPMENT CARD TURN
    // -------------------------------------------
    public void buyDevelopmentCard(int color, int level){
        buyDevelopmentCardTurn.buyDevelopmentCard(color, level, currentPlayerIndex);
    }

    public void spaceDevelopmentCard(int space){
        if(buyDevelopmentCardTurn.spaceDevelopmentCard(space, currentPlayerIndex) && checkMultiplayer()){
            newTurn();
        }
    }


    // -------------------------------------------
    // METHODS FOR THE BUY DEVELOPMENT CARD TURN
    // -------------------------------------------
    public void activateProduction(boolean useBaseProduction, Resource resourceRequested1, Resource resourceRequested2,
                                   ProductedMaterials resourceGranted, ArrayList<Boolean> useLeaders, ArrayList<Resource> materialLeaders,
                                   ArrayList<Boolean> useDevelop, String playerName){

        boolean tmpB = activateProductionTurn.productionsActivation(useBaseProduction, resourceRequested1, resourceRequested2, resourceGranted,
                                    useLeaders, materialLeaders, useDevelop, playerName);
        if(tmpB && checkMultiplayer()){  // vedi checklorenzo
            newTurn();
        }


    }

    // -------------------------------------------
    // METHODS FOR THE MANAGE OF THE FAITH TRACK
    // -------------------------------------------

    public void controlPlayerPath (int numPlayer){

        int section = game.getPlayersFaithTrack().getSection(numPlayer);
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
        connectionsToClient.get(currentPlayerIndex).sendNewTurn(turnNumber, game.getMarketBoard(), game.getDevelopmentCardsAvailable(), players, game.getPlayersFaithTrack());
    }

    public boolean checkMultiplayer(){
        // metodo da mettere private dopo aver tolto il turn dalle opzioni di scelta!!!!!!
        if(turnNumber != 0 && players.length == 1){
            if(lorenzoTurn.playLorenzo()){
                connectionsToClient.get(currentPlayerIndex).sendEndGame("HAI PERSO, HA VINTO LORENZO!!");
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
                connectionsToClient.get(current).sendInitialResources(1, players[current].getPlayerBoard().getResourceHandler().getDepositState());
            }else if(i == 2){
                if(game.getPlayersFaithTrack().forwardPos(current)){
                    controlPlayerPath(current);
                }
                connectionsToClient.get(current).sendInitialResources(1, players[current].getPlayerBoard().getResourceHandler().getDepositState());
            }else if(i == 3){
                if(game.getPlayersFaithTrack().forwardPos(current)){
                    controlPlayerPath(current);
                }
                connectionsToClient.get(current).sendInitialResources(2, players[current].getPlayerBoard().getResourceHandler().getDepositState());
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
