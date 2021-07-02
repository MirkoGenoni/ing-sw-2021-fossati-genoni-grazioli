package it.polimi.ingsw.client.cli.views.otherviews;

import it.polimi.ingsw.client.cli.views.marketview.ResourceIcon;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles the visualisation of the initial resource selection
 *
 * @author Mirko Genoni
 */
public class InitialResourceView {
    int numResources;
    ArrayList<ResourceIcon> icons;

    /**
     * Constructor of the class initialises all the data structure
     * Creates all the visualization of the resources a player can choose
     * @param numResources is the number of resources given to the player
     */
    public InitialResourceView(int numResources){
        this.icons = new ArrayList<>();
        icons.add(ResourceIcon.SHIELD);
        icons.add(ResourceIcon.COIN);
        icons.add(ResourceIcon.SERVANT);
        icons.add(ResourceIcon.STONE);

        this.numResources = numResources;
    }

    /**
     * This method handles the input of the player
     * @return the resource choosen by the player
     */
    public Resource startChoosing(){

        Scanner in = new Scanner(System.in);
        String input;

        while(true){
            this.printChoise();
            input = in.nextLine();
            input = input.toUpperCase();

            switch(input){
                case "COIN":
                    return Resource.COIN;

                case "SHIELD":
                    return  Resource.SHIELD;

                case "SERVANT":
                    return Resource.SERVANT;

                case "STONE":
                    return Resource.STONE;
            }
        }
    }

    /**
     * Prints the visualization of the initial resource
     */
    public void printChoise(){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
        System.out.print("                                            \n" +
                "\n" +
                "                                             ┌───────────────────────────────┐\n" +
                "                                             │YOU RECEIVED INITIAL RESOURCES!│\n" +
                "                                             └───────────────────────────────┘\n" +
                "                                                  \n" +
                "                                             \n" +
                "                                                      CHOOSE BETWEEN:                 \n" +
                "                                     \n" +
                "                                     (you'll receive " + this.numResources + " resource of the type selected)                                    \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "             " + icons.get(0).returnLine(0) + "               " + icons.get(1).returnLine(0) + "               " + icons.get(2).returnLine(0) + "               " + icons.get(3).returnLine(0) + "\n" +
                "             " + icons.get(0).returnLine(1) + "               " + icons.get(1).returnLine(1) + "               " + icons.get(2).returnLine(1) + "               " + icons.get(3).returnLine(1) + "\n" +
                "             " + icons.get(0).returnLine(2) + "               " + icons.get(1).returnLine(2) + "               " + icons.get(2).returnLine(2) + "               " + icons.get(3).returnLine(2) + "\n" +
                "             " + icons.get(0).returnLine(3) + "               " + icons.get(1).returnLine(3) + "               " + icons.get(2).returnLine(3) + "               " + icons.get(3).returnLine(3) + "\n" +
                "             " + icons.get(0).returnLine(4) + "               " + icons.get(1).returnLine(4) + "               " + icons.get(2).returnLine(4) + "               " + icons.get(3).returnLine(4) + "\n" +
                "             " + icons.get(0).returnLine(5) + "               " + icons.get(1).returnLine(5) + "               " + icons.get(2).returnLine(5) + "               " + icons.get(3).returnLine(5) + "\n" +
                "                          \n" +
                "\n" +
                "\n" +
                "                                              \n" +
                "\n" +
                "\n" +
                "                                              \n" +
                "                                               -----------------------------\n" +
                "                                                           \n" +
                "                                                 (SHIELD)        (SERVANT)\n" +
                "                                                          \n" +
                "                                                  (COIN)          (STONE)\n" +
                "                                        \n" +
                "                                               ----------------------------             \n" +
                "                                                           \n" +
                "\n" +
                "\n" +
                "                                   \n" +
                "                                 \n" +
                "                                 TYPE THE RESOURCE YOU WANT: ");
    }
}
