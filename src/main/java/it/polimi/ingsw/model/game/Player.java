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

    /**
     * constructor of the class
     * @param name name of the player
     */
    public Player(String name){
        this.name = name;
    }

    /**
     * getter of the name of the player
     * @return the name of the player
     */
    public String getName(){
        return name;
    }

    /**
     * getter of the playerBoard of the player
     * @return the playerBoard of the player
     */
    public Gameboard getPlayerBoard(){
        return playerBoard;
    }

    /**
     * create the gameBoard with the available leader
     * @param leaderCardGiven the leader choose at the beginning of the game
     * @throws StartGameException if there's any problem with the initial leaders
     */
    public void createGameBoard(ArrayList<LeaderCard> leaderCardGiven) throws StartGameException {
      try{
            playerBoard = new Gameboard(leaderCardGiven);
      }catch(LeaderCardException e) {
          //System.out.println(e.getMessage());
          throw new StartGameException(e.getMessage());
      }
    }

}
