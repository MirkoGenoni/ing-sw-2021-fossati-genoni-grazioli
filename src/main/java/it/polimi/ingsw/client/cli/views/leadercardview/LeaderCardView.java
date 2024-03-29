package it.polimi.ingsw.client.cli.views.leadercardview;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import it.polimi.ingsw.events.servertoclient.supportclass.LeaderCardToClient;

/**
 * This class handle all the visualization regarding the leader card
 *
 * @author Mirko Genoni
 */
public class LeaderCardView {
    ArrayList<LeaderCardVisualization> cards;
    Map<String, Integer> totalResource;

    /**
     * This constructor initializes the class for the initial leadercard selection at the start of the game or when the
     * user can decide to activate or discard the cards
     * @param leaderCardArray contains the leader card received
     * @param totalResource contains the counter of all the development card and resources
     */
    public LeaderCardView(ArrayList<LeaderCardToClient> leaderCardArray, Map<String, Integer> totalResource){

        cards = new ArrayList<>();

        if(leaderCardArray!=null) {
            for (int i = 0; i < leaderCardArray.size(); i++) {
                if (leaderCardArray.get(i) != null)
                    cards.add(new LeaderCardVisualization(leaderCardArray.get(i).getEffect(),
                            leaderCardArray.get(i).getVictoryPoint(),
                            leaderCardArray.get(i).getRequirement(),
                            leaderCardArray.get(i).getResourceType()));
            }

            while (cards.size() < 2) {
                cards.add(new LeaderCardVisualization("nothing"));
            }

            if (totalResource != null && totalResource.size() > 0) {
                this.totalResource = totalResource;
            }
        }
    }

    /**
     * Handles the user input for the initial discard of cards by the player
     * @return the number of the two cards discarded
     */
    public int[] StartInitialLeaderCardView(){
        while(true){
            printInitialCardRack(this.cards);

            String input;
            Scanner in = new Scanner(System.in);
            input = in.nextLine();

            //separo le due stringhe usando la virgola come separatore
            String[] formatInput = input.split(",", 2);

            if(formatInput.length == 2){
                //elimino eventuali spazi iniziali dalle due stringhe
                formatInput[0] = formatInput[0].trim();
                formatInput[1] = formatInput[1].trim();

                int num1 = -1;
                int num2 = -1;

                try{
                    num1 = Integer.parseInt(formatInput[0]);
                    num2 = Integer.parseInt(formatInput[1]);
                } catch(IllegalArgumentException e){
                    System.out.println("Insert a number");
                }

                if(num1>0 && num1<5 && num2>0 && num2<5 && num1 != num2){
                    int[] send = new int[2];
                    send[0] = num1-1;
                    send[1] = num2-1;

                    return send;
                }
            }
        }
    }

    /**
     * Print the rack of cards containing four cards, two of them will be discarded
     * @param cards contains all the four cards received
     */
    private void printInitialCardRack(ArrayList<LeaderCardVisualization> cards) {
        System.out.print("\u001B[2J\u001B[3J\u001B[H");

        System.out.print("\u001B[0;00m" + "                                                      ╔══════════════════════╗\n" +
                "                                                      ║ LEADER CARD RECEIVED ║\n" +
                "                                                      ╚══════════════════════╝\n" +
                "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                "┃                                                                                                                         ┃\n" +
                "┃                                                                                                                         ┃\n" +
                "┃                                                                                                                         ┃\n");


        for (int i = 0; i < 22; i++)
            System.out.println("┃ " + cards.get(0).printCard(i) + " " + cards.get(1).printCard(i) + " " + cards.get(2).printCard(i) + " " + cards.get(3).printCard(i) + " ┃");

        System.out.print("┃                                                                                                                         ┃\n" +
                "┃              -1-                           -2-                            -3-                           -4-             ┃\n" +
                "┃                                                                                                                         ┃\n" +
                "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n" +
                "\n" +
                "                               SELECT THE CARDS YOU WANT TO DISCARD (selection 1, selection2): \n\n" +
                "                                                     ");
    }

    /**
     * Handles the visualization and the user input for the selection by the user for activating or discarding cards
     * @return an arraylist containing 0 if the user does nothing to the card, 1 if the user wants to activate the card,
     * 2 if the user wants to discrd the card
     */
    public ArrayList<Integer> StartCardView(){

        String[] activated = new String[3];
        activated[0] = "┏━━━━━━━━━┓";
        activated[1] = "┃ACTIVATED┃";
        activated[2] = "┗━━━━━━━━━┛";

        String[] discarded = new String[3];
        discarded[0] = "┏━━━━━━━━━┓";
        discarded[1] = "┃DISCARDED┃";
        discarded[2] = "┗━━━━━━━━━┛";

        String[] nothing =  new String[3];
        nothing[0] = "           ";
        nothing[1] = "           ";
        nothing[2] = "           ";

        String[][] state = new String[2][3];

        for(int i=0; i<2; i++){
            for(int j=0; j<3; j++){
                state[i][j] = nothing[j];
            }
        }

        ArrayList<Integer> send = new ArrayList<>();
        send.add(0);
        send.add(0);
        String currentState = "leaderCard";

        while(true){
            if(currentState.equals("leaderCard"))
                printCardRack(state, "activation");
            if(currentState.equals("totalResource"))
                printCurrentState();

            String input;
            Scanner in = new Scanner(System.in);
            input = in.nextLine();

            //separo le due stringhe usando la virgola come separatore
            String[] formatInput = input.split(",", 2);

            if(formatInput.length == 1) {
                if (formatInput[0].equals("done"))
                    return send;

                if(formatInput[0].equals("leaderCard"))
                    currentState = "leaderCard";

                if(formatInput[0].equals("totalResource"))
                    currentState = "totalResource";
            }

            if(formatInput.length == 2) {
                //elimino eventuali spazi iniziali dalle due stringhe
                formatInput[0] = formatInput[0].trim();
                formatInput[1] = formatInput[1].trim();

                int num1;

                try {
                    num1 = Integer.parseInt(formatInput[1]);
                } catch (IllegalArgumentException e) {
                    continue;
                }

                if (num1 < 1 || num1 > 2 || cards.get(num1-1).effect.equals("nothing")) {
                    continue;
                }

                switch (formatInput[0]) {
                    case "discard":
                        for (int k = 0; k < 3; k++)
                            state[num1 - 1][k] = discarded[k];
                        send.set(num1 - 1, 2);
                        break;

                    case "nothing":
                        for (int k = 0; k < 3; k++)
                            state[num1 - 1][k] = nothing[k];
                        send.set(num1 - 1, 0);
                        break;

                    case "activate":
                        for (int k = 0; k < 3; k++)
                            state[num1 - 1][k] = activated[k];
                        send.set(num1 - 1, 1);
                        break;
                }
            }
        }
    }

    /**
     * Draw the rack for the view turn
     */
    public void draw(){
        String input = "";
        while(true) {
            printCardRack(null, "view");
            Scanner in = new Scanner(System.in);
            if (in.hasNext())
                input = in.nextLine();

            if(input.equals("quit"))
                break;
        }
    }

    /**
     * Print the rack for the activation or discarding of leader card
     * @param state contains the state of the cards (nothing, activate, discard), it's initialized as all nothing
     * @param type distinguish between discard/activation and view
     */
    private void printCardRack(String[][] state, String type){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");

        System.out.print("\u001B[0;00m" + "                                                      ╔══════════════════════╗\n" +
                "                                                      ║ LEADER CARD RECEIVED ║\n" +
                "                                                      ╚══════════════════════╝\n" +
                "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                "┃                                                                                                                         ┃\n" +
                "┃                                                                                                                         ┃\n");

        for(int i=0; i<22; i++)
            System.out.println("┃                     " + this.cards.get(0).printCard(i) + "                     " + this.cards.get(1).printCard(i) + "                     ┃");

        System.out.print("┃                                                                                                                         ┃\n" +
                "┃                                  -1-                                               -2-                                  ┃\n");
        if(type.equals("activation"))
            System.out.print("┃                              " + state[0][0] + "                                       " + state[1][0] + "                              ┃\n" +
                "┃                              " + state[0][1] + "                                       " + state[1][1] + "                              ┃\n" +
                "┃                              " + state[0][2] + "                                       " + state[1][2] + "                              ┃\n" +
                "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n" +
                "                                                                                                                           \n" +
                "                                     SELECT THE CARDS YOU WANT TO ACTIVATE OR DISCARD                                      \n" +
                "                                                                                                                           \n" +
                "                               |       Type the action you want to perform followed       |                                \n" +
                "                               |    by comma and the number of card you want to act on    |                                \n" +
                "                               |      -discard-         -activate-         -nothing-      |                                \n" +
                "                                                                                                                    \n" +
                "                       " + "\u001B[92m" + "type totalResource to see the total amount of resources and development card"+ "\u001B[0;0m" + "\n\n" +
                "                                                 ");

        if(type.equals("view")){
            System.out.print("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n" +
                    "                                                                                                                           \n" +
                    "                                                " + "\u001B[92m" + "type quit to exit this view" + "\u001B[0;0m" + "                                                \n\n" +
                    "                                                           ");
        }
    }

    /**
     * Prints the visualization of the current counter of development card and resource
     */
    private void printCurrentState(){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");

        System.out.print("                                                     ╔═══════════════════╗\n" +
                "                                                     ║   TOTAL COUNTER   ║\n" +
                "                                                     ╚═══════════════════╝\n" +
                " ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                   DEVELOPMENT CARD ( "+ "\u001B[32m" +"█" + "\u001B[0m"+ " ):                                   DEVELOPMENT CARD ( "+ "\u001B[36m" +"█" + "\u001B[0m"+ " ):                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                      LEVEL 1: " + getTotal("GREEN level: 1") +"                                                LEVEL 1: " + getTotal("BLUE level: 1") +"                             ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                      LEVEL 2: " + getTotal("GREEN level: 2") +"                                                LEVEL 2: " + getTotal("BLUE level: 2") +"                             ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                      LEVEL 3: " + getTotal("GREEN level: 3") +"                                                LEVEL 3: " + getTotal("BLUE level: 3") +"                             ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                   DEVELOPMENT CARD ( "+ "\u001B[93m" +"█" + "\u001B[0m"+ " ):                                   DEVELOPMENT CARD ( "+ "\u001B[35m" +"█" + "\u001B[0m"+ " ):                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                      LEVEL 1: " + getTotal("YELLOW level: 1") +"                                                LEVEL 1: " + getTotal("PURPLE level: 1") +"                             ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                      LEVEL 2: " + getTotal("YELLOW level: 2") +"                                                LEVEL 2: " + getTotal("PURPLE level: 2") +"                             ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                      LEVEL 3: " + getTotal("YELLOW level: 3") +"                                                LEVEL 3: " + getTotal("PURPLE level: 3") +"                             ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃              ┌────────────────────────────────────────────────────────────────────────────────────────┐               ┃ \n" +
                " ┃              │                                                                                        │               ┃ \n" +
                " ┃              │ COIN: ");

        if(getTotal("COIN")<10)
            System.out.print("0" + getTotal("COIN") + "                SERVANT: ");
        else
            System.out.print(getTotal("COIN") + "                SHIELD: ");

        if(getTotal("COIN")<10)
            System.out.print("0" + getTotal("SERVANT") + "                SHIELD: ");
        else
            System.out.print(getTotal("SERVANT") + "                SHIELD: ");

        if(getTotal("COIN")<10)
            System.out.print("0" + getTotal("SHIELD") + "                STONE: ");
        else
            System.out.print(getTotal("SHIELD") + "                STONE: ");

        if(getTotal("COIN")<10)
            System.out.print("0" + getTotal("STONE") + " │               ┃ \n");
        else
            System.out.print(getTotal("STONE") + "  │               ┃ \n");

        System.out.print(" ┃              │                                                                                        │               ┃ \n" +
                " ┃              └────────────────────────────────────────────────────────────────────────────────────────┘               ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ \n\n" +
                "                                      THIS IS THE TOTAL OF YOUR RESOURCES AND DEVELOPMENT CARD \n" +
                "                                       " + "\u001B[92m" + "type leaderCard to return to the leader card selection " + "\u001B[0;0m" + "\n\n" +
                "                                                             ");
    }

    /**
     * Returns the number of resource if the map contains that type
     * @param in contains the name of the resource
     * @return the number of resources available or 0 if the key is not contained
     */
    private int getTotal(String in){
        if(totalResource.get(in)!=null)
            return totalResource.get(in);
        else
            return 0;
    }
}

