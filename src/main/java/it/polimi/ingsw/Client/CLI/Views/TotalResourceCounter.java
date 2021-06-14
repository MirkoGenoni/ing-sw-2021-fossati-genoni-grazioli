package it.polimi.ingsw.Client.CLI.Views;

import it.polimi.ingsw.Client.CLI.Views.ProductionView.DevelopmentCardSymbols;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TotalResourceCounter {
    String[] representation;

    /*public static void main(String[] args) {
        Map<Resource, Integer> strongbox = new HashMap<>();
        for(Resource r: Resource.values())
            strongbox.put(r, 2);

        ArrayList<Resource> deposit = new ArrayList<>();
        deposit.add(null);
        deposit.add(Resource.COIN);
        deposit.add(null);
        deposit.add(null);
        deposit.add(Resource.SERVANT);
        deposit.add(null);

        ArrayList<Resource> additionalDeposit = new ArrayList<>();
        additionalDeposit.add(null);
        additionalDeposit.add(null);
        additionalDeposit.add(Resource.STONE);
        additionalDeposit.add(Resource.STONE);

        TotalResourceCounter prova = new TotalResourceCounter(strongbox, deposit, additionalDeposit);

        System.out.println("COIN:3 SERVANT:3 STONE:4 SHIELD:2");
        for(int i=0; i<3; i++){
            System.out.println(prova.returnLine(i));
        }
    }*/

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

    public String returnLine(int numLine){
        return representation[numLine];
    }

}
