package it.polimi.ingsw.Client.CLI.Views.MarketView;

import it.polimi.ingsw.Client.CLI.Views.Messages;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NewDepositView {

    ArrayList<ResourceIcon> depositState;
    ArrayList<ResourceIcon> additionalDepositState;
    ArrayList<ResourceIcon> type;
    Map<String, Integer> marketReceived;
    boolean additionalDeposit;

    public static void main(String[] args) {
        ArrayList<Resource> depositState = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            depositState.add(null);
        }
        ArrayList<Resource> marketReceived = new ArrayList<>();

        marketReceived.add(Resource.COIN);
        marketReceived.add(Resource.COIN);
        marketReceived.add(Resource.SHIELD);
        marketReceived.add(Resource.SHIELD);
        marketReceived.add(Resource.STONE);
        marketReceived.add(Resource.STONE);
        marketReceived.add(Resource.SERVANT);
        marketReceived.add(Resource.SERVANT);

        boolean additionalDeposit = true;
        ArrayList<Resource> type = new ArrayList<>();
        type.add(Resource.COIN);

        ArrayList<Resource> additionalDepositState = new ArrayList<>();
        additionalDepositState.add(null);
        additionalDepositState.add(null);


        NewDepositView prova = new NewDepositView(depositState, marketReceived, additionalDeposit, type, additionalDepositState);
        prova.LaunchView();

        System.out.println(prova.getDepositState());
        System.out.println(prova.getMarketReceived());
        System.out.println(prova.getAdditionalDepositState());
    }

    public NewDepositView(ArrayList<Resource> depositState, ArrayList<Resource> marketReceived, boolean additionalDeposit, ArrayList<Resource> type, ArrayList<Resource> additionalDepositState) {

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

        this.marketReceived = new HashMap<>();

        for (ResourceIcon r : ResourceIcon.values())
            this.marketReceived.put(r.toString(), 0);

        this.marketReceived.put(ResourceIcon.NOTHING.toString(), 6);

        for (Resource r : marketReceived) {
            this.marketReceived.put(r.toString(), this.marketReceived.get(r.toString()) + 1);
        }

        this.additionalDeposit = additionalDeposit;

        if (additionalDeposit) {
            for (Resource r : type) {
                this.type.add(ResourceIcon.valueOf(r.toString()));
                this.type.add(ResourceIcon.valueOf(r.toString()));
            }

            for (Resource t : additionalDepositState)
                if (t != null)
                    this.additionalDepositState.add(ResourceIcon.valueOf(t.toString()));
                else
                    this.additionalDepositState.add(ResourceIcon.NOTHING);
        }
    }

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

    public int getMarketReceived() {
        int num = 0;

        for (ResourceIcon r : ResourceIcon.values())
            if (!r.equals(ResourceIcon.NOTHING))
                num = num + marketReceived.get(r.toString());

        return num;
    }

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

    public boolean isAdditionalDeposit() {
        return additionalDeposit;
    }

    public void LaunchView() {

        PrintDepositChoise(this.depositState, marketReceived);

        String input = "";

        String state = "market";

        while (true) {
            if (state.equals("market")) {
                PrintDepositChoise(this.depositState, marketReceived);
            }
            if (state.equals("additionalDeposit")) {
                printAdditionalDeposit(additionalDepositState, marketReceived);
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
                if (additionalDepositState.size() == 0) {
                    Messages message = new Messages("YOU DON'T HAVE ANY ADDITIONAL DEPOSIT", true);
                    message.printMessage();
                }
                if (formatInput[0].equals("market")) {
                    state = "market";
                }
                if (formatInput[0].equals("done"))
                    break;
            }

            if (formatInput.length == 2) {
                //elimino eventuali spazi iniziali dalle due stringhe
                formatInput[0] = formatInput[0].trim();

                int num = -1;
                formatInput[1] = formatInput[1].trim();

                //trasformo la seconda stringa in un numero
                try {
                    num = Integer.parseInt(formatInput[1]);
                } catch (IllegalArgumentException e) {
                    Messages message = new Messages("INSERT A CORRECT INPUT PLEASE", true);
                    message.printMessage();
                    continue;
                }


                if((this.type.size()==4 && (num<1 || num>5)) || (type.size()==2 && (num<1 || num>2))){
                    Messages message = new Messages("INSERT A CORRECT INPUT PLEASE", true);
                    message.printMessage();
                    continue;
                }

                formatInput[0] = formatInput[0].toUpperCase();

                if (state.equals("market")) {
                    //tolgo il materiale dalle risorse ottenute dal market da inserire in deposito
                    if (marketReceived.containsKey(formatInput[0]) && num > 0 && num < 7)
                        if (marketReceived.get(formatInput[0]) - 1 > -1) {
                            marketReceived.put(formatInput[0], marketReceived.get(formatInput[0]) - 1);

                            //aggiungo il materiale tolto dal deposito ai materiali disponibili
                            if (marketReceived.containsKey(formatInput[0]) && depositState.get(num - 1) != null)
                                marketReceived.put(depositState.get(num - 1).toString(), marketReceived.get(depositState.get(num - 1).toString()) + 1);

                            //aggiungo il materiale tolto dai materiali disponibili al deposito
                            if (marketReceived.containsKey(formatInput[0]))
                                depositState.set(num - 1, ResourceIcon.valueOf(formatInput[0]));
                        } else {
                            Messages message = new Messages("INSERT A CORRECT INPUT PLEASE", true);
                        }
                }

                if (state.equals("additionalDeposit")) {
                    if ((formatInput[0].equals("NOTHING") || formatInput[0].equals(type.get(num - 1).toString()))
                            && marketReceived.containsKey(formatInput[0]) && num > 0 && num < additionalDepositState.size() + 1) {

                        if (marketReceived.get(formatInput[0]) - 1 > -1) {
                            marketReceived.put(formatInput[0], marketReceived.get(formatInput[0]) - 1);

                            //aggiungo il materiale tolto dal deposito ai materiali disponibili
                            if (marketReceived.containsKey(formatInput[0]) && additionalDepositState.get(num - 1) != null)
                                marketReceived.put(additionalDepositState.get(num - 1).toString(), marketReceived.get(additionalDepositState.get(num - 1).toString()) + 1);

                            //aggiungo il materiale tolto dai materiali disponibili al deposito
                            if (marketReceived.containsKey(formatInput[0]))
                                additionalDepositState.set(num - 1, ResourceIcon.valueOf(formatInput[0]));
                        }
                    } else {
                        Messages message = new Messages("INSERT A CORRECT INPUT PLEASE", true);
                    }
                }
            }

        }

    }

    public void PrintDepositChoise(ArrayList<ResourceIcon> incoming, Map<String, Integer> acquired) {

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
                " ┃                                                       TO ORGANIZE                                                     ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                       Coin:     " + acquired.get(ResourceIcon.COIN.toString()) + "                                                     ┃ \n" +
                " ┃                                                       Servant:  " + acquired.get(ResourceIcon.SERVANT.toString()) + "                                                     ┃ \n" +
                " ┃                                                       Shield:   " + acquired.get(ResourceIcon.SHIELD.toString()) + "                                                     ┃ \n" +
                " ┃                                                       Stone:    " + acquired.get(ResourceIcon.STONE.toString()) + "                                                     ┃ \n" +
                " ┃                                                      ______________                                                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ \n" +
                "\n" +
                "                             Choose a material and a position to put into (material,position):                             \n" +
                "                              " + "\u001B[92m" + "[type done when finished or additionalDeposit to change view]                              \n" +
                "\n" +
                "                                                              " + "\u001B[0;00m");
    }

    public void printAdditionalDeposit(ArrayList<ResourceIcon> additionalDepositState, Map<String, Integer> acquired) {
        System.out.print("\u001B[2J\u001B[3J\u001B[H");

        System.out.println("\u001B[0;00m" +
                "                                                  ╔═══════════════════════╗ \n" +
                "                                                  ║  ADDITIONAL DEPOSITS  ║ \n" +
                "                                                  ╚═══════════════════════╝ \n" +
                " ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ ");

        if (additionalDepositState.size() == 4) {
            System.out.print(" ┃                                         " + this.additionalDepositState.get(0).returnLine(0) + "                          " + this.additionalDepositState.get(1).returnLine(0) + "                          ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(0).returnLine(1) + "                          " + this.additionalDepositState.get(1).returnLine(1) + "                          ┃ \n" +
                    " ┃        SPECIAL ABILITY                  " + this.additionalDepositState.get(0).returnLine(2) + "                          " + this.additionalDepositState.get(1).returnLine(2) + "                          ┃ \n" +
                    " ┃            DEPOSIT                      " + this.additionalDepositState.get(0).returnLine(3) + "                          " + this.additionalDepositState.get(1).returnLine(3) + "                          ┃ \n" +
                    " ┃             ( "+colorSymbol(0) + " )                       " + this.additionalDepositState.get(0).returnLine(4) + "                          " + this.additionalDepositState.get(1).returnLine(4) + "                          ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(0).returnLine(5) + "                          " + this.additionalDepositState.get(1).returnLine(5) + "                          ┃ \n" +
                    " ┃                                              -1-                                    -2-                               ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(2).returnLine(0) + "                          " + this.additionalDepositState.get(3).returnLine(0) + "                          ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(2).returnLine(1) + "                          " + this.additionalDepositState.get(3).returnLine(1) + "                          ┃ \n" +
                    " ┃        SPECIAL ABILITY                  " + this.additionalDepositState.get(2).returnLine(2) + "                          " + this.additionalDepositState.get(3).returnLine(2) + "                          ┃ \n" +
                    " ┃            DEPOSIT                      " + this.additionalDepositState.get(2).returnLine(3) + "                          " + this.additionalDepositState.get(3).returnLine(3) + "                          ┃ \n" +
                    " ┃             ( "+ colorSymbol(2) + " )                       " + this.additionalDepositState.get(2).returnLine(4) + "                          " + this.additionalDepositState.get(3).returnLine(4) + "                          ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(2).returnLine(5) + "                          " + this.additionalDepositState.get(3).returnLine(5) + "                          ┃ \n" +
                    " ┃                                              -3-                                    -4-                               ┃ \n");
        }
        if (additionalDepositState.size() == 2) {
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
                    " ┃             ( "+colorSymbol(0) + " )                       " + this.additionalDepositState.get(0).returnLine(4) + "                          " + this.additionalDepositState.get(1).returnLine(4) + "                          ┃ \n" +
                    " ┃                                         " + this.additionalDepositState.get(0).returnLine(5) + "                          " + this.additionalDepositState.get(1).returnLine(5) + "                          ┃ \n" +
                    " ┃                                              -1-                                    -2-                               ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n" +
                    " ┃                                                                                                                       ┃ \n");

        }
        System.out.print("\u001B[0;00m" + " ┃                                                                                                                       ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                      ______________                                                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                       TO ORGANIZE                                                     ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┃                                                       Coin:     " + acquired.get(ResourceIcon.COIN.toString()) + "                                                     ┃ \n" +
                " ┃                                                       Servant:  " + acquired.get(ResourceIcon.SERVANT.toString()) + "                                                     ┃ \n" +
                " ┃                                                       Shield:   " + acquired.get(ResourceIcon.SHIELD.toString()) + "                                                     ┃ \n" +
                " ┃                                                       Stone:    " + acquired.get(ResourceIcon.STONE.toString()) + "                                                     ┃ \n" +
                " ┃                                                      ______________                                                   ┃ \n" +
                " ┃                                                                                                                       ┃ \n" +
                " ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ \n" +
                "\n" +
                "                             Choose a material and a position to put into (material,position):                             \n" +
                "                                    " + "\u001B[92m" + "[type done when finished or deposit to change view]                                    \n" +
                "\n" +
                "                                                              " + "\u001B[0;00m");

    }

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
