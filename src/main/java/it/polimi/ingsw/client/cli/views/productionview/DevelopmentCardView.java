package it.polimi.ingsw.client.cli.views.productionview;

import it.polimi.ingsw.client.cli.views.TotalResourceCounter;
import it.polimi.ingsw.events.servertoclient.supportclass.DevelopmentCardToClient;

import java.util.ArrayList;
import java.util.Scanner;

public class DevelopmentCardView {
    ArrayList<DevelopmentCardVisualization> cards = new ArrayList<>();
    DevelopmentCardVisualization boughtCard;
    int num;
    String color;
    ArrayList<Boolean> activation;
    ArrayList<Boolean> freeSpace = new ArrayList<>();
    String[][] state;
    TotalResourceCounter totalResourceCounter;

    boolean turnEnd;

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

    public DevelopmentCardView(DevelopmentCardToClient[][] input, TotalResourceCounter totalResourceCounter) {
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

        this.totalResourceCounter = totalResourceCounter;

        num = -1;
        color = "";
        turnEnd = false;
    }

    public DevelopmentCardView(ArrayList<DevelopmentCardToClient> input, TotalResourceCounter totalResourceCounter) {
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

        this.totalResourceCounter = totalResourceCounter;

        turnEnd = false;
    }

    public DevelopmentCardView(ArrayList<Boolean> freeSpace, ArrayList<DevelopmentCardToClient> state, DevelopmentCardVisualization input) {
        int i = 0;

        for (DevelopmentCardToClient r : state) {
            if (r != null) {
                cards.add(new DevelopmentCardVisualization(i+1,
                        r.getColor(),
                        r.getVictoryPoint(),
                        "LVL" + r.getLevel(),
                        r.getCost(),
                        r.getMaterialRequired(),
                        r.getProductionResult()));
            }
            else if(freeSpace.get(i)) {
                cards.add(new DevelopmentCardVisualization(0));
            } else {
                cards.add(new DevelopmentCardVisualization("CROSS"));

            }
            i++;
        }

        this.boughtCard = input;
        this.freeSpace.addAll(freeSpace);
        turnEnd = false;
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

    public DevelopmentCardVisualization getCard(int card){
        return this.cards.get(card);
    }

    public boolean isTurnEnd(){
        return this.turnEnd;
    }

    public void startCardForSaleSelection(ArrayList<String> costLess) {
        int currentView = 0;
        int tmp;
        turnEnd = false;

        num = -1;
        color = "";

        printDevelopmentCardGrid(currentView, "market", costLess);

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
                turnEnd = true;
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
                        break;
                    case "quit":
                        currentView = -1;
                        break;
                }

            }

            if(currentView == -1)
                break;

            printDevelopmentCardGrid(currentView, "market", costLess);
        }
    }

    public void startProductionCardBoardView() {
        turnEnd = false;
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
            printDevelopmentCardGrid(0, "activate",null);

            Scanner in = new Scanner(System.in);
            input = in.nextLine();
            String[] formatInput = input.split(",", 2);

            if (formatInput.length == 1) {
                if (formatInput[0].equals("done")) {
                    turnEnd = true;
                    break;
                } else if (formatInput[0].equals("quit")) {
                    turnEnd = false;
                    break;
                }
            }

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

    public int startSelectionNewCardSpace(){
        int currentView = 0;
        Scanner in = new Scanner(System.in);
        int tmp;
        String tmpS;

        while(true){
            printDevelopmentCardGrid(currentView, "insertion", null);

            try {
                tmpS  = in.nextLine();
                tmp = Integer.parseInt(tmpS);
            } catch(NumberFormatException e){
                continue;
            }

            if(tmp<1 || tmp>this.freeSpace.size())
                continue;

            if(this.freeSpace.get(tmp-1)) {
                if (this.cards.get(tmp - 1).getCardNumber() != 0) {
                    confirmBoughtSpaceSelection(this.boughtCard, this.cards.get(tmp - 1));
                    tmpS = in.nextLine();
                    tmpS = tmpS.toUpperCase();
                    if (tmpS.equals("YES"))
                        return tmp - 1;
                } else {
                    return tmp-1;
                }
            }
        }
    }

    public void draw(){
        String input = "";
        do {
            printDevelopmentCardGrid(0, "view", null);
            Scanner in = new Scanner(System.in);
            if (in.hasNext())
                input = in.nextLine();

        } while (!input.equals("quit"));
    }

    private void printDevelopmentCardGrid(int currentView, String type, ArrayList<String> costLess) {
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
        System.out.println("                                               ╔═══════════════════════════╗                                               \n" +
                        "                                               ║ DEVELOPMENT CARD RECEIVED ║                                               \n" +
                        "                                               ╚═══════════════════════════╝                                               \n" +
                        "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        for (int i = 0; i < 27; i++)
            System.out.println("┃    " + this.cards.get(currentView).printCard(i) + "          " + this.cards.get(currentView + 1).printCard(i) + "          " + this.cards.get(currentView + 2).printCard(i) + "    ┃");

        System.out.println("┃                                                                                                                         ┃\n" +
                    "┃                  -1-                                      -2-                                      -3-                  ┃");

        if (type.equals("market")) {

            System.out.print("┃                                                                                                                         ┃\n");

            for(int i=0; i<3; i++)
                System.out.println(this.totalResourceCounter.returnLine(i));

            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            System.out.print("                                              SELECT THE CARD YOU WANT TO BUY                                              \n");

            if(costLess.size()>0)
                System.out.print("                                           (you pay one less of " + addColor(costLess.get(0)));
            if (costLess.size()>1) {
                System.out.print(" and ");
                System.out.print(addColor(costLess.get(1)));
            }

            System.out.print(")");

            System.out.print("\n\n" +
                    "                                  | Insert a color (green, yellow, blue, purple) to see |                                  \n" +
                    "                                  |   other cards or a number to buy the card you see   |\n" +
                    "                                           " + "\u001B[92m" + "type quit to return to turn selection         \n\n" + "\u001B[0m" +
                    "                                                          ");
        } else if(type.equals("activate")) {

            if(!activation.get(0) && !activation.get(1) && !activation.get(2)){
                for(int i=0; i<3; i++)
                    System.out.println(this.totalResourceCounter.returnLine(i));
            } else {
                for (int j = 0; j < 3; j++)
                    System.out.println("┃              " + this.state[0][j] + "                              " + this.state[1][j] + "                              " + this.state[2][j] + "              ┃");
            }

            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");
            System.out.print("                                        SELECT THE DEVELOPMENT YOU WANT TO ACTIVATE         \n\n");
            System.out.print(  "                             |   Select the action you want to perform (activate, nothing)   |                             \n" +
                               "                             | followed by comma and the number of card you want to activate |\n" +
                               "                                " + "\u001B[92m" + "Type done when finished or quit to return to turn selection\n\n" + "\u001B[0m" +
                               "                                              ");

        } else if(type.equals("insertion")){
            System.out.print("┃                                                                                                                         ┃\n" +
                    "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n" +
                    "                                 INSERT THE POSITION WHERE YOU WANT TO PUT THE BOUGHT CARD                                 \n\n" +
                    "                                  " + "\u001B[92m" + "if you select a spot with a card, this will be replaced \n\n" + "\u001B[0m" +
                    "                                                 ");


        } else if(type.equals("view")){
            System.out.print("┃                                                                                                                         ┃\n" +
                    "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n\n" +
                    "                                  " + "\u001B[92m" + "type quit to return to view selection \n\n" + "\u001B[0m" +
                    "                                                 ");
        }
    }

    private void confirmBoughtSpaceSelection(DevelopmentCardVisualization bought, DevelopmentCardVisualization substitute){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
        System.out.print("                                                   ╔═══════════════════════════╗ \n" +
                "                                                   ║       ARE YOU SURE?       ║\n" +
                "                                                   ╚═══════════════════════════╝ \n" +
                "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                "┃                                                                                                                         ┃\n" +
                "┃                                                                                                                         ┃\n" +
                "┃                                                                                                                         ┃\n");
        for(int i=0; i<12; i++)
            System.out.print("┃            " + substitute.printCard(i) + "                                   " + bought.printCard(i) + "            ┃\n");

        System.out.print("┃            " + substitute.printCard(12) + "           ────────── ╲            " + bought.printCard(12) + "            ┃\n");
        System.out.print("┃            " + substitute.printCard(13) + "           ────────── ╱            " + bought.printCard(13) + "            ┃\n");

        for(int i=14; i<27; i++)
            System.out.print("┃            " + substitute.printCard(i) + "                                   " + bought.printCard(i) + "            ┃\n");

                System.out.print("┃                                                                                                                         ┃\n" +
                "┃                                                                                                                         ┃\n" +
                "┃                                                                                                                         ┃\n" +
                "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n\n" +
                "                                                      " + "\u001B[92m" + "type yes or no\n" + "\u001B[0m" +
                "                                                            ");
    }

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