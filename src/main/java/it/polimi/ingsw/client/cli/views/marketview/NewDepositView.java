package it.polimi.ingsw.client.cli.views.marketview;

import it.polimi.ingsw.client.cli.views.Messages;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class handles the visualization of a new deposit state by the user
 *
 * @author Mirko Genoni
 */
public class NewDepositView {

    ArrayList<ResourceIcon> depositState;
    ArrayList<ResourceIcon> additionalDepositState;
    ArrayList<ResourceIcon> type;
    Map<String, Integer> informationReceived;
    boolean additionalDeposit;

    /**
     * This constructor initializes the class for the phase after the market
     * @param depositState is the current deposit of the state
     * @param informationReceived are the material received by the market
     * @param additionalDeposit is the boolean that indicates if there ia an additional deposit activated
     * @param type are the type that the additional deposits can contain
     * @param additionalDepositState is the current state of the additional deposit
     */
    public NewDepositView(ArrayList<Resource> depositState, ArrayList<Resource> informationReceived, boolean additionalDeposit, ArrayList<Resource> type, ArrayList<Resource> additionalDepositState) {

        this.depositState = new ArrayList<>();
        this.additionalDepositState = new ArrayList<>();
        this.type = new ArrayList<>();

        for (int i = 0; i < depositState.size(); i++) {
            if (depositState.get(i) == null)
                this.depositState.add(ResourceIcon.NOTHING);
            else {
                this.depositState.add(ResourceIcon.valueOf(depositState.get(i).toString()));
            }
        }

        this.informationReceived = new HashMap<>();

        for (ResourceIcon r : ResourceIcon.values())
            this.informationReceived.put(r.toString(), 0);

        this.informationReceived.put(ResourceIcon.NOTHING.toString(), 6);

        for (Resource r : informationReceived) {
            this.informationReceived.put(r.toString(), this.informationReceived.get(r.toString()) + 1);
        }

        this.additionalDeposit = additionalDeposit;

        if (additionalDeposit) {
            for (Resource r : type) {
                this.type.add(ResourceIcon.valueOf(r.toString()));
                this.type.add(ResourceIcon.valueOf(r.toString()));
            }

            for (Resource t : additionalDepositState) {
                if (t != null)
                    this.additionalDepositState.add(ResourceIcon.valueOf(t.toString()));
                else
                    this.additionalDepositState.add(ResourceIcon.NOTHING);
            }
        }
    }


    /**
     * this constructor initializes the class for the view turn
     */
    /**
     * This constructor initializes the class for the view turn
     * @param depositState is the current deposit of the state
     * @param strongbox is the current strongbox state
     * @param additionalDepositType are the type that the additional deposits can contain
     * @param additionalDepositState is the current state of the additional deposit
     */
    public NewDepositView(ArrayList<Resource> depositState, Map<Resource, Integer> strongbox, ArrayList<Resource> additionalDepositType, ArrayList<Resource> additionalDepositState){
        this.depositState = new ArrayList<>();
        this.additionalDepositState = new ArrayList<>();
        this.type = new ArrayList<>();

        for (int i = 0; i < depositState.size(); i++) {
            if (depositState.get(i) == null)
                this.depositState.add(ResourceIcon.NOTHING);
            else {
                this.depositState.add(ResourceIcon.valueOf(depositState.get(i).toString()));
            }
        }

        this.informationReceived = new HashMap<>();

        for (ResourceIcon r : ResourceIcon.values())
            this.informationReceived.put(r.toString(), 0);

        for (Resource r : Resource.values()) {
            if(strongbox.containsKey(r))
                this.informationReceived.put(r.toString(), strongbox.get(r));
        }

        if(additionalDepositState!=null){
            this.additionalDeposit = true;

            for (Resource r : additionalDepositType) {
                this.type.add(ResourceIcon.valueOf(r.toString()));
                this.type.add(ResourceIcon.valueOf(r.toString()));
            }

            for (Resource t : additionalDepositState) {
                if (t != null)
                    this.additionalDepositState.add(ResourceIcon.valueOf(t.toString()));
                else
                    this.additionalDepositState.add(ResourceIcon.NOTHING);
            }
        }
    }

    /**
     *
     * @return the deposit state after the user input
     */
    public ArrayList<Resource> getDepositState() {

        ArrayList<Resource> tmp = new ArrayList<>();

        for (int i = 0; i < depositState.size(); i++) {
            if (depositState.get(i).toString().equals(ResourceIcon.NOTHING.toString()))
                tmp.add(null);
            else {
                tmp.add(Resource.valueOf(depositState.get(i).toString()));
            }
        }

        return tmp;

    }

    /**
     *
     * @return the resource not inserted into the deposit
     */
    public int getMarketReceived() {
        int num = 0;

        for (ResourceIcon r : ResourceIcon.values())
            if (!r.equals(ResourceIcon.NOTHING))
                num = num + informationReceived.get(r.toString());

        return num;
    }

    /**
     *
     * @return the state of the additional deposit after the user input
     */
    public ArrayList<Resource> getAdditionalDepositState() {
        ArrayList<Resource> tmp = new ArrayList<>();

        for (ResourceIcon r : this.additionalDepositState) {
            if (r.toString().equals("NOTHING")) {
                tmp.add(null);
            } else {
                tmp.add(Resource.valueOf(r.toString()));
            }
        }

        return tmp;
    }

    /**
     *
     * @return true if there are additional deposit activated
     */
    public boolean isAdditionalDeposit() {
        return additionalDeposit;
    }

    /**
     * Handles the visualization and the user input for the selection of a new deposit
     */
    public void LaunchView() {
        String input = "";

        String state = "deposit";

        while (true) {

            if (state.equals("deposit")) {
                printDepositChoise("reorganize");
            }
            if (state.equals("additionalDeposit")) {
                printAdditionalDeposit("reorganize");
            }

            Scanner in = new Scanner(System.in);
            input = in.nextLine();

            //separo le due stringhe usando la virgola come separatore
            String[] formatInput = input.split(",", 2);

            //verifico che il giocatore non abbia inserito done
            if (formatInput.length == 1) {
                if (formatInput[0].equals("additionalDeposit") && this.additionalDepositState.size() != 0) {
                    state = "additionalDeposit";
                }
                else if (formatInput[0].equals("additionalDeposit")) {
                    Messages message = new Messages("YOU DON'T HAVE ANY ADDITIONAL DEPOSIT", true);
                    message.printMessage();
                }
                else if (formatInput[0].equals("deposit")) {
                    state = "deposit";
                }
                else if (formatInput[0].equals("done")) {
                    break;
                } else {
                    Messages message = new Messages("INSERT A CORRECT INPUT PLEASE", true);
                    message.printMessage();
                    continue;
                }
            }

            if (formatInput.length == 2) {
                //elimino eventuali spazi iniziali dalle due stringhe
                formatInput[0] = formatInput[0].trim();

                int num = -1;
                formatInput[1] = formatInput[1].trim();

                //trasformo la seconda stringa in un numero e verifico che lo sia
                try {
                    num = Integer.parseInt(formatInput[1]);
                } catch (IllegalArgumentException e) {
                    Messages message = new Messages("INSERT A CORRECT INPUT PLEASE", true);
                    message.printMessage();
                    continue;
                }

                formatInput[0] = formatInput[0].toUpperCase();

                if (state.equals("deposit")) {
                    //tolgo il materiale dalle risorse ottenute dal market da inserire in deposito
                    if (informationReceived.containsKey(formatInput[0]) && num > 0 && num < 7)
                        resourceHandle(this.depositState, formatInput[0], num);
                }

                if (state.equals("additionalDeposit")) {

                    if((this.type.size()==4 && (num<1 || num>5)) || (type.size()==2 && (num<1 || num>2))){
                        Messages message = new Messages("INSERT A CORRECT INPUT PLEASE", true);
                        message.printMessage();
                        continue;
                    }

                    if ((formatInput[0].equals("NOTHING") || formatInput[0].equals(type.get(num - 1).toString()))
                            && informationReceived.containsKey(formatInput[0]) && num > 0 && num < additionalDepositState.size() + 1)
                        resourceHandle(this.additionalDepositState, formatInput[0], num);
                }
            }

        }

    }

    /**
     * Handles visualization and user input for the view turn
     */
    public void draw(){
        String state = "deposit";
        Scanner in = new Scanner(System.in);
        String input;

        do {
            if (state.equals("deposit"))
                this.printDepositChoise("view");
            if (state.equals("additionalDeposit"))
                this.printAdditionalDeposit("view");

            input = in.nextLine();

            switch (input.toLowerCase()) {
                case "deposit":
                    state = "deposit";
                    break;
                case "additionaldeposit":
                    if(this.additionalDepositState!=null && this.additionalDepositState.size()!=0)
                        state = "additionalDeposit";
                    break;
                case "quit":
                    state = "quit";
            }

        } while (!state.equals("quit"));
    }

    /**
     *
     * @param deposit contains the current deposit state
     * @param formatInput contains the resource from the resources inside the one received from the market
     * @param num contains the position of the deposit the player wants to insert the resources into
     */
    private void resourceHandle(ArrayList<ResourceIcon> deposit, String formatInput, int num){
        if (informationReceived.get(formatInput) - 1 > -1) {
            informationReceived.put(formatInput, informationReceived.get(formatInput) - 1);

            //aggiungo il materiale tolto dal deposito ai materiali disponibili
            if (informationReceived.containsKey(formatInput) && deposit.get(num - 1) != null)
                informationReceived.put(deposit.get(num - 1).toString(), informationReceived.get(deposit.get(num - 1).toString()) + 1);

            //aggiungo il materiale tolto dai materiali disponibili al deposito
            if (informationReceived.containsKey(formatInput))
                deposit.set(num - 1, ResourceIcon.valueOf(formatInput));
        } else {
            Messages message = new Messages("INSERT A CORRECT INPUT PLEASE", true);
            message.printMessage();
        }
    }

    /**
     * Print the visualization for the new deposit by the player
     * @param context contains a String that tells if the print is for the reorganize of the deposit or for the view
     */
    private void printDepositChoise(String context) {

        System.out.print("\u001B[2J\u001B[3J\u001B[H");
        System.out.println("\u001B[0;00m" + "                                                  ╔═══════════════════════╗ \n" +
                "                                                  ║ NEW DEPOSIT SELECTION ║ \n" +
                "                                                  ╚═══════════════════════╝ \n" +
                " ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ ");

        System.out.println("\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(0) + "                                                    ┃ \n" +
                "\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(1) + "                                                    ┃ \n" +
                "\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(2) + "                                                    ┃ \n" +
                "\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(3) + "                                                    ┃ \n" +
                "\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(4) + "                                                    ┃ \n" +
                "\u001B[0;00m" + " ┃                                                      " + depositState.get(0).returnLine(5) + "                                                    ┃ \n" +
                "\u001B[0;00m" + " ┃                                                           (1)                                                         ┃ \n" +
                "\u001B[0;00m" + " ┃                                                                                                                       ┃ ");

        System.out.println("\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(0) + "             " + depositState.get(2).returnLine(0) + "                                       ┃ \n" +
                "\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(1) + "             " + depositState.get(2).returnLine(1) + "                                       ┃ \n" +
                "\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(2) + "             " + depositState.get(2).returnLine(2) + "                                       ┃ \n" +
                "\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(3) + "             " + depositState.get(2).returnLine(3) + "                                       ┃ \n" +
                "\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(4) + "             " + depositState.get(2).returnLine(4) + "                                       ┃ \n" +
                "\u001B[0;00m" + " ┃                                         " + depositState.get(1).returnLine(5) + "             " + depositState.get(2).returnLine(5) + "                                       ┃ \n" +
                "\u001B[0;00m" + " ┃                                              (2)                       (3)                                            ┃ \n" +
                "\u001B[0;00m" + " ┃                                                                                                                       ┃ ");

        System.out.println("\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(0) + "             " + depositState.get(4).returnLine(0) + "             " + depositState.get(5).returnLine(0) + "                          ┃ \n" +
                "\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(1) + "             " + depositState.get(4).returnLine(1) + "             " + depositState.get(5).returnLine(1) + "                          ┃ \n" +
                "\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(2) + "             " + depositState.get(4).returnLine(2) + "             " + depositState.get(5).returnLine(2) + "                          ┃ \n" +
                "\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(3) + "             " + depositState.get(4).returnLine(3) + "             " + depositState.get(5).returnLine(3) + "                          ┃ \n" +
                "\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(4) + "             " + depositState.get(4).returnLine(4) + "             " + depositState.get(5).returnLine(4) + "                          ┃ \n" +
                "\u001B[0;00m" + " ┃                            " + depositState.get(3).returnLine(5) + "             " + depositState.get(4).returnLine(5) + "             " + depositState.get(5).returnLine(5) + "                          ┃ \n" +
                "\u001B[0;00m" + " ┃                                 (4)                       (5)                       (6)                               ┃ \n" +
                "\u001B[0;00m" + " ┃                                                                                                                       ┃ \n" +
                "\u001B[0;00m" + " ┃                                                                                                                       ┃ \n" +
                "\u001B[0;00m" + " ┃                                                                                                                       ┃ ");

        System.out.print("\u001B[0;00m" + " ┃                                                      ______________                                                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                       ");

        if(context.equals("reorganize"))
            System.out.print("TO ORGANIZE:");
        if(context.equals("view"))
            System.out.print(" STRONGBOX: ");

        System.out.print("                                                    ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                       Coin:     ");

        if(informationReceived.get(ResourceIcon.COIN.toString())<10)
            System.out.print("0" + informationReceived.get(ResourceIcon.COIN.toString()));
        else
            System.out.print(informationReceived.get(ResourceIcon.COIN.toString()));
        System.out.print("                                                    ┃\n" +
                " ┃                                                       Servant:  ");
        if(informationReceived.get(ResourceIcon.SERVANT.toString())<10)
            System.out.print("0" + informationReceived.get(ResourceIcon.SERVANT.toString()));
        else
            System.out.print(informationReceived.get(ResourceIcon.SERVANT.toString()));

        System.out.print("                                                    ┃\n" +
                " ┃                                                       Shield:   ");

        if(informationReceived.get(ResourceIcon.SHIELD.toString())<10)
            System.out.print("0" + informationReceived.get(ResourceIcon.SHIELD.toString()));
        else
            System.out.print(informationReceived.get(ResourceIcon.SHIELD.toString()));

        System.out.print("                                                    ┃\n" +
                " ┃                                                       Stone:    ");
        if(informationReceived.get(ResourceIcon.STONE.toString())<10)
            System.out.print("0" + informationReceived.get(ResourceIcon.STONE.toString()));
        else
            System.out.print(informationReceived.get(ResourceIcon.STONE.toString()));

        System.out.print("                                                    ┃\n" +
                " ┃                                                      ______________                                                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ \n" +
                "\n");
        if(context.equals("reorganize"))
            System.out.print("                             Choose a material and a position to put into (material,position):                             \n" +
                "                              " + "\u001B[92m" + "[type done when finished or additionalDeposit to change view]                              \n" +
                "\n" +
                "                                                              " + "\u001B[0;00m");
        else if(context.equals("view"))
            System.out.print("\u001B[92m" + "                                                Type quit to exit this view                                                " + "\u001B[0;0m" +"\n" +
                    "                                                              ");    }

    /**
     * Prints the visualization for the additional deposits
     */
    public void printAdditionalDeposit(String context) {
        System.out.print("\u001B[2J\u001B[3J\u001B[H");

        System.out.println("\u001B[0;00m" +
                "                                                  ╔═══════════════════════╗ \n" +
                "                                                  ║  ADDITIONAL DEPOSITS  ║ \n" +
                "                                                  ╚═══════════════════════╝ \n" +
                " ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ ");

        if (additionalDepositState.size() == 2 || additionalDepositState.size() == 4) {
            System.out.print(" ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(0).returnLine(0) + "                          " + this.additionalDepositState.get(1).returnLine(0) + "                          ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(0).returnLine(1) + "                          " + this.additionalDepositState.get(1).returnLine(1) + "                          ┃ \n" +
                    " ┃        SPECIAL ABILITY                  " + this.additionalDepositState.get(0).returnLine(2) + "                          " + this.additionalDepositState.get(1).returnLine(2) + "                          ┃ \n" +
                    " ┃            DEPOSIT                      " + this.additionalDepositState.get(0).returnLine(3) + "                          " + this.additionalDepositState.get(1).returnLine(3) + "                          ┃ \n" +
                    " ┃             ( " + colorSymbol(0) + " )                       " + this.additionalDepositState.get(0).returnLine(4) + "                          " + this.additionalDepositState.get(1).returnLine(4) + "                          ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(0).returnLine(5) + "                          " + this.additionalDepositState.get(1).returnLine(5) + "                          ┃ \n" +
                    " ┃                                              -1-                                    -2-                               ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n");

        }
        if (additionalDepositState.size() == 4) {
            System.out.print(" ┃                                         " + this.additionalDepositState.get(2).returnLine(0) + "                          " + this.additionalDepositState.get(3).returnLine(0) + "                          ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(2).returnLine(1) + "                          " + this.additionalDepositState.get(3).returnLine(1) + "                          ┃ \n" +
                    " ┃        SPECIAL ABILITY                  " + this.additionalDepositState.get(2).returnLine(2) + "                          " + this.additionalDepositState.get(3).returnLine(2) + "                          ┃ \n" +
                    " ┃            DEPOSIT                      " + this.additionalDepositState.get(2).returnLine(3) + "                          " + this.additionalDepositState.get(3).returnLine(3) + "                          ┃ \n" +
                    " ┃             ( " + colorSymbol(2) + " )                       " + this.additionalDepositState.get(2).returnLine(4) + "                          " + this.additionalDepositState.get(3).returnLine(4) + "                          ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(2).returnLine(5) + "                          " + this.additionalDepositState.get(3).returnLine(5) + "                          ┃ \n" +
                    " ┃                                              -3-                                    -4-                               ┃ \n" +
                    " ┃                                                                                                                       ┃ \n");
        }

        System.out.print("\u001B[0;00m" + " ┃                                                      ______________                                                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                       ");

        if(context.equals("reorganize"))
            System.out.print("TO ORGANIZE:");
        if(context.equals("view"))
            System.out.print(" STRONGBOX: ");

        System.out.print("                                                    ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                       Coin:     ");
        if(informationReceived.get(ResourceIcon.COIN.toString())<10)
            System.out.print("0" + informationReceived.get(ResourceIcon.COIN.toString()));
        else
            System.out.print(informationReceived.get(ResourceIcon.COIN.toString()));
        System.out.print("                                                    ┃\n" +
                " ┃                                                       Servant:  ");
        if(informationReceived.get(ResourceIcon.SERVANT.toString())<10)
            System.out.print("0" + informationReceived.get(ResourceIcon.SERVANT.toString()));
        else
            System.out.print(informationReceived.get(ResourceIcon.SERVANT.toString()));

        System.out.print("                                                    ┃\n" +
                " ┃                                                       Shield:   ");

        if(informationReceived.get(ResourceIcon.SHIELD.toString())<10)
            System.out.print("0" + informationReceived.get(ResourceIcon.SHIELD.toString()));
        else
            System.out.print(informationReceived.get(ResourceIcon.SHIELD.toString()));

        System.out.print("                                                    ┃\n" +
                " ┃                                                       Stone:    ");
        if(informationReceived.get(ResourceIcon.STONE.toString())<10)
            System.out.print("0" + informationReceived.get(ResourceIcon.STONE.toString()));
        else
            System.out.print(informationReceived.get(ResourceIcon.STONE.toString()));

        System.out.print("                                                    ┃\n" +
                " ┃                                                      ______________                                                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ \n" +
                "\n");

        if(context.equals("reorganize"))
            System.out.print("                             Choose a material and a position to put into (material,position):                             \n" +
                    "                              " + "\u001B[92m" + "[type done when finished or additionalDeposit to change view]                              \n" +
                    "\n" +
                    "                                                              " + "\u001B[0;00m");
        else if(context.equals("view"))
            System.out.print("\u001B[92m" + "                                                Type quit to exit this view                                                " + "\u001B[0;0m" +"\n" +
                    "                                                              ");

    }

    /**
     * Return the colored symbol for the additional deposit view
     * @param num contains the number of the position inside the type arraylist of resources that can be inserted
     *            inside the additional deposits
     * @return the colored symbol by the resource type
     */
    private String colorSymbol(int num) {
        String resource = type.get(num).toString();

        switch (resource) {
            case "COIN":
                return ("\u001B[93m" + "█" + "\u001B[0m");

            case "SHIELD":
                return ("\u001B[36m" + "█" + "\u001B[0m");

            case "STONE":
                return ("\u001B[1;37m" + "█" + "\u001B[0m");

            case "SERVANT":
                return ("\u001B[35m" + "█" + "\u001B[0m");

            default:
                return ("\u001B[0m" + "█" + "\u001B[0m");
        }
    }
}
