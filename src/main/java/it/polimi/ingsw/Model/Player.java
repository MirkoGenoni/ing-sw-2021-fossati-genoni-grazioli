package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Gameboard.Gameboard;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;

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

    public void createGameBoard(ArrayList<LeaderCard> leaderCardGiven){
       /* try{
            playerBoard = new Gameboard(leaderCardGiven);
        }catch(LeaderCardException e){
            System.out.println(e.getMessage());
        }*/
    }

    public boolean checkTurn(){
        return true;
    }
}
