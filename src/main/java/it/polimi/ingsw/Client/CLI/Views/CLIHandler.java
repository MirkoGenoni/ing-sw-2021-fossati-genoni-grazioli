package it.polimi.ingsw.Client.CLI.Views;


import it.polimi.ingsw.Client.CLI.Views.LeaderCardView.LeaderCardView;
import it.polimi.ingsw.Client.CLI.Views.MarketView.MarketView;
import it.polimi.ingsw.Client.CLI.Views.MarketView.NewDepositView;
import it.polimi.ingsw.Client.CLI.Views.ProductionView.AdditionalProductionView;
import it.polimi.ingsw.Client.CLI.Views.ProductionView.BaseProduction;
import it.polimi.ingsw.Client.CLI.Views.ProductionView.DevelopmentCardView;
import it.polimi.ingsw.Client.CLI.Views.ProductionView.DevelopmentCardVisualization;
import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.MarketToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.PlayerInformationToClient;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class CLIHandler {
    private final ConnectionToServer connection;
    private String namePlayer;
    private Map<String, PlayerInformationToClient> players;
    private MarketView market;
    private DevelopmentCardView developmentCardForSale;
    private LeaderCardView leaderCardSelection;
    private DevelopmentCardVisualization currentBuying;
    private TotalResourceCounter totalResourceCounter;
    AllPlayersView allPlayersView;
    ArrayList<LeaderCardToClient> inactiveLeaders;

    private int tmpRoomNumber;
    private String newRoomText;
    private boolean isNewRoomOrNot;

    private boolean turnEnd;
    private int tmpNumPlayers;

    public CLIHandler(ConnectionToServer connection){
        this.connection = connection;
    }

    public int getTmpRoomNumber() {
        return tmpRoomNumber;
    }

    public boolean isNewRoomOrNot() {
        return isNewRoomOrNot;
    }

    public void newState(Map<String, PlayerInformationToClient> players,
                         MarketToClient market,
                         DevelopmentCardToClient[][] developmentCardToBuy){

        this.players = players;
        this.market = new MarketView(market);
        this.turnEnd = false;

        PlayerInformationToClient currentPlayer = players.get(this.namePlayer);
        this.totalResourceCounter = new TotalResourceCounter(currentPlayer.getStrongBox(), currentPlayer.getDeposit(), currentPlayer.getAdditionalDeposit());

        this.developmentCardForSale = new DevelopmentCardView(developmentCardToBuy, totalResourceCounter);

        for(LeaderCardToClient l: players.get(namePlayer).getLeaderCardActive())
            for(int i=0; i<inactiveLeaders.size();i++)
                if(l.getNameCard().equals(inactiveLeaders.get(i).getNameCard()))
                    inactiveLeaders.set(i, null);

        this.allPlayersView = new AllPlayersView(this.players, this.namePlayer, this.inactiveLeaders);
    }

    public void leaderCardSelection(ArrayList<LeaderCardToClient> received, boolean initial, Map<String, Integer> totalReceived){
            this.leaderCardSelection = new LeaderCardView(new ArrayList<>(received), totalReceived);

            if(initial){
                this.inactiveLeaders = new ArrayList<>();
                int[] receive = leaderCardSelection.StartInitialLeaderCardView();
                this.connection.sendDiscardInitialLeaderCards(receive[0], receive[1]);
                for(int i=0; i<received.size(); i++) {
                    if (i != receive[0] && i != receive[1])
                        this.inactiveLeaders.add(received.get(i));
                }
            } else {
                ArrayList<Integer> receive = leaderCardSelection.StartCardView();
                connection.sendLeaderCardTurn(receive);
            }
    }

    public void newTurn(){
        
        this.turnEnd = false;
        
        while(!this.turnEnd) {
            NewTurnView chooseTurn = new NewTurnView();
            String in = chooseTurn.startTurnChoise();

            switch (in) {
                case "market":
                    market.launchChoiseView();
                    int line1 = market.getChoiseLine();
                    ArrayList<Boolean> tmp = marketWhiteChangeActive(players.get(this.namePlayer).getLeaderCardActive());
                    if (market.isTurnEnd())
                        connection.sendChooseLine(line1, tmp);
                    this.turnEnd = market.isTurnEnd();
                    break;
                case "buydevelopment":
                    selectedDevelopmentCard(this.developmentCardForSale);
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
                    AdditionalProductionView additionalProductionView = new AdditionalProductionView(leaderCardPower);

                    this.turnEnd = choseDevelopment(developmentBoard, additionalProductionView);
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

    public void selectNewDeposit(ArrayList<Resource> deposit, ArrayList<Resource> marketReceived,
                                  boolean additional, ArrayList<Resource> type, ArrayList<Resource> state){

        NewDepositView tmp = new NewDepositView(deposit, marketReceived, additional, type, state);
        tmp.LaunchView();
        connection.sendNewDepositState(tmp.getDepositState(), tmp.getMarketReceived(), tmp.isAdditionalDeposit(),tmp.getAdditionalDepositState());
    }

    private void selectedDevelopmentCard(DevelopmentCardView in){
        in.startCardForSaleSelection();

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




    private boolean choseDevelopment(DevelopmentCardView developmentCardBoard, AdditionalProductionView additionalProductionView){

        boolean tmp = true;

        char answer = selectActivation("Do you want to Activate the BASE Production? Y/N?");

        boolean useBaseProduction = false;
        ArrayList<Resource> input = new ArrayList<>();

        ProductedMaterials prodottoBaseProd = null;
        input.add(null);
        input.add(null);
        input.add(null);

        if (answer == 'Y') {
            useBaseProduction = true;
            BaseProduction baseProduction = new BaseProduction();
            baseProduction.startBaseProduction();
            tmp = baseProduction.isTurnEnd();
            if(tmp) {
                input = baseProduction.getResources();
                prodottoBaseProd = ProductedMaterials.valueOf(input.get(2).name());
            }
        }

        if(answer == 'Y' && !tmp)
            return false;

        answer = selectActivation("Do you want to Activate one of the LEADER Production? Y/N?");

        ArrayList<String> leaderCardPower = new ArrayList<>();
        ArrayList<Boolean> activation = new ArrayList<>();
        ArrayList<Resource> materialLeader = new ArrayList<>();

        if (answer == 'Y') {
            additionalProductionView.startAdditionalProductionView();
            tmp = additionalProductionView.isTurnEnd();
            if(tmp) {
                activation = additionalProductionView.getActivation();
                materialLeader = additionalProductionView.getRequested();
            }
        }

        if(answer == 'Y' && !tmp)
            return false;


        answer = selectActivation("Do you want to use any of yours DEVELOPMENT CARDS? Y/N?");
        ArrayList<Boolean> useDevelop = new ArrayList<>();
        for (int i=0; i<3; i++)
            useDevelop.add(false);

        if(answer == 'Y'){
            developmentCardBoard.startProductionCardBoardView();
            tmp = developmentCardBoard.isTurnEnd();
            if(tmp)
                useDevelop = developmentCardBoard.getActivation();
        }

        if(answer == 'Y' && !tmp)
            return false;

        /*DEBUG
        System.out.println("invio questi dati:");
        System.out.println("use_baseprod: " + useBaseProduction);
        if(useBaseProduction)
            System.out.println("selected for base prod: "+ input.get(0).toString() + " "+ input.get(1).toString() +"--> " + input.get(2).toString());
        System.out.println("use leaders --> "+ activation.toString());
        System.out.println("material leaders --> "+ materialLeader.toString());
        System.out.println("use develop --> "+ useDevelop.toString());*/

        //VANNO MANDATE TUTTE COME PRODUCTION RESULT

        //MANDA EVENTO CON:  BOOL_USA_GAMEPROD,PROD1,PROD2,PRODCHOOSE//ARRAY_LEADERS-> MATERIAL 1ST LEADER/MATERIAL 2ND LEADER//ARRAY_DEVELOP

        connection.sendSelectedProductionDevelopmentCard(useBaseProduction, input.get(0), input.get(1), prodottoBaseProd,
                activation, materialLeader, useDevelop);

        return true;

    }

    //TODO ALMENO UN BOOLEAN A TRUE PER LEADER WHITE CHANGE
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
              leaderCardWhiteChange = selectWitchWhiteChange(leaderCardActive); //torna array con un solo true e un solo false
            }
        }
        return leaderCardWhiteChange;
    }


    private ArrayList<Boolean> selectWitchWhiteChange(ArrayList<LeaderCardToClient> leaderCardActive){ //CALLED ONLY IF THE PLAYER HAS 2 WHITE-CHANGE
        char answer;
        ArrayList<Boolean> leaderSelection = new ArrayList<>();
        leaderSelection.add(false);
        leaderSelection.add(false);

        do{
            for (int i=0;i<leaderCardActive.size(); i++) {
                answer = selectActivation("Do you want to obtain " + leaderCardActive.get(i).getResourceType() + " from the White Marble? Y/N?");
                if (answer == 'Y') {
                    leaderSelection.set(i, true); //TODO FORSE BREAK
                } else {
                    leaderSelection.set(i, false);
                }
            }
        }while(leaderSelection.get(0) == leaderSelection.get(1)); //TODO FARE MESSAGGIO SE SCRIVE STUPIDAGGINI

        return leaderSelection;
    }


    private char selectActivation (String message) {
        Scanner scan = new Scanner(System.in);
        char choose;
        boolean isValid;
        do {
            System.out.println(message);
            String input;
            input = scan.nextLine();
            isValid = false;   //CONTROL
            input = input.trim();
            input = input.toUpperCase();
            choose = input.charAt(0);
            if (input.length() == 1 && (choose == 'Y' || choose == 'N')) {
                System.out.println("selected: " + choose);
                isValid = true;
            }
            if (!isValid) {
                Messages error = new Messages("Please select a correct option", true) ;
                isValid = false;
            }
        } while (!isValid);

        return choose;
    }

    public void initialResourceSelection(int numResources, ArrayList<Resource> depositState){
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
    }

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

    public void insertInitialData(String DataRequired){
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
                String tmpName;

                System.out.print("                                    INSERT NAME: ");

                tmpName = in.nextLine();
                this.namePlayer = tmpName;

                connection.setPlayerName(tmpName);
                connection.sendPlayerName(tmpName);

                notDone = false;
            } else if (!DataRequired.equals("isNewRoom") && !DataRequired.equals("roomNumber")) {
                System.out.println("                                    INSERT NAME: " + "\u001B[92m" + this.namePlayer + "\u001B[0m" + "\n");
            }

            if (DataRequired.equals("numberOfPlayers")) {
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

                this.tmpNumPlayers = tmpNumPlayers;
                notDone = false;
                connection.sendNumPlayer(tmpNumPlayers);
            }
        }
    }
}
