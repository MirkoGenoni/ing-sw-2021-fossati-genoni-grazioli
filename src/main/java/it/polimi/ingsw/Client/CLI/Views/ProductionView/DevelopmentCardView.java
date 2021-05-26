package it.polimi.ingsw.Client.CLI.Views.ProductionView;

import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DevelopmentCardView {
    ArrayList<DevelopmentCardVisualization> cards = new ArrayList<>();
    int num;
    String color;
    ArrayList<Boolean> activation;
    String[][] state;

    /*public static void main(String[] args) {
        Map<Resource,Integer> cost = new HashMap<>();
        Map<Resource,Integer> requirements = new HashMap<>();

        int j=0;
        for(Resource r: Resource.values()){
            if (j < 3) {
                cost.put(r,5);
                requirements.put(r,5);
            }
            j++;
        }
        Map<ProductedMaterials,Integer> productionResult = new HashMap<>();

        int k=0;

        for(ProductedMaterials s: ProductedMaterials.values()){
            if (k < 3)
            productionResult.put(s,1);
            k++;
        }

        DevelopmentCardToClient prova = new DevelopmentCardToClient("prova1", "GREEN", 1, cost, 3, requirements, productionResult);

        ArrayList<DevelopmentCardToClient> in = new ArrayList<>();
        for(int i=0; i<3; i++)
            in.add(prova);

        DevelopmentCardView test = new DevelopmentCardView(in);
        test.startProductionCardBoardView();
        System.out.println(test.getActivation());
    }*/

    public DevelopmentCardView(DevelopmentCardToClient[][] input) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if(input[i][j]!=null) {
                    cards.add(new DevelopmentCardVisualization(i + j + 1,
                            input[i][j].getColor(),
                            input[i][j].getVictoryPoint(),
                            "LVL" + input[i][j].getLevel(),
                            input[i][j].getCost(),
                            input[i][j].getMaterialRequired(),
                            input[i][j].getProductionResult()));
                } else {
                    cards.add(new DevelopmentCardVisualization(0));
                }
            }
        }

        num = -1;
        color = "";
    }

    public DevelopmentCardView(ArrayList<DevelopmentCardToClient> input) {
        int i = 1;

        for (DevelopmentCardToClient r : input) {
            if (r != null) {
                cards.add(new DevelopmentCardVisualization(i,
                        r.getColor(),
                        r.getVictoryPoint(),
                        "LVL" + r.getLevel(),
                        r.getCost(),
                        r.getMaterialRequired(),
                        r.getProductionResult()));
                i++;
            } else {
                cards.add(new DevelopmentCardVisualization(0));
            }

            state = new String[3][3];
            activation = new ArrayList<>();
        }
    }

    public int getNum() {
        return num;
    }

    public String getColor() {
        return color;
    }

    public ArrayList<Boolean> getActivation() {
        return activation;
    }

    public void startCardForSaleSelection() {
        int currentView = 0;
        int tmp;

        num = -1;
        color = "";

        printDevelopmentCardGrid(currentView, true);

        String input = "";
        while (true) {
            Scanner in = new Scanner(System.in);
            input = in.nextLine();

            try {

                tmp = Integer.parseInt(input);
                String tmpc;

                if(tmp<1 || tmp>3)
                    continue;

                if (currentView < 3) {
                    tmpc = "green";
                } else if (currentView < 6) {
                    tmpc = "blue";
                } else if (currentView < 9) {
                    tmpc = "yellow";
                } else {
                    tmpc = "purple";
                }

                num = tmp;
                color = tmpc;
                break;

            } catch (IllegalArgumentException e) {

                switch (input) {
                    case "green":
                        currentView = 0;
                        break;
                    case "blue":
                        currentView = 3;
                        break;
                    case "yellow":
                        currentView = 6;
                        break;
                    case "purple":
                        currentView = 9;
                }

            }

            printDevelopmentCardGrid(currentView, true);
        }
    }

    public void startProductionCardBoardView() {
        int tmp = -1;
        activation.clear();

        String[] activated = new String[3];
        activated[0] = "┏━━━━━━━━━┓";
        activated[1] = "┃ACTIVATED┃";
        activated[2] = "┗━━━━━━━━━┛";

        String[] nothing = new String[3];
        nothing[0] = "           ";
        nothing[1] = "           ";
        nothing[2] = "           ";

        for (int i = 0; i < 3; i++)
            activation.add(false);

        for (int k = 0; k < 3; k++) {
            for (int j = 0; j < 3; j++) {
                this.state[k][j] = nothing[j];
            }
        }

        String input = "";

        while (true) {
            printDevelopmentCardGrid(0, false);

            Scanner in = new Scanner(System.in);
            input = in.nextLine();
            String[] formatInput = input.split(",", 2);

            if (formatInput.length == 1)
                if (formatInput[0].equals("done"))
                    break;

            if (formatInput.length == 2) {
                //elimino eventuali spazi iniziali dalle due stringhe
                formatInput[0] = formatInput[0].trim();
                formatInput[1] = formatInput[1].trim();

                try {
                    tmp = Integer.parseInt(formatInput[1]);
                } catch (IllegalArgumentException e) {
                    continue;
                }

                if (this.cards.get(tmp - 1).getCardNumber() != 0) {

                    switch (formatInput[0]) {
                        case "activate":
                            activation.set(tmp - 1, true);

                            for (int k = 0; k < 3; k++) {
                                for (int j = 0; j < 3; j++) {
                                    this.state[tmp - 1][j] = activated[j];
                                }
                            }

                            break;
                        case "nothing":
                            activation.set(tmp - 1, false);

                            for (int k = 0; k < 3; k++) {
                                for (int j = 0; j < 3; j++) {
                                    this.state[tmp - 1][j] = nothing[j];
                                }
                            }

                            break;
                    }
                }
            }
        }
    }

    private void printDevelopmentCardGrid(int currentView, boolean market) {
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
        System.out.println(
                "                                               ╔═══════════════════════════╗                                               \n" +
                        "                                               ║ DEVELOPMENT CARD RECEIVED ║                                               \n" +
                        "                                               ╚═══════════════════════════╝                                               \n" +
                        "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                        "┃                                                                                                                         ┃\n" +
                        "┃                                                                                                                         ┃");
        for (int i = 0; i < 27; i++)
            System.out.println("┃    " + this.cards.get(currentView).printCard(i) + "          " + this.cards.get(currentView + 1).printCard(i) + "          " + this.cards.get(currentView + 2).printCard(i) + "    ┃");
        if (market) {
            System.out.println("┃                                                                                                                         ┃\n" +
                    "┃                  -1-                                      -2-                                      -3-                  ┃\n" +
                    "┃                                                                                                                         ┃\n" +
                    "┃                                                                                                                         ┃\n" +
                    "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            System.out.print("\n                                              SELECT THE CARD YOU WANT TO BUY                                              \n\n" +
                    "                                       | Insert a color (green, yellow, blue, purple) to see |    \n" +
                    "                                       |   other cards or a number to buy the card you see   |    \n" +
                    "                                                                  \n" +
                    "                                                          ");
        } else {
            for (int j = 0; j < 3; j++)
                System.out.println("┃              " + this.state[0][j] + "                              " + this.state[1][j] + "                              " + this.state[2][j] + "              ┃");

            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");
            System.out.println("                                        SELECT THE DEVELOPMENT YOU WANT TO ACTIVATE         \n");
            System.out.print(  "                           |   Select the action you want to perform (activate, nothing)   |                             \n" +
                               "                           | followed by comma and the number of card you want to activate |\n" +
                               "                                                Type done when finished                     \n\n" +
                    "                                                                    ");
        }
    }
}