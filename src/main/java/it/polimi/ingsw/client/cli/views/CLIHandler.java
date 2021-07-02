package it.polimi.ingsw.client.cli.views;


import it.polimi.ingsw.client.cli.WaitingOtherPlayers;
import it.polimi.ingsw.client.cli.views.leadercardview.LeaderCardView;
import it.polimi.ingsw.client.cli.views.marketview.MarketView;
import it.polimi.ingsw.client.cli.views.marketview.NewDepositView;
import it.polimi.ingsw.client.cli.views.otherviews.AllPlayersView;
import it.polimi.ingsw.client.cli.views.otherviews.InitialResourceView;
import it.polimi.ingsw.client.cli.views.otherviews.NewTurnView;
import it.polimi.ingsw.client.cli.views.productionview.*;
import it.polimi.ingsw.client.ConnectionToServer;
import it.polimi.ingsw.events.servertoclient.supportclass.DevelopmentCardToClient;
import it.polimi.ingsw.events.servertoclient.supportclass.LeaderCardToClient;
import it.polimi.ingsw.events.servertoclient.supportclass.MarketToClient;
import it.polimi.ingsw.events.servertoclient.supportclass.PlayerInformationToClient;
import it.polimi.ingsw.model.resource.Resource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This class visualizes all the information of the player and game board, take the input from the various
 * view handler and send the data to the server
 *
 * @author Mirko Genoni
 */

public class CLIHandler {
    private final ConnectionToServer connection;
    private String namePlayer;
    private Map<String, PlayerInformationToClient> players;
    private MarketView market;
    private DevelopmentCardView developmentCardForSale;
    private DevelopmentCardVisualization currentBuying;
    private TotalResourceCounter totalResourceCounter;
    AllPlayersView allPlayersView;
    ArrayList<LeaderCardToClient> inactiveLeaders;

    private int tmpRoomNumber;
    private String newRoomText;
    private boolean isNewRoomOrNot;

    private boolean turnEnd;

    Thread asyncPrint;

    /**
     * Constructor of the class, initializes all the data structure
     * @param connection passes to this class the connection to the server, already created
     */
    public CLIHandler(ConnectionToServer connection){
        this.connection = connection;
    }

    public int getTmpRoomNumber() {
        return tmpRoomNumber;
    }

    public boolean isNewRoomOrNot() {
        return isNewRoomOrNot;
    }

    /**
     * This method gets from the user all the information needed to start the game and prints the data already received
     * (ip address and port), asks and checks the name of the player, if the room is new, the number of the room, eventually
     * if the room is new and it's the first player the number of players
     *
     * @param DataRequired is a String that represents the current state and asks the correct information to the user
     */
    public synchronized void insertInitialData(String DataRequired){
        stopAsyncPrint();
        Scanner in = new Scanner(System.in);
        boolean notDone = true;

        while(notDone) {
            System.out.print("\u001B[2J\u001B[3J\u001B[H");
            System.out.println("\n" +
                    "                   ┌───────────────────────────────────────────────────────────────────────────────────┐                   \n" +
                    "                   │      ╔═══╦═══╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗       ╔═════╗ ╔═════╗      │                   \n" +
                    "                   │      ║ ╔╗ ╔╗ ║ ║ ╔═╗ ║ ║ ╔═══╝ ╚═╗ ╔═╝ ║ ╔═══╝ ║  ══ ║       ║ ╔═╗ ║ ║  ══╦╝      │                   \n" +
                    "                   │      ║ ║║ ║║ ║ ║ ╠═╣ ║ ║ ╚═══╗   ║ ║   ║ ╠═══  ║ ╔══╗║       ║ ║ ║ ║ ║ ╔══╝       │                   \n" +
                    "                   │      ║ ║╚═╝║ ║ ║ ║ ║ ║ ╠════ ║   ║ ║   ║ ╚═══╗ ║ ║  ║║       ║ ╚═╝ ║ ║ ║          │                   \n" +
                    "                   │      ╚═╝   ╚═╝ ╚═╝ ╚═╝ ╚═════╝   ╚═╝   ╚═════╝ ╚═╝  ╚╝       ╚═════╝ ╚═╝          │                   \n" +
                    "                   │                                                                                   │                   \n" +
                    "                   │╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═════╗│                   \n" +
                    "                   │║  ══ ║ ║ ╔═══╝ ║  ╚╝ ║ ║ ╔═╗ ║ ║ ║ ║ ╔═══╝ ║ ╔═══╝ ║ ╔═╗ ║ ║  ╚╝ ║ ║ ╔═══╝ ║ ╔═══╝│                   \n" +
                    "                   │║ ╔══╗║ ║ ╠═══  ║ ╔╗  ║ ║ ╠═╣ ║ ║ ║ ║ ╚═══╗ ║ ╚═══╗ ║ ╠═╣ ║ ║ ╔╗  ║ ║ ║     ║ ╠═══ │                   \n" +
                    "                   │║ ║  ║║ ║ ╚═══╗ ║ ║║  ║ ║ ║ ║ ║ ║ ║ ╠════ ║ ╠════ ║ ║ ║ ║ ║ ║ ║║  ║ ║ ╚═══╗ ║ ╚═══╗│                   \n" +
                    "                   │╚═╝  ╚╝ ╚═════╝ ╚═╝╚══╝ ╚═╝ ╚═╝ ╚═╝ ╚═════╝ ╚═════╝ ╚═╝ ╚═╝ ╚═╝╚══╝ ╚═════╝ ╚═════╝│                   \n" +
                    "                   └───────────────────────────────────────────────────────────────────────────────────┘                   \n");

            System.out.print("                                                                                                                          \n" +
                    "                                                                                                                          \n" +
                    "                                                                                                                          \n" +
                    "                                                                                                                          \n");

            System.out.print("                                    INSERT SERVER ADDRESS: " + "\u001B[92m" + connection.getAddress() + "\u001B[0m" + "\n\n");

            if (DataRequired.equals("isNewRoom")) {
                System.out.print("                                    IS YOUR ROOM NEW? ");

                String tmpIn = in.nextLine();
                tmpIn = tmpIn.toUpperCase();

                if (tmpIn.equals("YES") || tmpIn.equals("Y")) {
                    this.newRoomText = tmpIn;
                    this.isNewRoomOrNot = true;
                    notDone = false;
                    break;
                } else if (tmpIn.equals("NO") || tmpIn.equals("N")) {
                    this.newRoomText = tmpIn;
                    this.isNewRoomOrNot = false;
                    notDone = false;
                    break;
                }
            } else {
                System.out.println("                                    IS YOUR ROOM NEW? " + "\u001B[92m" + this.newRoomText + "\u001B[0m");
            }

            if (DataRequired.equals("roomNumber")) {
                String tmpIn;
                int tmpRoomNumber;

                System.out.print("                                    INSERT ROOM NUMBER: ");

                try {
                    tmpIn = in.nextLine();
                    tmpRoomNumber = Integer.parseInt(tmpIn);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (tmpRoomNumber > 0) {
                    this.tmpRoomNumber = tmpRoomNumber;
                    notDone = false;
                }
            } else if (!DataRequired.equals("isNewRoom")) {
                System.out.println("                                    INSERT ROOM NUMBER: " + "\u001B[92m" + this.tmpRoomNumber + "\u001B[0m" + "\n");
            }

            if (DataRequired.equals("namePlayer")) {
                WaitingOtherPlayers animation = new WaitingOtherPlayers();
                String tmpName = "";

                System.out.print("                                    INSERT NAME: ");

                tmpName = in.nextLine();

                if(tmpName.length() == 0) {
                    notDone = true;
                    continue;
                }

                this.namePlayer = tmpName;

                connection.setPlayerName(tmpName);
                connection.sendPlayerName(tmpName);

                notDone = false;
                this.asyncPrint = animation;
                animation.start();
            } else if (!DataRequired.equals("isNewRoom") && !DataRequired.equals("roomNumber")) {
                System.out.println("                                    INSERT NAME: " + "\u001B[92m" + this.namePlayer + "\u001B[0m" + "\n");
            }

            if (DataRequired.equals("numberOfPlayers")) {
                stopAsyncPrint();
                WaitingOtherPlayers animation = new WaitingOtherPlayers();
                String tmpIn;
                int tmpNumPlayers;

                System.out.print("                                    YOU'RE THE FIRST PLAYER! ");

                for (int i = 0; i < 60; i++)
                    System.out.print("\b");

                System.out.print("                                   INSERT NUMBER OF PLAYERS: ");

                try {
                    tmpIn = in.nextLine();
                    tmpNumPlayers = Integer.parseInt(tmpIn);
                } catch (NumberFormatException e) {
                    continue;
                }

                int tmpNumPlayers1 = tmpNumPlayers;
                notDone = false;
                connection.sendNumPlayer(tmpNumPlayers);
                this.asyncPrint = animation;
                animation.start();
            }
        }
    }

    /**
     * This method stops the animation of waiting other players, created with the class WaitingOtherPlayers
     */
    public void stopAsyncPrint(){
        if(this.asyncPrint!=null && this.asyncPrint.isAlive())
            asyncPrint.interrupt();
    }

    /**
     * This method handles the receive of initial resources, and gives the user the possibility to rearrange them inside
     * the deposit
     * @param numResources is the number of initial resources received
     * @param depositState is the current state of the deposit
     */
    public void initialResourceSelection(int numResources, ArrayList<Resource> depositState){
        stopAsyncPrint();
        InitialResourceView selection= new InitialResourceView(numResources);
        Resource choosen = selection.startChoosing();

        ArrayList<Resource> initialResources = new ArrayList<>();

        for(int i=0; i< numResources; i++){
            initialResources.add(choosen);
        }

        //essendo non ancora iniziato il gioco non può avere depositi addizionali
        NewDepositView newDepositView = new NewDepositView(depositState, initialResources,false, null, null);
        newDepositView.LaunchView();

        connection.sendInitialDepositState(newDepositView.getDepositState());
        Messages messages = new Messages("Wait other player's initial material choice", false);
        messages.printMessage();
    }

    /**
     * This method is the first received from the cli and handles the discard or activation of the cards, depending on the
     * user input and the initial boolean, stores then the card received in an attribute of the class so that after the
     * handle of this event are available to the cli. If the card are discarded they are removed immediately from the
     * arrayList, if they are activated they will be removed later, because if the server refuse to activate the data of
     * the cards would be lost
     *
     * @param received contains the leader card received from the server
     * @param initial indicates if the cards received the initial
     * @param totalReceived contains the count of all the development cards and resources
     * @param isFinal indicates if the leaderCard are sent at the start or end of the turn
     */
    public void leaderCardSelection(ArrayList<LeaderCardToClient> received, boolean initial, Map<String, Integer> totalReceived, boolean isFinal){
        stopAsyncPrint();

        LeaderCardView leaderCardSelection = new LeaderCardView(new ArrayList<>(received), totalReceived);

        this.asyncPrint = new WaitingOtherPlayers();

        this.inactiveLeaders = new ArrayList<>();

        if(initial){
            int[] receive = leaderCardSelection.StartInitialLeaderCardView();
            this.connection.sendDiscardInitialLeaderCards(receive[0], receive[1]);
            for(int i=0; i<received.size(); i++) {
                if (i != receive[0] && i != receive[1])
                    this.inactiveLeaders.add(received.get(i));
            }
            Messages messages = new Messages("Wait other player's discard choice", false);
            messages.printMessage();
        } else {
            this.inactiveLeaders = received;
            ArrayList<Integer> receive = leaderCardSelection.StartCardView();

            int j=0;
            for(Integer i: receive)
                if(i==2){ //if discarded they have been removed
                    inactiveLeaders.set(j, null);
                    j++;
                }

            connection.sendLeaderCardActions(receive, isFinal);
        }
    }

    /**
     * This method is called after the handle of the leaderCard and updates all the data of all the players and removes
     * from the leader card in hand the one activated, calls then the newTurn() method
     *
     * @param players is a map that contains all the data of all the player's board
     * @param market contains the state of the market
     * @param developmentCardToBuy contains the state of the development card store
     */
    public synchronized void newState(Map<String, PlayerInformationToClient> players,
                         MarketToClient market,
                         DevelopmentCardToClient[][] developmentCardToBuy){

        this.players = players;
        this.market = new MarketView(market);
        this.turnEnd = false;

        PlayerInformationToClient currentPlayer = players.get(this.namePlayer);
        this.totalResourceCounter = new TotalResourceCounter(currentPlayer.getStrongBox(), currentPlayer.getDeposit(), currentPlayer.getAdditionalDeposit());

        this.developmentCardForSale = new DevelopmentCardView(developmentCardToBuy, totalResourceCounter);

        for(LeaderCardToClient l: players.get(namePlayer).getLeaderCardActive())
            if(inactiveLeaders!=null) //I need to check only when I've received the leaderCard selection turn
                for(int i=0; i<inactiveLeaders.size();i++)
                    if(inactiveLeaders.get(i)!=null && l.getNameCard().equals(inactiveLeaders.get(i).getNameCard()))
                        inactiveLeaders.set(i, null);

        this.allPlayersView = new AllPlayersView(this.players, this.namePlayer, this.inactiveLeaders);
    }


    /**
     * This method handles all the various type of turn and send the data to the server
     * -market: take material from the market and checks if a marketWhiteChange leader power is activated, eventually
     *          calling the handler of the marketWhiteChange
     *
     * -buydevelopment: the user buys a card from the development card market, calls eventually a handler for the costLess
     *                  leaderPower
     *
     * -usedevelopment: the user activates one of the development, the base production, the leader additional production
     *                  or a development card
     *
     * -viewboard: the user can see the boards of all the players
     */
    public void newTurn(){
        this.turnEnd = false;
        
        while(!this.turnEnd) {
            NewTurnView chooseTurn = new NewTurnView();
            String in = chooseTurn.startTurnChoise();

            switch (in) {
                case "market":
                    market.launchChoiseView(this.getWhiteChange(players.get(this.namePlayer).getLeaderCardActive()));
                    int line1 = market.getChoiseLine();
                    ArrayList<Boolean> tmp = marketWhiteChangeActive(players.get(this.namePlayer).getLeaderCardActive());
                    if (market.isTurnEnd())
                        connection.sendChooseLine(line1, tmp);
                    this.turnEnd = market.isTurnEnd();
                    break;
                case "buydevelopment":
                    ArrayList<String> tmp2 = costLessCheck(players.get(this.namePlayer).getLeaderCardActive());
                    selectedDevelopmentCard(this.developmentCardForSale, tmp2);
                    this.turnEnd = developmentCardForSale.isTurnEnd();
                    break;
                case "usedevelopment":
                    DevelopmentCardView developmentBoard = new DevelopmentCardView(this.players.get(this.namePlayer).getDevelopmentCardPlayer(), this.totalResourceCounter);

                    ArrayList<String> leaderCardPower = new ArrayList<>();
                    for (int j = 0; j < players.get(this.namePlayer).getLeaderCardActive().size(); j++) {          //control if there's an active LeaderCard additionalProd
                        if (players.get(this.namePlayer).getLeaderCardActive().get(j).getEffect().equals("additionalProduction")) {
                            leaderCardPower.add(players.get(this.namePlayer).getLeaderCardActive().get(j).getResourceType());
                        }
                    }
                    int additionalNumber = leaderCardPower.size();
                    AdditionalProductionView additionalProductionView = new AdditionalProductionView(leaderCardPower);

                    this.turnEnd = choseDevelopment(developmentBoard, additionalProductionView, additionalNumber);
                    break;

                case "turn":
                    connection.sendTurnPlayed(in);
                    this.turnEnd = true;
                    break;

                case "viewboard":
                    this.allPlayersView.test();
                    break;
            }
        }
    }

    /**
     * This method starts the cli for the rearrangement of the deposit, the check of its correctness is done by the server
     *
     * @param deposit represent the current state of the deposit
     * @param marketReceived is a map that contains the resources received from the market
     * @param additional is a boolean that indicates if there is one or more additional deposit activated
     * @param type contains all the types of material that the additional deposit can contain, its length can be 1 and
     *             that represents that there is only one additional deposit active or 2 and that represents that there
     *             are 2 deposits activate
     * @param state represents the current state of the additional deposit
     */
    public void selectNewDeposit(ArrayList<Resource> deposit, ArrayList<Resource> marketReceived,
                                  boolean additional, ArrayList<Resource> type, ArrayList<Resource> state){

        NewDepositView tmp = new NewDepositView(deposit, marketReceived, additional, type, state);
        tmp.LaunchView();
        connection.sendNewDepositState(tmp.getDepositState(), tmp.getMarketReceived(), tmp.isAdditionalDeposit(),tmp.getAdditionalDepositState());
    }

    /**
     * This method handles the visualization of the view to buy development card
     *
     * @param in contains the view handler already created from the development card store received from the newState()
     * @param costLess represents the material eventually discounted from the leader power
     */
    private void selectedDevelopmentCard(DevelopmentCardView in, ArrayList<String> costLess){
        in.startCardForSaleSelection(costLess);

        //converts the color selected with a number needed by the server
        if(in.isTurnEnd()) {
            int num;
            String color;
            num = in.getNum();
            color = in.getColor();
            int colorForCard;

            switch (color) {
                case "green":
                    colorForCard = 0;
                    break;
                case "blue":
                    colorForCard = 1;
                    break;
                case "yellow":
                    colorForCard = 2;
                    break;
                case "purple":
                    colorForCard = 3;
                    break;
                default:
                    throw new RuntimeException(); //attention Exception
            }

            this.currentBuying = this.developmentCardForSale.getCard(3*colorForCard + (num - 1));
            connection.sendSelectedDevelopmentCard(colorForCard, num - 1); //livello da 0 a 2
        }
    }

    /**
     * This method gives the user the choice to where to postion the development card bought
     *
     * @param space is an array of 3 spaces that represent if the development card can be positioned in that position
     */
    public void selectSpaceForDevelopment(ArrayList<Boolean> space){
        ArrayList<DevelopmentCardToClient> tmp = new ArrayList<>();
        int i=0;

        for(boolean r : space){
            if(r && players.get(this.namePlayer).getDevelopmentCardPlayer().get(i) != null)
                tmp.add(players.get(this.namePlayer).getDevelopmentCardPlayer().get(i));
            else
                tmp.add(null);

            i++;
        }

        DevelopmentCardView selectSpace = new DevelopmentCardView(space, tmp, this.currentBuying);
        int tmpPos = selectSpace.startSelectionNewCardSpace();
        connection.sendSelectedDevelopmentCardSpace(tmpPos);
    }

    /**
     * This method handles the activation of one or more of the development
     *
     * @param developmentCardBoard contains all the development possessed by the user
     * @param additionalProductionView contains all the additional production given by a leader power
     * @param additionalNumber contains the number of additional production possessed, if 1 it's necessary to apply a
     *                         correction to the data before it can be send to the server
     * @return false if the turn is not finished and the user changed his mind about the turn chois
     */
    private boolean choseDevelopment(DevelopmentCardView developmentCardBoard, AdditionalProductionView additionalProductionView, int additionalNumber){
        boolean tmp;

        ProductionSelectionView prod = new ProductionSelectionView(developmentCardBoard, additionalProductionView, this.totalResourceCounter);
        tmp = prod.activationSelection("base");

        if(!tmp)
            return false;

        tmp = prod.activationSelection("leader");

        if(!tmp)
            return false;

        tmp = prod.activationSelection("development");

        if(!tmp)
            return false;

        /*DEBUG
        System.out.println("invio questi dati:");
        System.out.println("use_baseprod: " + useBaseProduction);
        if(useBaseProduction)
            System.out.println("selected for base prod: "+ input.get(0).toString() + " "+ input.get(1).toString() +"--> " + input.get(2).toString());
        System.out.println("use leaders --> "+ activation.toString());
        System.out.println("material leaders --> "+ materialLeader.toString());
        System.out.println("use develop --> "+ useDevelop.toString());*/

        ArrayList<Boolean> tmpB = new ArrayList<>();

        //insert the boolean in the correct position as the position in the active leader card, not handled by
        // the additional production cli view
        if(additionalNumber==1) {
            for (boolean b : prod.getLeaderActivation())
                if (b)
                    for (int j = 0; j < players.get(this.namePlayer).getLeaderCardActive().size(); j++) {
                        if (players.get(this.namePlayer).getLeaderCardActive().get(j).getEffect().equals("additionalProduction")) {
                            tmpB.add(true);
                        } else {
                            tmpB.add(false);
                        }
                    }
        }

        connection.sendSelectedProductionDevelopmentCard(prod.isUseBaseProduction(), prod.getInputBaseProduction().get(0), prod.getInputBaseProduction().get(1), prod.getProdottoBaseProd(),
               tmpB, prod.getMaterialLeader(), prod.getSelectDevelopment());

        return true;

    }


    /**
     * This method checks for costLess leader power inside the active leaderCard and pass the resource discounted for the
     * bought of development card
     *
     * @param leaderCardActive contains all the leader cards activated by the player
     * @return the Strings of resources discounted by the leader power
     */
    private ArrayList<String> costLessCheck(ArrayList<LeaderCardToClient> leaderCardActive){
        ArrayList<String> tmp = new ArrayList<>();

        for(LeaderCardToClient l: leaderCardActive)
            if(l.getEffect().equals("costLess"))
                tmp.add(l.getResourceType());

        return tmp;
    }

    /**
     * This method checks for market white change leader power inside the active leader card for the market turn
     * and return the Strings of resources that the market white marbles can be turned into
     *
     * @param leaderCardActive contains all the leaderCard activated by the player
     * @return the Strings of resources that the white marbles can be turned into
     */
    private ArrayList<String> getWhiteChange(ArrayList<LeaderCardToClient> leaderCardActive) {
        ArrayList<String> whiteChange = new ArrayList<>();

        for (int i = 0; i < leaderCardActive.size(); i++) { //Find market white change power
            if (leaderCardActive.get(i).getEffect().equals("marketWhiteChange")) {
                whiteChange.add(leaderCardActive.get(i).getResourceType());
            }
        }
        return whiteChange;
    }

    /**
     * This method places the boolean of the market white change power activated inside the correct position and send the
     * data to the server
     *
     * @param leaderCardActive contains all the leaderCard activated by the player
     * @return the correct boolean position confronted with the arraylist of leader card activate
     */
    private ArrayList<Boolean> marketWhiteChangeActive(ArrayList<LeaderCardToClient> leaderCardActive){
        ArrayList<Boolean> leaderCardWhiteChange = new ArrayList<>();

        for(int i=0; i< leaderCardActive.size(); i++){ //Find market white change power
            if(leaderCardActive.get(i).getEffect().equals("marketWhiteChange")){
                leaderCardWhiteChange.add(true);
            }else{
                leaderCardWhiteChange.add(false);
            }
        }

        if(leaderCardActive.size()==2){ //due leader attivi
            if(leaderCardWhiteChange.get(0) && leaderCardWhiteChange.get(1)){ //ONLY IF YOU HAVE TWO WHITE-CHANGE
              leaderCardWhiteChange = selectWhichWhiteChange(leaderCardActive); //torna array con un solo true e un solo false
            }
        }

        int j=0;
        String marbleOut = "";

        for(boolean b: leaderCardWhiteChange){
            if(b)
                marbleOut = leaderCardActive.get(j).getResourceType();
            j++;
        }

        return leaderCardWhiteChange;
    }

    /**
     * This view handles the eventual activation of two market white change, if this are activated simultaneously the user
     * must choose one between them
     *
     * @param leaderCardActive contains all the leaderCard activated by the player
     * @return the boolean of the market white change activate by the user
     */
    private ArrayList<Boolean> selectWhichWhiteChange(ArrayList<LeaderCardToClient> leaderCardActive){ //CALLED ONLY IF THE PLAYER HAS 2 WHITE-CHANGE
        ArrayList<Boolean> leaderSelection = new ArrayList<>();
        leaderSelection.add(false);
        leaderSelection.add(false);

        String input;
        Scanner in = new Scanner(System.in);

        while(true) {
            System.out.print("\u001B[2J\u001B[3J\u001B[H");
            System.out.print("                                                  ╔═══════════════════════════════════╗\n" +
                    "                                                  ║  YOU GOT TWO MARKET WHITE CHANGE  ║\n" +
                    "                                                  ╚═══════════════════════════════════╝\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                                         -> Do you want to change the white marble into " + leaderCardActive.get(0).getResourceType() + "?" +
                    "\n" +
                    "                                                             ");

            input = in.nextLine();

            if (input.toLowerCase().equals("yes")) {
                leaderSelection.set(0, true);
            } else if (input.toLowerCase().equals("no")) {
                leaderSelection.set(0, false);
            } else {
                continue;
            }


            System.out.print("\n" +
                    "\n" +
                    "                                       \n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                                         -> Do you want to change the white marble into shield?\n" +
                    " \n" +
                    "                                                                 ");

            input = in.nextLine();

            if (input.toLowerCase().equals("yes")) {
                leaderSelection.set(1, true);
            } else if (input.toLowerCase().equals("no")) {
                leaderSelection.set(1, false);
            }

            if(leaderSelection.get(0)!=leaderSelection.get(1))
                break;
        }

        return leaderSelection;
    }

    /**
     * This class prints the "decision" made by Lorenzo and his position on the faithTrack
     * @param action represents the action taken by Lorenzo
     * @param position represents the position of Lorenzo on the board
     */
    public void printLorenzoTurn(String action, int position){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
        System.out.print("                                  ╔═╗     ╔═════╗ ╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═════╗                                  \n" +
                         "                                  ║ ║     ║ ╔═╗ ║ ║  ══ ║ ║ ╔═══╝ ║  ╚╝ ║ ╚═╗  ╔╝ ║ ╔═╗ ║\n" +
                         "                                  ║ ║     ║ ║ ║ ║ ║ ╔══╗║ ║ ╠═══  ║ ╔╗  ║  ╔╝ ╔╝  ║ ║ ║ ║\n" +
                         "                                  ║ ╚═══╗ ║ ╚═╝ ║ ║ ║  ║║ ║ ╚═══╗ ║ ║║  ║ ╔╝  ╚═╗ ║ ╚═╝ ║\n" +
                         "                                  ╚═════╝ ╚═════╝ ╚═╝  ╚╝ ╚═════╝ ╚═╝╚══╝ ╚═════╝ ╚═════╝\n" +
                "\n\n\n\n\n\n\n");
        System.out.print("                                                  ┌");
        for(int i=0; i<13 + action.length(); i++)
            System.out.print("─");
        System.out.print("┐\n");
        System.out.print("                                                  │ HAS PLAYED " + action + " │\n" +
                "                                                  └");
        for(int i=0; i< 13 + action.length(); i++)
            System.out.print("─");
        System.out.print("┘\n");

        System.out.print("\n\n\n\n\n\n\n");
        System.out.print("                                                   HE IS IN POSITION: ");
        if(position<10)
            System.out.print("0" + position);
        else
            System.out.print(position);
        System.out.println("/24                                                ");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}