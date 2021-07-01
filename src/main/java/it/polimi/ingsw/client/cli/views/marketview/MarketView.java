package it.polimi.ingsw.client.cli.views.marketview;

import it.polimi.ingsw.events.servertoclient.supportclass.MarketToClient;
import it.polimi.ingsw.model.market.Marble;

import java.util.ArrayList;
import java.util.Scanner;

public class MarketView {
    ArrayList<MarketIcon> marketState;
    MarketIcon outMarble;
    int choiseLine;
    boolean turnEnd;

    public MarketView(MarketToClient in){
        marketState = new ArrayList<>();

        for(Marble r: in.getGrid()){
            this.marketState.add(MarketIcon.valueOf(r.toString()));
        }

        this.outMarble = MarketIcon.valueOf(in.getOutMarble().toString());

        choiseLine = -1;
        turnEnd = true;
    }

    public int getChoiseLine(){
        return choiseLine;
    }

    public boolean isTurnEnd(){
        return turnEnd;
    }


    public void launchChoiseView(){
        turnEnd = true;
        printMarketLineChoice();

        int num = -1;

        while(num <1 || num > 7) {
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();

            if(input.equals("quit")) {
                turnEnd = false;
                break;
            }

            try{
                num = Integer.parseInt(input);
            } catch(IllegalArgumentException e) {
                continue;
            }
        }

        this.choiseLine = num;
    }

    private void printMarketLineChoice(){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
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
                "                                         " + "\u001B[92m" + "or type quit to return to turn selection\n\n" + "\u001B[0m" +
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
