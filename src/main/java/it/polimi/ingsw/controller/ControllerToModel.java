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

/**
 * Class to control and manage the game
 * @author Stefano Fossati
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

    /**
     * Constructor of the class
     * @param connections map for player's name and each connections
     */
    public ControllerToModel(Map<String, ConnectionToClient> connections) {
        this.connections = connections;
        this.playerDisconnected = new ArrayList<>();
        this.orderPlayerConnections = new ArrayList<>();
        numPlayer = 0;
        playerWithInitialResource = new ArrayList<>();
    }

    /**
     * getter of the number of the playing client in the match
     * @return the number of the players
     */
    public int getNumPlayer() {
        return numPlayer;
    }

    /**
     * getter of the players
     * @return the array of the players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * getter the index of the current player
     * @return the index of the current player
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * getter of the active player
     * @return the player who's playing
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * getter of disconnected players
     * @return  names of disconnected players
     */
    public ArrayList<String> getPlayerDisconnected() {
        return playerDisconnected;
    }

    /**
     * getter of order player connections
     * @return return the arraylist player's name in order of the first connection
     */
    public ArrayList<String> getOrderPlayerConnections() {
        return orderPlayerConnections;
    }

    /**
     * getter of the game
     * @return the current game
     */
    public Game getGame() {
        return game;
    }

    /**
     * getter of connections to client
     * @return a map with player name as a key and connection to client
     */
    public Map<String, ConnectionToClient> getConnections() {
        return connections;
    }

    /**
     * manage and control if the player has chosen the initial resources
     * @return the array with the players that hasn't already chosen the initial resources
     */
    public ArrayList<String> getPlayerWithInitialResource() {
        return playerWithInitialResource;
    }

    // -------------------------------------------------------
    // METHODS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------

    /**
     * add a connected player
     * @param name name of the player
     */
    public void addPlayerNameOrder(String name){
        orderPlayerConnections.add(name);
    }

    /**
     * set the number of playing players
     * @param numPlayer how many players are going to play
     */
    public void setNumPlayer(int numPlayer) {
        System.out.println("ho settato il numero di giocatori");
        this.numPlayer = numPlayer;
    }





    // -------------------------------------------
    // METHODS FOR THE START OF THE MATCH
    // -------------------------------------------

    /**
     * start the match creating lorenzo if is a singleplayer game
     * @throws StartGameException if there's any problem in starting game
     */
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

    /**
     * create the manager for each type of turns for the match
     */
    private void createTurns(){
        marketTurn = new MarketTurn(this);
        buyDevelopmentCardTurn = new BuyDevelopmentCardTurn(this);
        leaderCardTurn = new LeaderCardTurn(this);
        activateProductionTurn = new ActivateProductionTurn(this);
        endGame = new EndGame(this);
    }

    // questi metodi servono per il multiPlayer


    /**
     * check if the game is ended and send a new turn event to clients
     * @param nextPlayer specify if the controller has to pass the turn to a new player
     */
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


    /**
     * put the initial resources choice in player's deposit
     * @param initialDepositState the state of the initial deposit
     * @param playerName the name of the player
     */
    public void initialResourcesChoose(ArrayList<Resource> initialDepositState, String playerName){
        for(int i=0; i<players.length; i++){
            if(players[i].getName().equals(playerName)){
                try {
                    players[i].getPlayerBoard().getResourceHandler().newDepositState(initialDepositState);
                } catch (ResourceException e) {
                    //e.printStackTrace();
                    connections.get(playerName).sendInitialResources(2, players[i].getPlayerBoard().getResourceHandler().getDepositState());
                    return;
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

    /**
     * discard two initial leader-cards from the started four
     * @param playerName the player who's discarding the leaders
     * @param leaderCard1 the first leader to discard
     * @param leaderCard2 the second leader to discard
     */
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

    /**
     * start the leader card turn
     * @param playerName the player who's playing
     * @param actions the action for each leader in order respect to the available ones
     * @param isFinal if is the end of the turn
     */
    public void leaderCardTurn(String playerName, ArrayList<Integer> actions, boolean isFinal){
        leaderCardTurn.leaderTurns(playerName, actions, isFinal);
    }



    // -------------------------------------------
    // METHODS FOR THE MARKET TURN
    // -------------------------------------------

    /**
     *
     * @param namePlayer the player who's playing the turn
     * @param line the line to choice from the market
     * @param leaderMarketWhiteChange the leader white-change to use, the position in the array specify the leader to use
     *                                in order respect on active leaders
     */
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

    /**
     * start the turn buy-development-card
     * @param color color of the development to buy
     * @param level level of the development to buy
     */
    public void buyDevelopmentCard(int color, int level){
        buyDevelopmentCardTurn.buyDevelopmentCard(color, level);
    }

    /**
     * Put a bought development card in an available space
     * @param space the space where to put the bought card
     */
    public void spaceDevelopmentCard(int space){
        if(buyDevelopmentCardTurn.spaceDevelopmentCard(space)){
            //newTurn(true);
            sendLeaderCardTurnFinal(true);
        }
    }


    // -------------------------------------------
    // METHODS FOR THE BUY DEVELOPMENT CARD TURN
    // -------------------------------------------

    /**
     *
     * @param useBaseProduction boolean that specify if the player want to use the base production
     * @param resourceRequested1 if the player want to use the base production indicates the first resource to use to develop
     * @param resourceRequested2 if the player want to use the base production indicates the second resource to use to develop
     * @param resourceGranted if the player want to use the base production indicates resource granted from the base production
     * @param useLeaders Array of Boolean that specify if the player want to use leaders productions, the boolean is in the same
     *      *                   position of the leader the player want to use
     * @param materialLeaders The material the player want from the leader production, the resource is in the same
     *      *      *                   position of the leader the player want to use
     * @param useDevelop Array of Boolean that specify if the player want to use a production card, the boolean is in the same
     *      *      *                   position of the production card the player want to use
     * @param playerName the player who has chosen the development turn
     */
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

    /**
     * control the other player's path if a player go on a pope-space
     * @param numPlayer the player who activate the pope-space
     * @param section the section in which the pope-space has been activated
     */
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

    /**
     * send to each player the current status of the match
     */
    public void turnToView(){
        for(String s : connections.keySet()){
            if(s.equals(activePlayer.getName())){
                connections.get(s).sendNewTurn(turnNumber, game.getMarketBoard(), game.getDevelopmentCardsAvailable(), players, game.getPlayersFaithTrack(), true);
            }else{
                connections.get(s).sendNewTurn(turnNumber, game.getMarketBoard(), game.getDevelopmentCardsAvailable(), players, game.getPlayersFaithTrack(), false);
            }
        }

    }

    /**
     * send the turn to activate or discard a leader from the available ones
     * @param isFinal if it's the end of the turn
     */
    private void sendLeaderCardTurnFinal(boolean isFinal){
        try {
            connections.get(activePlayer.getName()).sendArrayLeaderCards(activePlayer.getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable(),false, activePlayer, isFinal);
        } catch (LeaderCardException e) {
            if(checkMultiplayer()){
                newTurn(true);
            }

        }
    }

    /**
     * if a player is playing a match in single-game do lorenzo turn and return false otherwise return true
     * @return true if there are more than one player
     */
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

    /**
     * Grant to each player the initial amount of resources starting from the first player
     * @param firstPlayer the player who start the match
     */
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

    /**
     * update the turn giving the next player
     * @return the player who has to play
     */
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
