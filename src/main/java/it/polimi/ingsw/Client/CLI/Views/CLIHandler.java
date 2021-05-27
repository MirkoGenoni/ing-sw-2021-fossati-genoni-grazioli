package it.polimi.ingsw.Client.CLI.Views;


import it.polimi.ingsw.Client.CLI.Views.LeaderCardView.LeaderCardView;
import it.polimi.ingsw.Client.CLI.Views.MarketView.MarketView;
import it.polimi.ingsw.Client.CLI.Views.MarketView.NewDepositView;
import it.polimi.ingsw.Client.CLI.Views.ProductionView.AdditionalProductionView;
import it.polimi.ingsw.Client.CLI.Views.ProductionView.BaseProduction;
import it.polimi.ingsw.Client.CLI.Views.ProductionView.DevelopmentCardView;
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
    ConnectionToServer connection;
    String namePlayer;
    Map<String, PlayerInformationToClient> players;
    MarketView market;
    DevelopmentCardView developmentCardForSale;
    LeaderCardView leaderCardSelection;

    public CLIHandler(ConnectionToServer connection){
        this.connection = connection;
    }

    public void newState(String namePlayer,
                         Map<String, PlayerInformationToClient> players,
                         MarketToClient market,
                         DevelopmentCardToClient[][] developmentCardToBuy){

        this.namePlayer = namePlayer;
        this.players = players;
        this.market = new MarketView(market);
        this.developmentCardForSale = new DevelopmentCardView(developmentCardToBuy);
    }

    public void leaderCardSelection(ArrayList<LeaderCardToClient> received, boolean initial){
            this.leaderCardSelection = new LeaderCardView(received);
            if(initial){
                int[] receive = leaderCardSelection.StartInitialLeaderCardView();
                this.connection.sendDiscardInitialLeaderCards(receive[0], receive[1]);
            } else {
                ArrayList<Integer> receive = leaderCardSelection.StartCardView();
                connection.sendLeaderCardTurn(receive);
            }
    }

    public void newTurn(){
        NewTurnView chooseTurn = new NewTurnView();
        String out = chooseTurn.startTurnChoise();
        this.startTurn(out);
    }

    public void startTurn(String in){
        switch(in){
            case "market":
                int line1 = market.launchChoiseView();
                ArrayList<Boolean> tmp = marketWhiteChangeActive(players.get(this.namePlayer).getLeaderCardActive());
                connection.sendChooseLine(line1, tmp);
                break;
            case "buydevelopment":
                DevelopmentCardView BoardCard = new DevelopmentCardView(players.get(this.namePlayer).getDevelopmentCardPlayer());
                selectedDevelopmentCard(this.developmentCardForSale);
                break;
            case "usedevelopment":
                DevelopmentCardView developmentBoard = new DevelopmentCardView(this.players.get(this.namePlayer).getDevelopmentCardPlayer());

                ArrayList<String> leaderCardPower = new ArrayList<>();
                for (int j=0; j<players.get(this.namePlayer).getLeaderCardActive().size(); j++){          //control if there's an active LeaderCard additionalProd
                    if(players.get(this.namePlayer).getLeaderCardActive().get(j).getEffect().equals("additionalProduction")) {
                        leaderCardPower.add(players.get(this.namePlayer).getLeaderCardActive().get(j).getResourceType());
                    }
                }
                AdditionalProductionView additionalProductionView = new AdditionalProductionView(leaderCardPower);

                choseDevelopment(developmentBoard, additionalProductionView);
                break;
            case "turn":
                connection.sendTurnPlayed(in);
                break;

            case "viewBoard":
                //TODO: aggiungere scelta visualizzazione gameboard giocatore corrente e altri giocatori
                break;
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
        connection.sendSelectedDevelopmentCard(colorForCard, num-1); //livello da 0 a 2
    }




    private void choseDevelopment(DevelopmentCardView developmentCardBoard, AdditionalProductionView additionalProductionView){

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
            input = baseProduction.getResources();
            prodottoBaseProd = ProductedMaterials.valueOf(input.get(2).name());
        }


        answer = selectActivation("Do you want to Activate one of the LEADER Production? Y/N?");

        ArrayList<String> leaderCardPower = new ArrayList<>();
        ArrayList<Boolean> activation = new ArrayList<>();
        ArrayList<Resource> materialLeader = new ArrayList<>();

        if(answer == 'Y'){

            additionalProductionView.startAdditionalProductionView();
            activation = additionalProductionView.getActivation();
            materialLeader = additionalProductionView.getRequested();
        }


        answer = selectActivation("Do you want to use any of yours DEVELOPMENT CARDS? Y/N?");
        ArrayList<Boolean> useDevelop = new ArrayList<>();
        for (int i=0; i<3; i++)
            useDevelop.add(false);

        if(answer == 'Y'){
            /*for(int i=0; i<3; i++) {
                if (activeDevCards.get(i) != null) {
                    answer = selectActivation("Do you want to use the ACTIVE PRODUCTION number: " + i + " ?" + " Y/N?");
                    if (answer == 'Y')
                        useDevelop.set(i, true);
                }
            }*/
            developmentCardBoard.startProductionCardBoardView();
            useDevelop = developmentCardBoard.getActivation();
        }

        //DEBUG
        System.out.println("invio questi dati:");
        System.out.println("use_baseprod: " + useBaseProduction);
        if(useBaseProduction)
            System.out.println("selected for base prod: "+ input.get(0).toString() + " "+ input.get(1).toString() +"--> " + input.get(2).toString());
        System.out.println("use leaders --> "+ activation.toString());
        System.out.println("material leaders --> "+ materialLeader.toString());
        System.out.println("use develop --> "+ useDevelop.toString());

        //VANNO MANDATE TUTTE COME PRODUCTION RESULT

        //MANDA EVENTO CON:  BOOL_USA_GAMEPROD,PROD1,PROD2,PRODCHOOSE//ARRAY_LEADERS-> MATERIAL 1ST LEADER/MATERIAL 2ND LEADER//ARRAY_DEVELOP

        connection.sendSelectedProductionDevelopmentCard(useBaseProduction, input.get(0), input.get(1), prodottoBaseProd,
                activation, materialLeader, useDevelop);

    }

    private ArrayList<Boolean> marketWhiteChangeActive(ArrayList<LeaderCardToClient> leaderCardActive){
        ArrayList<Boolean> leaderCardWhiteChange = new ArrayList<>();
        for(int i=0; i< leaderCardActive.size(); i++){
            if(leaderCardActive.get(i).getEffect().equals("marketWhiteChange")){
                char answer = selectActivation("vuoi usare la carta attiva marketWhiteChange" + leaderCardActive.get(i) + "?");
                if(answer == 'Y'){
                    leaderCardWhiteChange.add(true);
                }else{
                    leaderCardWhiteChange.add(false);
                }
            }else{
                leaderCardWhiteChange.add(false);
            }
        }
        return leaderCardWhiteChange;
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