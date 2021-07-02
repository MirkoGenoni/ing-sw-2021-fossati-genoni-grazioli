package it.polimi.ingsw.client.cli.views.productionview;

import it.polimi.ingsw.client.cli.views.Messages;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the visualization of the additional production view for the user
 *
 * @author Mirko Genoni
 */
public class AdditionalProductionView {
    Messages noCards = new Messages("You got no active cards with additional production", true);
    ArrayList<AdditionalProductionVisualization> productions;
    ArrayList<Boolean> activation;
    ArrayList<Resource> requested;
    boolean turnEnd;

    /**
     * Constructor of the class initializes all the data structures
     * @param type represents the number of additional production possessed by the user
     */
    public AdditionalProductionView(ArrayList<String> type){
        activation = new ArrayList<>();
        activation.add(false);
        activation.add(false);
        requested = new ArrayList<>();
        productions = new ArrayList<>();
        turnEnd = true;

        switch(type.size()) {
            case 1:
                productions.add(new AdditionalProductionVisualization(type.get(0)));
                break;

            case 2:
                productions.add(new AdditionalProductionVisualization(type.get(0)));
                productions.add(new AdditionalProductionVisualization(type.get(1)));
                break;
        }
    }

    /**
     *
     * @return the boolean true if the production is activated
     */
    public ArrayList<Boolean> getActivation() {
        return new ArrayList<>(activation);
    }

    /**
     *
     * @return the material requested as result of the production
     */
    public ArrayList<Resource> getRequested() {
        return requested;
    }

    /**
     *
     * @return false if the user exited the production before the end of the turn
     */
    public boolean isTurnEnd() {
        return turnEnd;
    }

    /**
     * Starts the view and handles the user input for activation or not
     */
    public void startAdditionalProductionView(){
        switch (productions.size()){

            case 0:
                noCards.printMessage();
                activation.set(0, false);
                activation.set(1, false);
                break;

            case 1:
                String input = "";
                while(!input.equals("YES") && !input.equals("NO")){
                    printAdditionalProductionView(true);

                    Scanner in = new Scanner(System.in);
                    input = in.nextLine();
                    input = input.toUpperCase();

                    if(input.equals("YES")){
                        activation.set(0, true);
                        activation.set(1, false);
                        selectResourceProducted();
                    }
                    else if(input.equals("NO")){
                        activation.set(0, false);
                        activation.set(1, false);
                    }
                    else if(input.equals("QUIT")){
                        this.turnEnd = false;
                        break;
                    }
                }
                break;

            case 2:
                activation.set(0, false);
                activation.set(1, false);
                String input2;
                int num;

                while(true){
                    printAdditionalProductionView(true);
                    Scanner in = new Scanner(System.in);
                    input2 = in.nextLine();

                    String[] formatInput;
                    formatInput = input2.split(",", 2);
                    formatInput[0] = formatInput[0].trim();

                    if(formatInput.length==1){
                        if(formatInput[0].equals("done")) {
                            selectResourceProducted();
                            break;
                        }
                        if(formatInput[0].equals("quit")){
                            this.turnEnd = false;
                            break;
                        }
                    }
                    if(formatInput.length==2){
                        formatInput[1] = formatInput[1].trim();

                        try{
                            num= Integer.parseInt(formatInput[1]);
                        } catch (IllegalArgumentException e){
                            continue;
                        }

                        if(num-1 > -1 && num-1 <2) {
                            switch (formatInput[0]) {
                                case "activate":
                                    activation.set(num-1, true);
                                    break;
                                case "nothing":
                                    activation.set(num-1, false);
                                    break;
                            }
                        }
                    }
                    System.out.println(activation.toString());
                }
                break;

        }
    }

    /**
     * Prints the view used by the user
     * @param text is a boolean that activates or deactivates the text under the box of the view
     */
    private void printAdditionalProductionView(boolean text){

        String[] activated = new String[3];
        activated[0] = "┏━━━━━━━━━┓";
        activated[1] = "┃ACTIVATED┃";
        activated[2] = "┗━━━━━━━━━┛";

        switch(productions.size()){
            case 1:
                System.out.print("\u001B[2J\u001B[3J\u001B[H");
                System.out.println("                                                 ╔═══════════════════════╗\n" +
                        "                                                 ║ ADDITIONAL PRODUCTION ║\n" +
                        "                                                 ╚═══════════════════════╝                                                 \n" +
                        "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                        "┃                                                                                                                         ┃\n" +
                        "┃                                                                                                                         ┃");
                for(int i=0; i<25; i++)
                    System.out.print("┃                                             " + productions.get(0).returnLine(i) + "                                             ┃\n");
                System.out.print("┃                                                                                                                         ┃\n" +
                        "┃                                                                                                                         ┃\n" +
                        "┃                                                                                                                         ┃\n" +
                                 "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");

                if(text){
                    System.out.print("\n                                               " + "\u001B[92m" + "Type YES/NO to activate or not " + "\u001B[0;0m" + "                                               \n" +
                            "\n                                                            ");
                }
                break;
            case 2:
                System.out.print("\u001B[2J\u001B[3J\u001B[H");
                System.out.println("                                                      ╔═══════════════════════╗\n" +
                        "                                                      ║ ADDITIONAL PRODUCTION ║\n" +
                        "                                                      ╚═══════════════════════╝\n" +
                        "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                        "┃                                                                                                                         ┃");

                for(int i=0; i<25; i++)
                    System.out.println("┃           " + productions.get(0).returnLine(i) + "                                     " + productions.get(1).returnLine(i) + "           ┃");

                System.out.print("┃                         -1-                                                                 -2-                         ┃\n" );

                for(int k=0; k<3; k++) {
                    System.out.print("┃                     ");
                    if(activation.get(0)){
                        System.out.print(activated[k] + "                                                         ");
                    }
                    else{
                        System.out.print("                                                                    ");
                    }
                    if(activation.get(1)){
                        System.out.print(activated[k] + "                     ┃\n");
                    } else {
                        System.out.print("                                ┃\n");
                    }
                }


                System.out.print("┃                                                                                                                         ┃\n");
                System.out.print("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");

                if (text) {
                    System.out.print("                                      " + "\u001B[92m" + "SELECT THE PRODUCTION CARD YOU WANT TO ACTIVATE" + "\u001B[0;0m" + "                                     \n\n" +
                            "                                   |    Type nothing or activate, followed by comma    |                                           \n" +
                            "                                   |                and the card number                |                                           \n\n" +
                            "                                                      ");
                }
                break;
        }
    }

    /**
     * Asks the user the resource he wants to receive and handles the input
     */
    private void selectResourceProducted(){
        String input;
        String[] formatInput;
        int j = 0;

        for(int i=0; i<2; i++){
            if(activation.get(i)){
                j++;
            }
        }

        while(true){
            printAdditionalProductionView(false);
            System.out.print("\n                              " + "\u001B[92m" + "INSERT THE MATERIAL OR MATERIALS YOU WANT FROM THIS PRODUCTION:" + "\u001B[0;0m" + "                              \n" +
                               "                                       (if more than one separate them with a comma)                                       \n" +
                    "\n                                                         ");
            Scanner in = new Scanner(System.in);
            input = in.nextLine();

            formatInput = input.split(",", 2);

            if(formatInput.length==1 && j==1){
                try {
                    Resource send = Resource.valueOf(formatInput[0].toUpperCase());
                    this.requested.add(send);
                    break;
                }catch(IllegalArgumentException e){
                    continue;
                }
            }

            if(formatInput.length==2 && j==2){
                try{
                    Resource send1 = Resource.valueOf(formatInput[0].toUpperCase());
                    Resource send2 = Resource.valueOf(formatInput[1].toUpperCase());
                    this.requested.add(send1);
                    this.requested.add(send2);
                    break;
                } catch(IllegalArgumentException e){
                    continue;
                }
            }
        }
    }

}
