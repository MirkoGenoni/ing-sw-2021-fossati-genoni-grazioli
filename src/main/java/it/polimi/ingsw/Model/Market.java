package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Resource.Resource;
import it.polimi.ingsw.Model.Resource.ResourcesFactory;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Market class is an arraylist that contains the market's marble.
 * 
 * @author Stefano Fossati
 */

public class Market {
    private Resource outMarble;
    private ArrayList<Resource> grid;

    /**
     * Constructor Market create an ArrayList of the marble, the number of each type of marble is defined by the game's rules.
     * The order of the marble is casual.
     */

    public Market(){
        grid = new ArrayList<Resource>();

        grid.addAll(ResourcesFactory.createMaterials("nothing", 4));
        grid.addAll(ResourcesFactory.createMaterials("shield", 2));
        grid.addAll(ResourcesFactory.createMaterials("stone", 2));
        grid.addAll(ResourcesFactory.createMaterials("coin", 2));
        grid.addAll(ResourcesFactory.createMaterials("servant", 2));
        grid.addAll(ResourcesFactory.createMaterials("faith", 1));

        Collections.shuffle(grid);

        outMarble = grid.remove(grid.size()-1);
    }

    /**
     * Method chooseLine select the line that the player want to choose.
     * Also move the marble following the game's rules.
     *
     * @param line report the line choose by the player.
     * @return an ArrayList of Resources selected by the player: the ArrayList has three Resources if the player choose a column, if the player choose a row the ArrayList has four Resources
     */

    /*  The ArrayList of Resource is managed in the following way:

          4    5    6    7 -----> is the parameter line passed to ChooseLine (these are the column)
       ---------------------
   1   | 00 | 01 | 02 | 03 |
       ---------------------
   2   | 04 | 05 | 06 | 07 |
       ---------------------
   3   | 08 | 09 | 10 | 11 |
   |   ---------------------
   |-->  is the parameter line passed to ChooseLine (these are the row)

   */

    public ArrayList<Resource> chooseLine(int line){
        ArrayList<Resource> marbleLine = new ArrayList<Resource>();
        int k = line - 4;

        // this if manage the choose of a row
        if( line < 4){
            for(int i=0; i<4; i++){
                marbleLine.add(ResourcesFactory.createMaterials(grid.get(i+(line-1)*4).getMaterialType()));
            }
            Resource tmpMarble =  grid.get(3+(line-1)*4);
            for(int i=3; i>0; i--){
                grid.set(i+(line-1)*4, grid.get(i-1+(line-1)*4));
            }
            grid.set((line-1)*4, outMarble);
            outMarble = tmpMarble;
        }

        // this if manage the choose of a column
        else if(line >= 4 && line < 8){
            for(int i=0; i<3; i++){
                marbleLine.add(ResourcesFactory.createMaterials(grid.get(i*4+k).getMaterialType()));
            }
            Resource tmpMarble = grid.get(8+k);
            for(int i=2; i>0; i--){
                grid.set(i*4+k, grid.get((i-1)*4+k));
            }
            grid.set(k, outMarble);
            outMarble = tmpMarble;
        }

        return marbleLine;
    }

    /**
     * Method get
     * @return grid of the Market
     */

    public ArrayList<Resource> getGrid(){
        return grid;
    }

    /**
     * Method get
     * @return outMarble of the Market
     */

    public Resource getOutMarble(){
        return outMarble;
    }


}
