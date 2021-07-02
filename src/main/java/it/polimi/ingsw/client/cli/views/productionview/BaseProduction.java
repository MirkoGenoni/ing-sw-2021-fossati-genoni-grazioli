package it.polimi.ingsw.client.cli.views.productionview;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the visualization of the base production
 *
 * @author Mirko Genoni
 */
public class BaseProduction {
    DevelopmentCardSymbols material1;
    DevelopmentCardSymbols material2;
    DevelopmentCardSymbols obtain;
    ArrayList<Resource> resources;
    boolean isTurnEnd;

    /**
     * The constructor of the class initializes all the data structure
     */
    public BaseProduction(){
        obtain = DevelopmentCardSymbols.ANYTHING;
        material1 = DevelopmentCardSymbols.ANYTHING;
        material2 = DevelopmentCardSymbols.ANYTHING;
        isTurnEnd = false;
    }

    /**
     * Return the resource asked for and given
     * @return the resource asked for and given
     */
    public ArrayList<Resource> getResources() {
        return new ArrayList<>(this.resources);
    }

    /**
     * returns false if the user quitted before ending the turn
     * @return false if the user quitted before ending the turn
     */
    public boolean isTurnEnd() {
        return isTurnEnd;
    }

    /**
     * Starts the representation and gets the user input
     */
    public void startBaseProduction(){
        isTurnEnd = false;
        int valid = 0;

        while(valid==0) {

            int i = 1;
            resources = new ArrayList<>();
            Scanner in = new Scanner(System.in);

            while (i < 4) {
                printBaseProductionSelection();

                for (int j = 0; j < this.resources.size(); j++) {
                    printSelection(j + 1);
                    System.out.println(resources.get(j).toString().toLowerCase());
                    System.out.println("");
                }

                printSelection(i);

                String input = in.nextLine();

                if(input.equals("quit"))
                    break;

                try {
                    this.resources.add(Resource.valueOf(input.toUpperCase()));
                    i++;
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }

            while (true) {
            printBaseProductionSelection();

            for (int j = 0; j < this.resources.size(); j++) {
                printSelection(j + 1);
                System.out.println(resources.get(j).toString().toLowerCase());
                System.out.println("");
            }

            System.out.print("\n\n\n                                             ┌───────────────────────────────┐                                             \n"+
                             "                                             │ARE YOU SURE ABOUT YOUR CHOICE?│\n" +
                             "                                             └───────────────────────────────┘\n\n");
            System.out.print("                                                        type YES/NO          \n" +
                             "                                           " + "\u001B[92m" + "type quit to return to turn selection \n"+ "\u001B[0m");
            System.out.print("                                                            ");


            String input = in.nextLine();
            if (input.toUpperCase().equals("YES")) {
                valid = 1;
                isTurnEnd = true;
                break;
            }
            if (input.toUpperCase().equals("NO"))
                break;
            if(input.toUpperCase().equals("QUIT")) {
                valid = -1;
                isTurnEnd = false;
                break;
            }
            }
        }
    }

    /**
     * Prints the page of selection for the user
     */
    private void printBaseProductionSelection(){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");

        System.out.println("                                            \n" +
                "\n" +
                "                                                 ╔═══════════════════════╗                                                 \n" +
                "                                                 ║    BASE PRODUCTION    ║                                                 \n" +
                "                                                 ╚═══════════════════════╝                                                 \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "                                          ┌───┐           ┌───┐              ┌───┐\n" +
                "                                          │ ? │     +     │ ? │     --->     │ ? │\n" +
                "                                          └───┘           └───┘              └───┘\n");

        if(this.resources.size()<1) {
            System.out.print("\u001B[31m" + "                                        RESOURCE1       " + "\u001B[0m");
            System.out.print("\u001B[31m" + "RESOURCE2          " + "\u001B[0m");
            System.out.print("\u001B[31m" + "RESOURCE3\n" +
                    "\n" +
                    "\n" +
                    "\n" + "\u001B[0m");
        }

        else if(this.resources.size()<2) {
            System.out.print("\u001B[92m" + "                                        RESOURCE1       " + "\u001B[0m");
            System.out.print("\u001B[31m" + "RESOURCE2          " + "\u001B[0m");
            System.out.print("\u001B[31m" + "RESOURCE3\n" +
                    "\n" +
                    "\n" +
                    "\n" + "\u001B[0m");
        }

        else if(this.resources.size()<3) {
            System.out.print("\u001B[92m" + "                                        RESOURCE1       " + "\u001B[0m");
            System.out.print("\u001B[92m" + "RESOURCE2          " + "\u001B[0m");
            System.out.print("\u001B[31m" + "RESOURCE3\n" +
                    "\n" +
                    "\n" +
                    "\n" + "\u001B[0m");
        }

        else if(this.resources.size() == 3){
            System.out.print("\u001B[92m" + "                                        RESOURCE1       " + "\u001B[0m");
            System.out.print("\u001B[92m" + "RESOURCE2          " + "\u001B[0m");
            System.out.print("\u001B[92m" + "RESOURCE3\n" +
                    "\n" +
                    "\n" +
                    "\n" + "\u001B[0m");
        }
    }

    /**
     * Asks for the resource to give to the base production
     * @param number is the number of resource asked
     */
    private void printSelection(int number){
        System.out.print("                                        SELECT RESOURCE"+ number +":  ");
    }
}
