package it.polimi.ingsw.Client.CLI.Views.ProductionView;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class BaseProduction {
    DevelopmentCardSymbols material1;
    DevelopmentCardSymbols material2;
    DevelopmentCardSymbols obtain;
    ArrayList<Resource> resources;

    public BaseProduction(){
        obtain = DevelopmentCardSymbols.ANYTHING;
        material1 = DevelopmentCardSymbols.ANYTHING;
        material2 = DevelopmentCardSymbols.ANYTHING;
    }

    public ArrayList<Resource> getResources() {
        return new ArrayList<>(this.resources);
    }

    public void startBaseProduction(){
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

                try {
                    this.resources.add(Resource.valueOf(input.toUpperCase()));
                    i++;
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }

            printBaseProductionSelection();

            for (int j = 0; j < this.resources.size(); j++) {
                printSelection(j + 1);
                System.out.println(resources.get(j).toString().toLowerCase());
                System.out.println("");
            }

            System.out.print("\n\n\n                                             ┌───────────────────────────────┐                                             \n"+
                             "                                             │ARE YOU SURE ABOUT YOUR CHOICE?│\n" +
                             "                                             └───────────────────────────────┘\n\n");
            System.out.print("                                                        type YES/NO          \n");
            System.out.print("                                                            ");

            while (true) {
                String input = in.nextLine();
                if (input.equals("YES")) {
                    valid = 1;
                    break;
                }
                if (input.equals("NO"))
                    break;
            }
        }
    }

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

    private void printSelection(int number){
        System.out.print("                                        SELECT RESOURCE"+ number +":  ");
    }
}
