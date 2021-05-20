package it.polimi.ingsw.Client.CLI.Views;

import java.util.Scanner;

public class NewTurnView {

    public String startTurnChoise(){
        while(true){
            printTurnChoise();
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            input = input.toLowerCase();

            switch(input) {
                case "market":
                    return "market";

                case"buydevelopment":
                    return "buydevelopment";

                case "usedevelopment":
                    return "usedevelopment";

                case "turn":
                    return "turn";
            }
        }
    }
    private void printTurnChoise(){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");

        System.out.println("\n" +
                           "                    ╔═════╗ ╔═╗ ╔═╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗     ╔═╗ ╔═╗ ╔═════╗ ╔═╗ ╔═╗ ╔═════╗                    \n" +
                           "                    ║ ╔═══╝ ║ ╚═╝ ║ ║ ╔═╗ ║ ║ ╔═╗ ║ ║ ╔═══╝ ║ ╔═══╝     ║ ╚═╝ ║ ║ ╔═╗ ║ ║ ║ ║ ║ ║  ══ ║                    \n" +
                           "                    ║ ║     ║ ╔═╗ ║ ║ ║ ║ ║ ║ ║ ║ ║ ║ ╚═══╗ ║ ╠═══      ╚═╗ ╔═╝ ║ ║ ║ ║ ║ ║ ║ ║ ║ ╔══╗║                    \n" +
                           "                    ║ ╚═══╗ ║ ║ ║ ║ ║ ╚═╝ ║ ║ ╚═╝ ║ ╠════ ║ ║ ╚═══╗       ║ ║   ║ ╚═╝ ║ ║ ╚═╝ ║ ║ ║  ║║                    \n" +
                           "                    ╚═════╝ ╚═╝ ╚═╝ ╚═════╝ ╚═════╝ ╚═════╝ ╚═════╝       ╚═╝   ╚═════╝ ╚═════╝ ╚═╝  ╚╝                    \n" +
                           "                                                                                                                           \n" +
                           "                                                                                                                           \n" +
                           "                                              ╔═════╗ ╔═╗ ╔═╗ ╔═════╗ ╔══╗╔═╗                                              \n" +
                           "                                              ╚═╗ ╔═╝ ║ ║ ║ ║ ║  ══ ║ ║  ╚╝ ║                                              \n" +
                           "                                                ║ ║   ║ ║ ║ ║ ║ ╔══╗║ ║ ╔╗  ║                                              \n" +
                           "                                                ║ ║   ║ ╚═╝ ║ ║ ║  ║║ ║ ║║  ║                                              \n" +
                           "                                                ╚═╝   ╚═════╝ ╚═╝  ╚╝ ╚═╝╚══╝                                              \n\n\n");
        System.out.println("                                                 ┏━━━━━━━━━━━━━━━━━━━━━━━┓                                                 \n" +
                           "                                                 ┃YOU CAN CHOOSE BETWEEN:┃                                                 \n" +
                           "                                                 ┗━━━━━━━━━━━━━━━━━━━━━━━┛\n" +
                           "\n" +
                           "                                                         -> MARKET                                                         \n" +
                           "                                               "+ "\u001B[92m" + "Get Resources from the market " + "\u001B[0m" + "                                               \n" +
                           "                                                   \n" +
                           "                                                     -> BUYDEVELOPMENT\n" +
                           "                                         "+ "\u001B[92m" + "Buy a development card from the available"  + "\u001B[0m\n" +
                "                                                  \n" +
                           "                                                   \n" +
                           "                                                     -> USEDEVELOPMENT \n" +
                           "                                        "+ "\u001B[92m" + "Use a development between the ones you have" + "\u001B[0m" + "                                                                    \n\n\n");
        System.out.print("                                       INSERT YOUR CHOICE: ");
    }
}
