package it.polimi.ingsw.model.market;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents the market
 *
 * @author Stefano Fossati
 */

public class Market {
    private Marble outMarble;
    private final ArrayList<Marble> grid;

    /**
     * Creates the structure of the market, the number of each type of marble is defined by the game's rules.
     * The order of marbles is casual.
     */

    public Market(){
        grid = new ArrayList<>();

        grid.add(Marble.NOTHING);
        grid.add(Marble.NOTHING);
        grid.add(Marble.NOTHING);
        grid.add(Marble.NOTHING);
        grid.add(Marble.COIN);
        grid.add(Marble.COIN);
        grid.add(Marble.SERVANT);
        grid.add(Marble.SERVANT);
        grid.add(Marble.SHIELD);
        grid.add(Marble.SHIELD);
        grid.add(Marble.STONE);
        grid.add(Marble.STONE);
        grid.add(Marble.FAITH);

        Collections.shuffle(grid);

        outMarble = grid.remove(grid.size()-1);
    }

    /**
     * Select the line choose by the player and returns all the elements of the line choose.
     * Also move the marble following the game's rules.
     *
     * @param line The line choose by the player.
     * @return an ArrayList of marble selected by the player:
     * the ArrayList has three marble if the player choose a column,
     * if the player choose a row the ArrayList has four marble.
     */

    /*  The ArrayList of Marble is managed in the following way:

          4    5    6    7 -----> is the parameter line passed to chooseLine (these are the column)
       +----+----+----+----+
   1   | 00 | 01 | 02 | 03 |
       +----+----+----+----+
   2   | 04 | 05 | 06 | 07 |
       +----+----+----+----+
   3   | 08 | 09 | 10 | 11 |
   |   +----+----+----+----+
   |-->  is the parameter line passed to chooseLine (these are the row)

   */

    public ArrayList<Marble> chooseLine(int line){
        ArrayList<Marble> marbleLine = new ArrayList<>();
        int k = line - 4;

        // this if manage the choose of a row
        if( line < 4){
            for(int i=0; i<4; i++){
                marbleLine.add(Marble.valueOf((grid.get(i + (line - 1) * 4).getType()).toUpperCase()));
            }
            Marble tmpMarble =  grid.get(3+(line-1)*4);
            for(int i=3; i>0; i--){
                grid.set(i+(line-1)*4, grid.get(i-1+(line-1)*4));
            }
            grid.set((line-1)*4, outMarble);
            outMarble = tmpMarble;
        }

        // this if manage the choose of a column
        else if(line >= 4 && line < 8){
            for(int i=0; i<3; i++){
                marbleLine.add(Marble.valueOf((grid.get(i * 4 + k).getType()).toUpperCase()));
            }
            Marble tmpMarble = grid.get(8+k);
            for(int i=2; i>0; i--){
                grid.set(i*4+k, grid.get((i-1)*4+k));
            }
            grid.set(k, outMarble);
            outMarble = tmpMarble;
        }

        return marbleLine;

    }

    /**
     * Getter that returns the grid of the market
     * @return the grid of the market.
     */

    public ArrayList<Marble> getGrid(){
        return new ArrayList<>(grid);
    }

    /**
     * Getter that returns the grid of the market
     * @return the outMarble of the market.
     */

    public Marble getOutMarble(){
        return outMarble;
    }

}
