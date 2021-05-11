package it.polimi.ingsw.Client.CLI.Views.LeaderCardView;

import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.Events.ServerToClient.SendLeaderCardToClient;

public class LeaderCardView {
    ArrayList<LeaderCardVisualization> cards;

    public LeaderCardView(ArrayList<SendLeaderCardToClient> leaderCardArray){

        cards = new ArrayList<>();

        for(int i = 0; i < leaderCardArray.size(); i++) {
            cards.add(new LeaderCardVisualization(leaderCardArray.get(i).getEffect(),
                    leaderCardArray.get(i).getVictoryPoint(),
                    leaderCardArray.get(i).getRequirement(),
                    leaderCardArray.get(i).getResourceType()));
        }

        if (leaderCardArray.size() < 2) {
            cards.add(new LeaderCardVisualization("nothing", 0, new ArrayList<>(), ""));
        }
    }

    public int[] StartInitialLeaderCardView(){
        while(true){
            printInitialCardRack(this.cards);

            String input = "";
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

                if(num1>0 && num1<5 && num2>0 && num2<5){
                    int[] send = new int[2];
                    send[0] = num1-1;
                    send[1] = num2-1;

                    return send;
                }
            }
        }
    }

    private void printInitialCardRack(ArrayList<LeaderCardVisualization> cards) {
        System.out.print("\u001B[2J\u001B[3J\u001B[H");

        System.out.print("\u001B[0;00m" + "                                                      ╔══════════════════════╗\n" +
                "                                                      ║ LEADER CARD RECEIVED ║\n" +
                "                                                      ╚══════════════════════╝\n" +
                "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                "┃                                                                                                                         ┃\n" +
                "┃                                                                                                                         ┃\n");


        for (int i = 0; i < 22; i++)
            System.out.println("┃ " + cards.get(0).printCard(i) + " " + cards.get(1).printCard(i) + " " + cards.get(2).printCard(i) + " " + cards.get(3).printCard(i) + " ┃");

        System.out.print("┃                                                                                                                         ┃\n" +
                "┃              -1-                           -2-                            -3-                           -4-             ┃\n" +
                "┃                                                                                                                         ┃\n" +
                "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n" +
                "\n" +
                "                               SELECT THE CARDS YOU WANT TO DISCARD (selection 1, selection2): \n" +
                "                                                     ");
    }

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

        while(true){
            printCardRack(this.cards, state);

            String input = "";
            Scanner in = new Scanner(System.in);
            input = in.nextLine();

            //separo le due stringhe usando la virgola come separatore
            String[] formatInput = input.split(",", 2);

            if(formatInput.length == 1)
                if(formatInput[0].equals("done"))
                    return send;

            if(formatInput.length == 2) {
                //elimino eventuali spazi iniziali dalle due stringhe
                formatInput[0] = formatInput[0].trim();
                formatInput[1] = formatInput[1].trim();

                int num1 = -1;

                try {
                    num1 = Integer.parseInt(formatInput[0]);
                } catch (IllegalArgumentException e) {
                    continue;
                }

                if (num1 < 1 || num1 > 2 || cards.get(num1-1).effect.equals("nothing")) {
                    continue;
                }

                switch (formatInput[1]) {
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

    private void printCardRack(ArrayList<LeaderCardVisualization> cards, String[][] state){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");

        System.out.print("\u001B[0;00m" + "                                                      ╔══════════════════════╗\n" +
                "                                                      ║ LEADER CARD RECEIVED ║\n" +
                "                                                      ╚══════════════════════╝\n" +
                "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                "┃                                                                                                                         ┃\n" +
                "┃                                                                                                                         ┃\n");

        for(int i=0; i<22; i++)
            System.out.println("┃                     " + cards.get(0).printCard(i) + "                     " + cards.get(1).printCard(i) + "                     ┃");

        System.out.print("┃                                                                                                                         ┃\n" +
                "┃                                  -1-                                               -2-                                  ┃\n" +
                "┃                              " + state[0][0] + "                                       " + state[1][0] + "                              ┃\n" +
                "┃                              " + state[0][1] + "                                       " + state[1][1] + "                              ┃\n" +
                "┃                              " + state[0][2] + "                                       " + state[1][2] + "                              ┃\n" +
                "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n" +
                "                                                                                                                           \n" +
                "                               SELECT THE CARDS YOU WANT TO ACTIVATE OR DISCARD                                            \n" +
                "                                                                                                                           \n" +
                "                         |        Type the number of card you want to act on        |                                      \n" +
                "                         |   followed by comma and the action you want to perform   |                                      \n" +
                "                         |     -discard-          -activate-          -nothing-     |                                      \n" +
                "                                                                                                                           \n" +
                "                                                 ");
    }
}

