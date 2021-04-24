package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.Model.Game.MultiPlayerGame;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.SinglePlayerGame;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Market.Marble;
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

    public ControllerToModel() {
        this.connectionsToClient = new ArrayList<>();
        numPlayer = 0;
    }

    public void addConnectionToClient(ConnectionToClient connectionToClient){
        connectionsToClient.add(connectionToClient);
    }

    // il controller crea direttamente il model
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
                    connectionsToClient.get(i).sendArrayLeaderCards(multiGame.getPlayers()[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable());
                } catch (LeaderCardException e) {
                    e.printStackTrace();
                }
            // manca la distribuzione di risorse e dei punti fede all'inizio della partite
            // metodo private initialResources
            }
            this.activePlayer = players[0];
            turnNumber = 0;
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


    //


    public int getNumPlayer() {
        return numPlayer;
    }

    public void setNumPlayer(int numPlayer) {
        System.out.println("ho settato il numero di giocatori");
        this.numPlayer = numPlayer;
    }

    // questi metodi servono per il multiPlayer

    public void SetPlayerName(String newPlayerName, String oldPlayerName){
        for(int i=0; i< connectionsToClient.size(); i++){
            if(connectionsToClient.get(i).getNamePlayer().equals(oldPlayerName)){
                System.out.println("setto il nuovo nome");
                connectionsToClient.get(i).setNamePlayer(newPlayerName);
            }
        }
    }

    public void newTurn(){
        System.out.println("è iniziato un nuovo turno");
        activePlayer = nextPlayer();
        connectionsToClient.forEach(cc -> cc.sendNotify("è il turno di " + activePlayer.getName()));
        connectionsToClient.get(currentPlayerIndex).sendNewTurn(turnNumber);
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

    // metodi per il turno del market

    public void marketTurn(){
        System.out.println("prendo il market");
        connectionsToClient.get(currentPlayerIndex).sendMarket(game.getMarketBoard().getGrid(), game.getMarketBoard().getOutMarble());
    }

    public void marketChooseLine(String namePlayer, int line){
        System.out.println("aggiungo al player");
        ArrayList<Marble> tmpM =  game.getMarketBoard().chooseLine(line);
        System.out.println(tmpM);

        if(tmpM.contains(Marble.FAITH)){    // se ci sono marbel di tipo faith incrementa direttamente la pedina del giocatore sul tracciato fede
            game.getPlayersFaithTrack().forwardPos(currentPlayerIndex);
            tmpM.remove(Marble.FAITH);
        }

        if(tmpM.contains(Marble.NOTHING)){   // le leader card da usare sarebbero da scegliere....
            try{
                ArrayList<LeaderCard> tmpL = players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive();
                if(!tmpL.isEmpty()){                                        // controlla se ci sono carte leader attive
                    for(int i=0; i< tmpL.size() && tmpM.contains(Marble.NOTHING); i++){
                        if(tmpL.get(i).getSpecialAbility().getEffect().equals("marketWhiteChange") && tmpM.contains(Marble.NOTHING)){  // se ci sono carte leader attive di tipo marketWhiteChenge converte la marble
                            tmpM.remove(Marble.NOTHING);
                            tmpM.add(Marble.valueOf(tmpL.get(i).getSpecialAbility().getMaterialType().toString()));
                        }
                    }
                }
            } catch (LeaderCardException e) {
                System.out.println(players[currentPlayerIndex].getName() + e.getMessage());
            }
        }
        System.out.println(tmpM);
        while(tmpM.contains(Marble.NOTHING)){           // toglie le marble nothing in eccesso
            tmpM.remove(Marble.NOTHING);
        }

        ArrayList<Resource> tmpR = new ArrayList<>();  // converte da marble a resource
        for( Marble m : tmpM){
            tmpR.add(Resource.valueOf(m.toString()));
        }

        connectionsToClient.get(currentPlayerIndex).sendReorganizeDeposit(tmpR, players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getDepositState());
        newTurn();
    }

    public void saveNewDepositState(ArrayList<Resource> newDepositState, ArrayList<Resource> discardResources){
        // deve fare il check del nuovo stato del deposito se non va bene rimanda l'evento di riorganizzare il deposito
        try{
            players[currentPlayerIndex].getPlayerBoard().getResourceHandler().newDepositState(newDepositState);
        } catch (ResourceException e) {
            connectionsToClient.get(currentPlayerIndex).sendNotify(e.getMessage());
            connectionsToClient.get(currentPlayerIndex).sendReorganizeDeposit(discardResources, newDepositState);
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
