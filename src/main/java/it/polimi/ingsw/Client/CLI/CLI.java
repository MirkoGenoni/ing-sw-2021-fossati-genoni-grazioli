package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.CLI.Views.NewDepositView;
import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Events.ServerToClient.*;
import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendReselectedDevelopmentCardAvailableToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendSpaceDevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.SendReorganizeDepositToClient;
import it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient.SendNumPlayerToClient;
import it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient.SendPlayerNameToClient;
import it.polimi.ingsw.Model.DevelopmentCard.CardColor;
import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Resource.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CLI implements EventToClientVisitor {
    private final ConnectionToServer connectionToServer;
    private String namePlayer;
    private final Thread asyncPrint = new Thread(()->{
        System.out.printf("                                                                                                                          \n" +
                          "                                                                                                                          \n" +
                          "                                                                                                                          \n");
        System.out.print("                                   WAITING FOR OTHER PLAYERS");

        while(true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.print(". ");
                TimeUnit.SECONDS.sleep(1);
                System.out.print(". ");
                TimeUnit.SECONDS.sleep(1);
                System.out.print(".");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.printf("\u001B[2J\u001B[3J\u001B[H");
                break;
            }

            for (int i = 0; i < 5; i++)
                System.out.printf("\b");

            System.out.printf("     ");

            for (int i = 0; i < 5; i++)
                System.out.printf("\b");

        }});


    public CLI(ConnectionToServer connectionToServer) {
        this.connectionToServer = connectionToServer;
    }

    public void receiveEvent(EventToClient event){
        event.acceptVisitor(this);
    }

    // Events that arrive from ConnectionToServer, so from the server
    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------
    @Override
    public void visit(SendPlayerNameToClient playerName) {
        namePlayer = playerName.getPlayerName();
        connectionToServer.setPlayerName(playerName.getPlayerName());

        System.out.printf("                                                                                                                          \n");

        System.out.print("                                   INSERT NAME: ");

        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scanner scanIn = new Scanner(System.in);


        String line = scanIn.nextLine();
        connectionToServer.sendNewPlayerName(line);
        namePlayer = line;
        connectionToServer.setPlayerName(line);

    }

    @Override
    public void visit(SendNumPlayerToClient numPlayer) {
        //System.out.println("ho ricevuto la richiesta di numero di giocatori");
        //System.out.println(numPlayer.getMessage());

        System.out.printf("                                                                                                                          \n");

        System.out.print("                                   YOU'RE THE FIRST PLAYER! ");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0; i<60; i++)
            System.out.printf("\b");

        System.out.print("                                   INSERT NUMBER OF PLAYERS: ");

        Scanner scanIn = new Scanner(System.in);
        int num = scanIn.nextInt();
        connectionToServer.sendNumPlayer(num);
    }

    // -------------------------------------------
    // EVENTS THAT RECEIVE LEADER CARD INFORMATION
    // -------------------------------------------
    @Override
    public void visit(SendLeaderCardToClient leaderCard) {
        System.out.println("mi è arrivata la leaderCArd");
        System.out.println(leaderCard.getEffect());
        System.out.println(leaderCard.getVictoryPoint());
        System.out.println(leaderCard.getRequirement());
        System.out.println(leaderCard.getResourceType());
    }

    @Override
    public void visit(SendArrayLeaderCardsToClient leaderCardArray) {

        if (leaderCardArray.isInitialLeaderCards()) {
            System.out.println("mi è arrivato un array forse");
            for (int i = 0; i < leaderCardArray.getLeaderCardArray().size(); i++) {
                System.out.println(leaderCardArray.getLeaderCardArray().get(i).getEffect());
                System.out.println(leaderCardArray.getLeaderCardArray().get(i).getVictoryPoint());
                System.out.println(leaderCardArray.getLeaderCardArray().get(i).getRequirement());
                System.out.println(leaderCardArray.getLeaderCardArray().get(i).getResourceType());
            }
            if (leaderCardArray.getLeaderCardArray().size() == 4) {
                System.out.println("dammi le due carte da scartare: num 1");
                Scanner scanIn = new Scanner(System.in);
                int num1 = scanIn.nextInt();
                System.out.println("num 2:");
                int num2 = scanIn.nextInt();
                connectionToServer.sendDiscardInitialLeaderCards(num1, num2);
            }
        }else{ //false
            System.out.println("Mi sono arrivati leader, non sono a inizio gioco");
            for (int i = 0; i < leaderCardArray.getLeaderCardArray().size(); i++) {
                System.out.println(leaderCardArray.getLeaderCardArray().get(i).getEffect());
                System.out.println(leaderCardArray.getLeaderCardArray().get(i).getVictoryPoint());
                System.out.println(leaderCardArray.getLeaderCardArray().get(i).getRequirement());
                System.out.println(leaderCardArray.getLeaderCardArray().get(i).getResourceType());
            }

            System.out.println("Do you want to play or discard any leader? 0-To do nothing/ 1-To play / 2-To discard ");
            ArrayList<Integer> arrayToSend = new ArrayList<>();
            for (int i = 0; i < leaderCardArray.getLeaderCardArray().size(); i++){
                System.out.println("The number " + i + " card:");
                Scanner scanIn = new Scanner(System.in);
                int input = scanIn.nextInt();                   //ATTENTO ALL'INPUT NO DIVERSO 0-1-2 (Forse meglio un while)
                arrayToSend.add(input);
            }

            connectionToServer.sendLeaderCardTurn(arrayToSend);
        }

    }
    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------

    @Override
    public void visit(SendReorganizeDepositToClient newResources) {
        System.out.println("mi è arrivato il deposito");

        NewDepositView view = new NewDepositView(newResources.getDepositResources(), newResources.getMarketResources());
        view.LaunchView();

        connectionToServer.sendNewDepositState(view.getDepositState(), view.getMarketReceived());
    }

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------

    @Override
    public void visit(SendReselectedDevelopmentCardAvailableToClient message) {
        System.out.println(message.getMessage());
        selectedDevelopmentCard();
    }

    @Override
    public void visit(SendSpaceDevelopmentCardToClient developmentCardSpace) {
        boolean bought = true;
        System.out.println("You can put the bought card in these positions:");
        for (int i=0; i<developmentCardSpace.getDevelopmentCardSpace().size(); i++) {
            if (developmentCardSpace.getDevelopmentCardSpace().get(i).equals(true))
                System.out.println(" " + i);
        }

        int selectedPosition;
        do {
            bought=true;
            System.out.println("Select one of the valid positions:");
            Scanner scanIn = new Scanner(System.in);
            selectedPosition = scanIn.nextInt();
            if (selectedPosition>2 || selectedPosition<0 || !developmentCardSpace.getDevelopmentCardSpace().get(selectedPosition)) {
                System.out.println("You can't put the card in this position");
                bought = false;
            }
        }while (!bought);
        connectionToServer.sendSelectedDevelopmentCardSpace(selectedPosition);
    }

    // ----------------------------------
    // OTHER EVENTS
    // ----------------------------------
    @Override
    public void visit(NotifyToClient message) {

        switch(message.getMessage()){

            case "WaitForOtherPlayers":
                asyncPrint.start();
                break;

            case "AllPlayersConnected":
                asyncPrint.interrupt();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Error in sleep");
                }

                break;
        }

        if(!message.getMessage().equals("WaitForOtherPlayers") && !message.getMessage().equals("AllPlayersConnected")){
            System.out.print("ho ricevuto: ");
            System.out.println(message.getMessage());
        }
    }

    @Override
    public void visit(NewTurnToClient newTurn){
        for(int i=0; i<newTurn.getMarket().getGrid().size(); i++){
            System.out.print("  " + newTurn.getMarket().getGrid().get(i).toString());
            if(i==3 || i==7 || i==11){
                System.out.println("");
            }
        }
        System.out.println(" ");
        System.out.println("out marble :" + newTurn.getMarket().getOutMarble().toString());

        System.out.println("ho ricevuto Array Dev disponibili: ");
        for (DevelopmentCardToClient[] cards : newTurn.getDevelopmentCards()) {
            for (DevelopmentCardToClient card : cards)
                System.out.println("color: " + card.getColor() + " level: " + card.getLevel() + " cost: " + card.getCost() + " Req: " + card.getMaterialRequired() + " Grant: " + card.getProductionResult());
        }

        System.out.println("il mio deposito");
        System.out.println(newTurn.getDepositState());

        System.out.println("la mia strongbox");
        System.out.println(newTurn.getStrongbox().toString());

        System.out.println("le leadercard attive");
        System.out.println(newTurn.getLeaderCarsActive().toString());

        System.out.println("le mie development");
        System.out.println(newTurn.getDevelopmentCard().toString());

        System.out.println("è il mio turno: scrivo market, buydevelopment o usedevelopment (turn)");
        Scanner scanIn = new Scanner(System.in);
        String line = scanIn.nextLine();
        if(line.equals("market")){
            System.out.println("dimmi una riga che scegli: ");
            Scanner scanIn1 = new Scanner(System.in);
            int line1 = scanIn1.nextInt();
            connectionToServer.sendChooseLine(line1);
        }else if(line.equals("buydevelopment")){
            selectedDevelopmentCard();
        }else if(line.equals("usedevelopment")){
            choseDevelopment(newTurn.getDevelopmentCard(), newTurn.getLeaderCarsActive());
        }
        else if(line.equals("turn")){
            connectionToServer.sendTurnPlayed(line);
        }

    }



    private void selectedDevelopmentCard(){
        boolean isValid;
        Integer level = null;
        CardColor color = null;
        Scanner scan = new Scanner(System.in);

        do {
            System.out.println("\nSELECT WITH 'LEVEL,COLOR' WITCH CARD YOU WANT TO BUY");
            String input;
            input = scan.nextLine();
            input = input.trim();
            String delimits = ",";
            String[] strArray = input.split(delimits);
            if (strArray.length == 2) { //lenght!=0
                strArray[1] = strArray[1].toUpperCase();
                isValid = true;

                try {
                    level = Integer.parseInt(strArray[0]);
                    color = CardColor.valueOf(strArray[1]);
                    //System.out.println("level:" + level + "color: " + color);
                    if (level < 1 || level > 3)
                        throw new IllegalArgumentException();
                } catch (IllegalArgumentException e) {
                    System.out.println("Non valid Argument");
                    isValid = false;
                }
            }
            else {
                System.out.println("Non valid Argument");
                isValid = false;
            }

        } while (!isValid);


        int colorForCard;

        switch (color) {
            case GREEN:
                colorForCard = 0;
                break;
            case BLUE:
                colorForCard = 1;
                break;
            case YELLOW:
                colorForCard = 2;
                break;
            case PURPLE:
                colorForCard = 3;
                break;
            default:
                throw new RuntimeException(); //attention Exception
        }
        System.out.println("Chose" + " " + color.name() + " " + level);
        connectionToServer.sendSelectedDevelopmentCard(colorForCard, level-1); //livello da 0 a 2
    }




    private void choseDevelopment(ArrayList<DevelopmentCardToClient> activeDevCards, ArrayList<SendLeaderCardToClient> activeLeaderCards){

        System.out.println("Do you want to Activate the BASE Production? Y/N?");
        char answer = selectActivation();
        boolean useBaseProduction = false;
        Resource resourceRequested1 = null;
        Resource resourceRequested2 = null;
        Resource resourceGranted = null;

        if (answer == 'Y') {
            useBaseProduction = true;
            resourceRequested1 = selectResource("Select the first MATERIAL for the base production");
            resourceRequested2 = selectResource("Select the second MATERIAL for the base production");
            resourceGranted = selectResource("Select which resource you want to obtain from the base production");

            System.out.println("selected "+ resourceRequested1 + " "+ resourceRequested2+"--> "+resourceGranted);
        }


        System.out.println("Do you want to Activate one of the LEADER Production? Y/N?");
        answer = selectActivation();
        ArrayList<Boolean> useLeaders = new ArrayList<>();
        for (int i=0; i<2; i++)
            useLeaders.add(false);
        ArrayList<Resource> materialLeader = new ArrayList<>();
        for (int i=0; i<2; i++)
            materialLeader.add(null);

        if(answer == 'Y'){
                for (int i=0; i<activeLeaderCards.size(); i++){          //control if there's an active LeaderCard additionalProd
                    if(activeLeaderCards.get(i).getEffect().equals("additionalProduction")) {
                        System.out.println("Do you want to use production of ACTIVE LEADER number " + i + " ?" + " Y/N?");
                        answer = selectActivation();
                        if (answer == 'Y') {
                            useLeaders.set(i, true);
                            Resource resource = selectResource("Select RESOURCE you want from the Leader");
                            materialLeader.set(i, resource);
                        }
                    }
                }
        }


        System.out.println("Do you want to use any of yours DEVELOPMENT CARDS? Y/N?");
        answer = selectActivation();
        ArrayList<Boolean> useDevelop = new ArrayList<>();
        for (int i=0; i<3; i++)
            useDevelop.add(false);

        if(answer == 'Y'){
            for(int i=0; i<3; i++) {
                if (activeDevCards.get(i) != null) {
                    System.out.println("Do you want to use the ACTIVE PRODUCTION number: " + i + " ?" + " Y/N?");
                    answer = selectActivation();
                    if (answer == 'Y')
                        useDevelop.set(i, true);
                }
            }
        }

        //DEBUG
        System.out.println("invio questi dati:");
        System.out.println("use_baseprod: " + useBaseProduction);
        System.out.println("selected for base prod: "+ resourceRequested1 + " "+ resourceRequested2+"--> "+resourceGranted);
        System.out.println("use leaders --> "+ useLeaders.toString());
        System.out.println("material leaders --> "+ materialLeader.toString());
        System.out.println("use develop --> "+ useDevelop.toString());

        ProductedMaterials prodottoBaseProd = null;
        if (resourceGranted!=null) {
            prodottoBaseProd = ProductedMaterials.valueOf(resourceGranted.name());
        }

        //VANNO MANDATE TUTTE COME PRODUCTION RESULT

        //MANDA EVENTO CON:  BOOL_USA_GAMEPROD,PROD1,PROD2,PRODCHOOSE//ARRAY_LEADERS-> MATERIAL 1ST LEADER/MATERIAL 2ND LEADER//ARRAY_DEVELOP

        connectionToServer.sendSelectedProductionDevelopmentCard(useBaseProduction, resourceRequested1, resourceRequested2, prodottoBaseProd,
                                                                    useLeaders, materialLeader, useDevelop);

        }




        /*
     --------------------------------------------------------------
      METODI PER PARSE DELLA SCELTA --> Y/N E UNA RESOURCE
     --------------------------------------------------------------
    */

    // PARSA LA SCELTA Y/N
    private char selectActivation () {
        Scanner scan = new Scanner(System.in);
        char choose;
        boolean isValid;
        do {
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
                System.out.println("Non valid Argument");
                isValid = false;
            }
        } while (!isValid);

        return choose;
    }


    //PARSA LA SCELTA DI UNA RISORSA
    private Resource selectResource(String message){
        Scanner scan = new Scanner(System.in);
        boolean validMaterial;
        Resource resource = null;
        do {
            validMaterial = true;
            System.out.println(message);
            String materialInput1 = scan.nextLine();
            materialInput1 = materialInput1.trim();
            materialInput1 = materialInput1.toUpperCase();
            try {
                resource = Resource.valueOf(materialInput1);
            } catch (IllegalArgumentException e) {
                System.out.println("Selected a non valid material, please rewrite it");
                validMaterial = false;
            }
        } while (!validMaterial);

        return resource;
    }







}
