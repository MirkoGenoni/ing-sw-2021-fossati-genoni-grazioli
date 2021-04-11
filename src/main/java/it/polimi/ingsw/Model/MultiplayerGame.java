package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.StartGameException;

/**
 * This class represents the multiplayer.
 *
 * @author Stefano Fossati
 */
public class MultiplayerGame extends Game{
    private Player players[];
    private int numberActivePlayer;

    public MultiplayerGame(int numPlayer) {
        super(numPlayer);
        players = new Player[numPlayer];
    }

    public void addPlayer(String name) throws StartGameException {
        int i=0;
        while(players[i]!=null){
            i++;
        }
        if(i<players.length-1){
            players[i] = new Player(name);
        }else{
            throw new StartGameException("players all initialized");
        }

    }

    public Player getActivePlayer(){
        return players[numberActivePlayer];
    }


}
