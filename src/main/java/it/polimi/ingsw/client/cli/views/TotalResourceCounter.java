package it.polimi.ingsw.client.cli.views;

import it.polimi.ingsw.client.cli.views.productionview.DevelopmentCardSymbols;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class prints the total count of resources possessed by the player for the buy of development
 * card and the activation of development card
 *
 * @author Mirko Genoni
 */
public class TotalResourceCounter {
    String[] representation;

    /**
     * Constructor of the class initializes all the data structure
     * @param strongbox is the current state of the player's strongbox
     * @param deposit is the current state of the player's deposit
     * @param additionalDeposit is the current state of the player's additionalDeposit
     */
    public TotalResourceCounter(Map<Resource, Integer> strongbox, ArrayList<Resource> deposit, ArrayList<Resource> additionalDeposit){
        Map<Resource, Integer> tmp = new HashMap<>(strongbox);

        for(Resource r: deposit){
            if(r!=null)
                tmp.put(r, tmp.get(r)+1);
        }

        if(additionalDeposit.size()!=0){
            for(Resource r: additionalDeposit){
                if(r!=null)
                    tmp.put(r, tmp.get(r)+1);
            }
        }

        ArrayList<DevelopmentCardSymbols> symbols = new ArrayList<>();
        symbols.add(DevelopmentCardSymbols.SHIELD);
        symbols.add(DevelopmentCardSymbols.COIN);
        symbols.add(DevelopmentCardSymbols.STONE);
        symbols.add(DevelopmentCardSymbols.SERVANT);

        this.representation = new String[3];
        saveState(tmp, symbols);
    }

    /**
     * This method saves the visualization of the resource counter
     *
     * @param totalResources is a map that contains the count of all the resources
     * @param symbols contains a square colored by the type of resource
     */
    private void saveState(Map<Resource, Integer> totalResources, ArrayList<DevelopmentCardSymbols> symbols){
        this.representation[0] = "┃                                                  " + symbols.get(0).returnLine(0) + "            " + symbols.get(1).returnLine(0) + "            " + symbols.get(2).returnLine(0) + "            " + symbols.get(3).returnLine(0) + "               ┃";

        this.representation[1] = "┃            RESOURCE AVAILABLE:               ";
        if(totalResources.get(Resource.valueOf(symbols.get(0).toString()))<10)
               this.representation[1] = this.representation[1] + "0" + totalResources.get(Resource.valueOf(symbols.get(0).toString())) + "x " + symbols.get(0).returnLine(1) + "        ";
        else
            this.representation[1] = this.representation[1] + totalResources.get(Resource.valueOf(symbols.get(0).toString())) + "x " + symbols.get(0).returnLine(1) + "        ";

        if(totalResources.get(Resource.valueOf(symbols.get(1).toString()))<10)
            this.representation[1] = this.representation[1] + "0" + totalResources.get(Resource.valueOf(symbols.get(1).toString())) + "x " + symbols.get(1).returnLine(1) + "        ";
        else
            this.representation[1] = this.representation[1] + totalResources.get(Resource.valueOf(symbols.get(1).toString())) + "x " + symbols.get(1).returnLine(1) + "        ";

        if(totalResources.get(Resource.valueOf(symbols.get(2).toString()))<10)
            this.representation[1] = this.representation[1] + "0" + totalResources.get(Resource.valueOf(symbols.get(2).toString())) + "x " + symbols.get(2).returnLine(1) + "        ";
        else
            this.representation[1] = this.representation[1] + totalResources.get(Resource.valueOf(symbols.get(2).toString())) + "x " + symbols.get(2).returnLine(1) + "        ";

        if(totalResources.get(Resource.valueOf(symbols.get(3).toString()))<10)
            this.representation[1] = this.representation[1] + "0" + totalResources.get(Resource.valueOf(symbols.get(3).toString())) + "x " + symbols.get(3).returnLine(1) + "               ┃";
        else
            this.representation[1] = this.representation[1] + totalResources.get(Resource.valueOf(symbols.get(3).toString())) + "x " + symbols.get(3).returnLine(1) + "               ┃";

        this.representation[2] = "┃                                                  " + symbols.get(0).returnLine(2) + "            " + symbols.get(1).returnLine(2) + "            " + symbols.get(2).returnLine(2) + "            " + symbols.get(3).returnLine(2) + "               ┃";
    }

    /**
     * Returns one at the time the visualization
     *
     * @param numLine is the number of line asked
     * @return the line asked
     */
    public String returnLine(int numLine){
        return representation[numLine];
    }

}
