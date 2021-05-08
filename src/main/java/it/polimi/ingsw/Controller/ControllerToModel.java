package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.Turns.ActivateProductionTurn;
import it.polimi.ingsw.Controller.Turns.BuyDevelopmentCardTurn;
import it.polimi.ingsw.Controller.Turns.LeaderCardTurn;
import it.polimi.ingsw.Controller.Turns.MarketTurn;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
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
    private int turnNumber=1;
    private int currentPlayerIndex;
    private int numPlayer;

    // type of turn
    private MarketTurn marketTurn;
    private BuyDevelopmentCardTurn buyDevelopmentCardTurn;
    private LeaderCardTurn leaderCardTurn;
    private ActivateProductionTurn activateProductionTurn;

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
            marketTurn = new MarketTurn(this);
            buyDevelopmentCardTurn = new BuyDevelopmentCardTurn(this);
            leaderCardTurn = new LeaderCardTurn(this);
            activateProductionTurn = new ActivateProductionTurn(this);

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

    public void leaderCardTurn(String playerName, ArrayList<Integer> actions){
        leaderCardTurn.leaderTurns(playerName, actions, currentPlayerIndex);
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

        activateProductionTurn.productionsActivation(useBaseProduction, resourceRequested1, resourceRequested2, resourceGranted,
                                    useLeaders, materialLeaders, useDevelop, playerName);
        newTurn();

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

    private Player nextPlayer(){
        if(currentPlayerIndex < players.length-1) {
            currentPlayerIndex++;
            return players[currentPlayerIndex];
        }else{
            currentPlayerIndex = 0;
            return players[0];
        }
    }

    public void turnToView(){
        try{
            connectionsToClient.get(currentPlayerIndex).sendNewTurn(turnNumber, game.getMarketBoard(), game.getDevelopmentCardsAvailable(),
                    players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getDepositState(),
                    players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getStrongboxState(),
                    players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive(),
                    players[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().getActiveDevelopmentCard(),
                    players[currentPlayerIndex].getPlayerBoard().getPopeFavorTilesState(),
                    game.getPlayersFaithTrack().getPosition(currentPlayerIndex)
            );
        } catch (LeaderCardException err) {
            connectionsToClient.get(currentPlayerIndex).sendNewTurn(turnNumber, game.getMarketBoard(), game.getDevelopmentCardsAvailable(),
                    players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getDepositState(),
                    players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getStrongboxState(),
                    null,
                    players[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().getActiveDevelopmentCard(),
                    players[currentPlayerIndex].getPlayerBoard().getPopeFavorTilesState(),
                    game.getPlayersFaithTrack().getPosition(currentPlayerIndex)
            );
        }
    }



}
