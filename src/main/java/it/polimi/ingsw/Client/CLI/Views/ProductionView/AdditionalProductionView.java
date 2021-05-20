package it.polimi.ingsw.Client.CLI.Views.ProductionView;

import it.polimi.ingsw.Client.CLI.Views.Messages;
import it.polimi.ingsw.Model.LeaderCard.AdditionalProduction;
import it.polimi.ingsw.Model.Resource.Resource;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Scanner;

public class AdditionalProductionView {
    Messages noCards = new Messages("You got no active cards with additional deposit");
    ArrayList<AdditionalProductionVisualization> productions;
    ArrayList<Boolean> activation;
    ArrayList<Resource> requested;



    public AdditionalProductionView(ArrayList<String> type){
        activation = new ArrayList<>();
        activation.add(false);
        activation.add(false);
        requested = new ArrayList<>();
        productions = new ArrayList<>();

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

    public ArrayList<Boolean> getActivation() {
        return new ArrayList<>(activation);
    }

    public ArrayList<Resource> getRequested() {
        return requested;
    }

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
                        "┃                                                                                                                         ┃");
                for(int i=0; i<19; i++)
                    System.out.print("┃                                             " + productions.get(0).returnLine(i) + "                                             ┃\n");
                System.out.print("┃                                                                                                                         ┃\n" +
                                 "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");

                if(text){
                    System.out.print("\n                                               Type YES/NO to activate or not                                               \n" +
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

                for(int i=0; i<19; i++)
                    System.out.println("┃                           " + productions.get(0).returnLine(i) + "     " + productions.get(1).returnLine(i) + "                           ┃");

                System.out.print("┃                                         -1-                                 -2-                                         ┃\n" );

                for(int k=0; k<3; k++) {
                    System.out.print("┃                                     ");
                    if(activation.get(0)){
                        System.out.print(activated[k] + "                         ");
                    }
                    else{
                        System.out.print("                                    ");
                    }
                    if(activation.get(1)){
                        System.out.print(activated[k] + "                                     ┃\n");
                    } else {
                        System.out.print("                                                ┃\n");
                    }
                }


                System.out.print("┃                                                                                                                         ┃\n");
                System.out.print("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");

                if (text) {
                    System.out.print("                                      SELECT THE PRODUCTION CARD YOU WANT TO ACTIVATE                                     \n\n" +
                            "                                            |    Type nothing or activate, followed by comma    |                                  \n" +
                            "                                            |                and the card number                |                                  \n\n" +
                            "                                                      ");
                }
                break;
        }
    }

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
            System.out.print("\n                              INSERT THE MATERIAL OR MATERIALS YOU WANT FROM THIS PRODUCTION:                              \n" +
                               "                                       (if more than one separate them with a comma)                                       \n" +
                    "\n                                                                ");
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
