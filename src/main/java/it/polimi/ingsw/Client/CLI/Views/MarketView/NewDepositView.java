package it.polimi.ingsw.Client.CLI.Views.MarketView;

import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NewDepositView {

    ArrayList<ResourceIcon> depositState;
    Map<String, Integer> marketReceived;

    public NewDepositView(ArrayList<Resource> depositState, ArrayList<Resource> marketReceived){

        this.depositState = new ArrayList<>();

        for(int i=0; i<depositState.size(); i++) {
            if (depositState.get(i) == null)
                this.depositState.add(ResourceIcon.NOTHING);
            else {
                this.depositState.add(ResourceIcon.valueOf(depositState.get(i).toString()));
            }
        }

        this.marketReceived = new HashMap<>();
        for(ResourceIcon r: ResourceIcon.values())
            this.marketReceived.put(r.toString(), 0);

        this.marketReceived.put(ResourceIcon.NOTHING.toString(), 6);

        for(Resource r: marketReceived){
            this.marketReceived.put(r.toString(), this.marketReceived.get(r.toString())+1);
        }

    }

    public ArrayList<Resource> getDepositState() {

        ArrayList<Resource> tmp = new ArrayList<>();

        for(int i=0; i<depositState.size(); i++){
            if(depositState.get(i).toString().equals(ResourceIcon.NOTHING.toString()))
                tmp.add(null);
            else{
                tmp.add(Resource.valueOf(depositState.get(i).toString()));
            }
        }

        return tmp;
    }

    public int getMarketReceived() {
        int num = 0;

        for(ResourceIcon r: ResourceIcon.values())
            if(!r.equals(ResourceIcon.NOTHING))
                num = num + marketReceived.get(r.toString());

        return num;
    }

    public void LaunchView(){

        PrintDepositChoise(this.depositState, marketReceived);

        String input = "";

        while(true){

            Scanner in = new Scanner(System.in);
            input = in.nextLine();

            //separo le due stringhe usando la virgola come separatore
            String[] formatInput = input.split(",", 2);

            //verifico che il giocatore non abbia inserito done
            if(formatInput.length!=0)
                if(formatInput[0].equals("done"))
                    break;

            if(formatInput.length==2) {
                //elimino eventuali spazi iniziali dalle due stringhe
                formatInput[0] = formatInput[0].trim();

                int num = -1;
                formatInput[1] = formatInput[1].trim();

                //trasformo la seconda stringa in un numero
                try{
                    num = Integer.parseInt(formatInput[1]);
                } catch(IllegalArgumentException e){
                    System.out.println("Insert a number");
                }

                formatInput[0] = formatInput[0].toUpperCase();

                //tolgo il materiale dalle risorse ottenute dal market da inserire in deposito
                if (marketReceived.containsKey(formatInput[0]) && num > 0 && num < 7)
                    if (marketReceived.get(formatInput[0]) - 1 > -1) {
                        marketReceived.put(formatInput[0], marketReceived.get(formatInput[0]) - 1);

                        //aggiungo il materiale tolto dal deposito ai materiali disponibili
                        if (marketReceived.containsKey(formatInput[0]) && depositState.get(num - 1) != null)
                            marketReceived.put(depositState.get(num - 1).toString(), marketReceived.get(depositState.get(num - 1).toString()) + 1);

                        //aggiungo il materiale tolto dai materiali disponibili al deposito
                        if (marketReceived.containsKey(formatInput[0]))
                            depositState.set(num - 1, ResourceIcon.valueOf(formatInput[0]));
                    }
            }

            PrintDepositChoise(this.depositState, this.marketReceived);

        }

    }

    public void PrintDepositChoise(ArrayList<ResourceIcon> incoming, Map<String, Integer> acquired){

        System.out.printf("\u001B[2J\u001B[3J\u001B[H");
        System.out.println("\u001B[0;00m" + "                                                  ╔═══════════════════════╗ \n" +
                                            "                                                  ║ NEW DEPOSIT SELECTION ║ \n" +
                                            "                                                  ╚═══════════════════════╝ \n" +
                                            " ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ ");

        System.out.println("\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(0) + "                                                    " + "\u001B[0;00m" + "┃ \n" +
                "\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(1) + "                                                    " + "\u001B[0;00m" + "┃ \n" +
                "\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(2) + "                                                    " + "\u001B[0;00m" + "┃ \n" +
                "\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(3) + "                                                    " + "\u001B[0;00m" + "┃ \n" +
                "\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(4) + "                                                    " + "\u001B[0;00m" + "┃ \n" +
                "\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(5) + "                                                    " + "\u001B[0;00m" + "┃ \n" +
                "\u001B[0;00m" + " ┃                                                           (1)                                                         " + "\u001B[0;00m" + "┃ \n" +
                "\u001B[0;00m" + " ┃                                                                                                                       " + "\u001B[0;00m" + "┃ ");

        System.out.println("\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(0) + "             " + depositState.get(2).returnLine(0) + "                                       "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(1) + "             " + depositState.get(2).returnLine(1) + "                                       "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(2) + "             " + depositState.get(2).returnLine(2) + "                                       "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(3) + "             " + depositState.get(2).returnLine(3) + "                                       "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(4) + "             " + depositState.get(2).returnLine(4) + "                                       "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(5) + "             " + depositState.get(2).returnLine(5) + "                                       "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                                              (2)                       (3)                                            ┃ \n" +
                "\u001B[0;00m" + " ┃                                                                                                                       ┃ ");

        System.out.println("\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(0) + "             " + depositState.get(4).returnLine(0) + "             " + depositState.get(5).returnLine(0) + "                          "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(1) + "             " + depositState.get(4).returnLine(1) + "             " + depositState.get(5).returnLine(1) + "                          "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(2) + "             " + depositState.get(4).returnLine(2) + "             " + depositState.get(5).returnLine(2) + "                          "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(3) + "             " + depositState.get(4).returnLine(3) + "             " + depositState.get(5).returnLine(3) + "                          "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(4) + "             " + depositState.get(4).returnLine(4) + "             " + depositState.get(5).returnLine(4) + "                          "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(5) + "             " + depositState.get(4).returnLine(5) + "             " + depositState.get(5).returnLine(5) + "                          "+"\u001B[0;00m" +"┃ \n" +
                "\u001B[0;00m" + " ┃                                 (4)                       (5)                       (6)                               ┃ \n" +
                "\u001B[0;00m" + " ┃                                                                                                                       ┃ \n" +
                "\u001B[0;00m" + " ┃                                                                                                                       ┃ \n" +
                "\u001B[0;00m" + " ┃                                                                                                                       ┃ ");

        System.out.print("\u001B[0;00m" + " ┃                                                      ______________                                                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                       TO ORGANIZE                                                     ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                       Coin:     " + acquired.get(ResourceIcon.COIN.toString()) + "                                                     ┃ \n" +
                " ┃                                                       Servant:  " + acquired.get(ResourceIcon.SERVANT.toString()) + "                                                     ┃ \n" +
                " ┃                                                       Shield:   " + acquired.get(ResourceIcon.SHIELD.toString()) + "                                                     ┃ \n" +
                " ┃                                                       Stone:    " + acquired.get(ResourceIcon.STONE.toString()) + "                                                     ┃ \n" +
                " ┃                                                      ______________                                                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ \n" +
                "\n" +
                "                             Choose a material and a position to put into (material,position):\n" +
                "                                                 "+ "\u001B[92m"+"[type done when finished]                    \n" +
                "\n"+
                "                                                              " + "\u001B[0;00m");
    }
}
