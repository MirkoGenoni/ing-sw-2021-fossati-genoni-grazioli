package it.polimi.ingsw.Client.CLI.Views.MarketView;

import it.polimi.ingsw.Events.ServerToClient.SupportClass.MarketToClient;
import it.polimi.ingsw.Model.Market.Marble;
import it.polimi.ingsw.Model.Market.Market;

import java.util.ArrayList;
import java.util.Scanner;

public class MarketView {
    ArrayList<MarketIcon> marketState;
    MarketIcon outMarble;

    public MarketView(MarketToClient in){
        marketState = new ArrayList<>();

        for(Marble r: in.getGrid()){
            this.marketState.add(MarketIcon.valueOf(r.toString()));
        }

        this.outMarble = MarketIcon.valueOf(in.getOutMarble().toString());
    }


    public int launchChoiseView(){
        printMarketLineChoice();

        int num = -1;

        while(num <1 || num > 7) {
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();

            try{
                num = Integer.parseInt(input);
            } catch(IllegalArgumentException e) {
                continue;
            }
        }

        return num;
    }

    private void printMarketLineChoice(){
        System.out.println("                                                  ╔═══════════════════════╗                                                \n" +
                           "                                                  ║ MARKET LINE SELECTION ║                                                \n" +
                           "                                                  ╚═══════════════════════╝                                                \n" +
                           " ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ \n" +
                           " ┃                                                                                                                       ┃ \n" +
                           " ┃                       (4)                       (5)                       (6)                       (7)               ┃ \n" +
                           " ┃                                                                                                                       ┃ ");
                           printMarketLine(0,1);
                           printMarketLine(4, 2);
                           printMarketLine(8,3);

        System.out.println(" ┃                                                                                                                       ┃ \n" +
                " ┃                                                         " + outMarble.returnLine(0) + "                                                 ┃ \n" +
                " ┃                                                         " + outMarble.returnLine(1) + "                                                 ┃ \n" +
                " ┃                                                         " + outMarble.returnLine(2) + "                                                 ┃ \n" +
                " ┃                                         OUT MARBLE :    " + outMarble.returnLine(3) + "                                                 ┃ \n" +
                " ┃                                                         " + outMarble.returnLine(4) + "                                                 ┃ \n" +
                " ┃                                                         " + outMarble.returnLine(5) + "                                                 ┃ \n" +
                " ┃                                                         " + outMarble.returnLine(6) + "                                                 ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ ");

        System.out.print("\n" +
                "                                                      CHOOSE A LINE:\n" +
                "                                                             \n" +
                "                                                             ");

    }

    private void printMarketLine(int initialValue, int line){
        for(int i=0; i<7; i++) {
            if(i==3) {
                System.out.print(" ┃          ("+ line +")     " + marketState.get(initialValue).returnLine(i) +
                        "             " + marketState.get(initialValue + 1).returnLine(i) +
                        "             " + marketState.get(initialValue + 2).returnLine(i) +
                        "             " + marketState.get(initialValue + 3).returnLine(i) + "          ┃ \n");
            } else {
                System.out.print(" ┃                  " + marketState.get(initialValue).returnLine(i) +
                        "             " + marketState.get(initialValue + 1).returnLine(i) +
                        "             " + marketState.get(initialValue + 2).returnLine(i) +
                        "             " + marketState.get(initialValue + 3).returnLine(i) + "          ┃ \n");
            }
        }
        System.out.println(" ┃                                                                                                                       ┃ ");
    }
}
