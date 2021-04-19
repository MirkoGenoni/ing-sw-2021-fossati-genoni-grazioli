package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Game.MultiPlayerGame;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Market.Marble;
import it.polimi.ingsw.Server.ConnectionToClient;

import java.util.ArrayList;

/*
   modifica direttamente il model
 */
public class ControllerToModel {
    private final Player[] players;
    private final ArrayList<ConnectionToClient> connectionsToClient;
    private final MultiPlayerGame game;
    private Player activePlayer;
    private int turnNumber=1;
    private int currentPlayerIndex = 3;

    public ControllerToModel(ArrayList<ConnectionToClient> connectionToClient, Player[] players, MultiPlayerGame game) {
        this.connectionsToClient = connectionToClient;
        this.players = players;
        this.game = game;
        this.activePlayer = players[0];
    }

    public void startMatch() throws StartGameException {
        System.out.println("hai invocato start game");
        connectionsToClient.forEach(cc -> cc.sendStartGame("il gioco è iniziato"));
        game.startGame();
        turnNumber = 0;
        newTurn();
    }


    //
    public void newTurn(){
        System.out.println("è iniziato un nuovo turno");
        activePlayer = nextPlayer();
        connectionsToClient.forEach(cc -> cc.sendNotify("è il turno di " + activePlayer.getName()));
        connectionsToClient.get(currentPlayerIndex).sendNewTurn(turnNumber);
        turnNumber++;
    }

    public void marketTurn(){
        System.out.println("prendo il market");
        connectionsToClient.get(currentPlayerIndex).sendMarket(game.getMarketBoard().getGrid(), game.getMarketBoard().getOutMarble());
    }

    public void marketChooseLine(int line){
        System.out.println("aggiungo al player");
        ArrayList<Marble> tmpM =  game.getMarketBoard().chooseLine(line);
        System.out.println(tmpM); // da finire
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


}
