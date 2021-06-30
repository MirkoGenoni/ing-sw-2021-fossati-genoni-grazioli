package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.exceptions.LeaderCardException;
import it.polimi.ingsw.model.exceptions.StartGameException;
import it.polimi.ingsw.model.gameboard.Gameboard;
import it.polimi.ingsw.model.leadercard.LeaderCard;

import java.util.ArrayList;

/**
 * This class represents the player.
 *
 * @author Stefano Fossati
 */
public class Player {
    final private String name;
    private Gameboard playerBoard;

    public Player(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Gameboard getPlayerBoard(){
        return playerBoard;
    }

    public void createGameBoard(ArrayList<LeaderCard> leaderCardGiven) throws StartGameException {
      try{
            playerBoard = new Gameboard(leaderCardGiven);
      }catch(LeaderCardException e) {
          //System.out.println(e.getMessage());
          throw new StartGameException(e.getMessage());
      }
    }

}
