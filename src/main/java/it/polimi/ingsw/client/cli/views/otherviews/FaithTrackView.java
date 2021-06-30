package it.polimi.ingsw.client.cli.views.otherviews;

import java.util.ArrayList;
import java.util.Scanner;

public class FaithTrackView {
    ArrayList<String> color;
    int position;

    public void draw(){
        String input = "";
        do {
            printFaithTrack();
            Scanner in = new Scanner(System.in);
            if (in.hasNext())
                input = in.nextLine();

        } while (!input.equals("quit"));
    }

    public FaithTrackView(ArrayList<Integer> popePass, int position){
        color = new ArrayList<>();

        for(Integer i: popePass)
            if(i!=0)
                color.add("\u001B[92m");
            else
                color.add("\u001B[31m");

        this.position = position;
    }

    public void printFaithTrack(){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
        System.out.print("                     ╔═════╗ ╔═════╗ ╔═╗ ╔═════╗ ╔═╗ ╔═╗       ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═╗╔══╗\n" +
                "                     ║  ══╦╝ ║ ╔═╗ ║ ║ ║ ╚═╗ ╔═╝ ║ ╚═╝ ║       ╚═╗ ╔═╝ ║  ══ ║ ║ ╔═╗ ║ ║ ╔═══╝ ║ ╚╝ ╔╝\n" +
                "                     ║ ╔══╝  ║ ╠═╣ ║ ║ ║   ║ ║   ║ ╔═╗ ║         ║ ║   ║ ╔══╗║ ║ ╠═╣ ║ ║ ║     ║ ╔╗ ╚╗\n" +
                "                     ║ ║     ║ ║ ║ ║ ║ ║   ║ ║   ║ ║ ║ ║         ║ ║   ║ ║  ║║ ║ ║ ║ ║ ║ ╚═══╗ ║ ║╚╗ ║\n" +
                "                     ╚═╝     ╚═╝ ╚═╝ ╚═╝   ╚═╝   ╚═╝ ╚═╝         ╚═╝   ╚═╝  ╚╝ ╚═╝ ╚═╝ ╚═════╝ ╚═╝ ╚═╝\n" +
                "\n" +
                "\n" +
                "\n" +
                "                                                          TOKENS:                                                          \n" +
                "\n" +
                "\n" +
                "                                             " + color.get(0) + "┌─────┐" + "\u001B[0;0m" + "      " + color.get(1) + "┌─────┐" + "\u001B[0;0m" + "      " + color.get(2) + "┌─────┐" + "\u001B[0;0m" + "\n" +
                "                                             " + color.get(0) + "│     │" + "\u001B[0;0m" + "      " + color.get(1) + "│     │" + "\u001B[0;0m" + "      " + color.get(2) + "│     │" + "\u001B[0;0m" + "\n" +
                "                                             " + color.get(0) + "│  2  │" + "\u001B[0;0m" + "      " + color.get(1) + "│  3  │" + "\u001B[0;0m" + "      " + color.get(2) + "│  4  │" + "\u001B[0;0m" + "\n" +
                "                                             " + color.get(0) + "│     │" + "\u001B[0;0m" + "      " + color.get(1) + "│     │" + "\u001B[0;0m" + "      " + color.get(2) + "│     │" + "\u001B[0;0m" + "\n" +
                "                                             " + color.get(0) + "└─────┘" + "\u001B[0;0m" + "      " + color.get(1) + "└─────┘" + "\u001B[0;0m" + "      " + color.get(2) + "└─────┘" + "\u001B[0;0m" + "\n" +
                "\n" +
                "\n" +
                "\n" +
                "                                                         POSITION:\n\n" +
                "                                    (and the relative position's winner point inside)\n\n\n" +
                "\n" +
                "                                                       ─ ─┬─────┬─ ─\n");
        if(calculateWinnerPoints(position).size()==1){
            System.out.print("                                                          │  0  │\n");
            System.out.print("                                                          │  " + calculateWinnerPoints(position).get(0) + "  │\n");
        } else if(calculateWinnerPoints(position).size()==2){
            System.out.print("                                                          │  " + calculateWinnerPoints(position).get(0) + "  │\n");
            System.out.print("                                                          │  " + calculateWinnerPoints(position).get(1) + "  │\n");
        }
        System.out.print("                                                       ─ ─┴─────┴─ ─\n");

        if(position<10)
            System.out.print("                                                           0" + this.position + "/24\n");
        else if(position>10)
            System.out.print("                                                           " + this.position + "/24\n");

        System.out.print("\n" +
                "                                                             \n" +
                "\n" +
                "\n" +
                "                                                ┌─────────────────────────┐                                                \n" +
                "                                                │ VATICAN REPORT SECTION: │\n" +
                "                                                │                         │\n" +
                "                                                │      " + "\u001B[92m" + "8    16    24" + "\u001B[0;0m" + "      │\n" +
                "                                                │                         │\n" +
                "                                                └─────────────────────────┘\n\n" +
                "                                           " + "\u001B[92m" + "type quit to return to view selection" + "\u001B[0;0m" + "\n\n" +
                "                                                     ");
    }

    private ArrayList<Integer> calculateWinnerPoints(int position){
        ArrayList<Integer> tmp = new ArrayList<>();

        if (position <= 2)
            tmp.add(0);
        else if (position <= 5)
            tmp.add(1);
        else if (position <= 8)
            tmp.add(2);
        else if (position <= 11)
            tmp.add(4);
        else if (position <= 14)
            tmp.add(6);
        else if (position <= 17)
            tmp.add(9);
        else if (position <= 20) {
            tmp.add(1);
            tmp.add(2);
        } else if (position <= 23) {
            tmp.add(1);
            tmp.add(6);
        } else {
            tmp.add(2);
            tmp.add(0);
        }

        return tmp;
    }
}
