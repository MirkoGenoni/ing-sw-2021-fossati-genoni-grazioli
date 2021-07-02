package it.polimi.ingsw.client.cli.views.marketview;

import it.polimi.ingsw.events.servertoclient.supportclass.MarketToClient;
import it.polimi.ingsw.model.market.Marble;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles the representation of the market
 *
 * @author Mirko Genoni
 */
public class MarketView {
    ArrayList<MarketIcon> marketState;
    MarketIcon outMarble;
    int choiseLine;
    boolean turnEnd;

    /**
     * Constructor of the class, initializes the visualization of the market
     *
     * @param in contains the state of the market
     */
    public MarketView(MarketToClient in){
        marketState = new ArrayList<>();

        for(Marble r: in.getGrid()){
            this.marketState.add(MarketIcon.valueOf(r.toString()));
        }

        this.outMarble = MarketIcon.valueOf(in.getOutMarble().toString());

        choiseLine = -1;
        turnEnd = true;
    }

    /**
     *
     * @return the number of line chosen
     */
    public int getChoiseLine(){
        return choiseLine;
    }

    /**
     *
     * @return false if the player quit before the end of the turn
     */
    public boolean isTurnEnd(){
        return turnEnd;
    }

    /**
     * Handles the visualization and the user input for the market
     * @param marketWhiteChange contains Strings that represent the resource changed from the white marble
     */
    public void launchChoiseView(ArrayList<String> marketWhiteChange){
        turnEnd = true;
        printMarketLineChoice(marketWhiteChange);

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

    /**
     * Prints the visualization for the market
     *
     * @param marketWhiteChange contains Strings that represent the resource changed from the white marble
     */
    private void printMarketLineChoice(ArrayList<String> marketWhiteChange){
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
                " ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ " );

        if(marketWhiteChange.size()!=0)
            System.out.print("   You got a market white change from the white marble you can obtain: ");

        if(marketWhiteChange.size() > 0)
            System.out.print(addColor(marketWhiteChange.get(0)));
        if(marketWhiteChange.size() > 1) {
            System.out.print(", ");
            System.out.print(addColor(marketWhiteChange.get(1)));
        }
        if(marketWhiteChange.size()>0)
            System.out.println("");

        System.out.print("\n" +
                "                                                      CHOOSE A LINE:\n" +
                "                                         " + "\u001B[92m" + "or type quit to return to turn selection\n\n" + "\u001B[0m" +
                "                                                             ");
    }

    /**
     * Prints the single line of the market
     * @param initialValue is the number of column to print
     * @param line is the number of line to print
     */
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

    /**
     * Add color to the writing for the market white change by the resource that can be transformed
     * the white marble into
     * @param material is the name of the resource
     * @return the name of the resource colored
     */
    private String addColor(String material){
        switch(material.toLowerCase()){
            case "stone":
                return("\u001B[37m" + "stone" + "\u001B[0;0m");
            case "coin":
                return("\u001B[93m" + "coin" + "\u001B[0;0m");
            case "servant":
                return("\u001B[35m" + "servant" + "\u001B[0;0m");
            case "shield":
                return("\u001B[36m" + "shield" + "\u001B[0;0m");
            default:
                return "";
        }
    }
}
