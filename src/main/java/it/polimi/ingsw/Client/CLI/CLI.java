package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.CLI.Views.NewDepositView;
import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Events.ServerToClient.*;
import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendDevelopmentCardAvailableToClient;
import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendDevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.MarketTurnToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.SendReorganizeDepositToClient;
import it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient.SendNumPlayerToClient;
import it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient.SendPlayerNameToClient;
import it.polimi.ingsw.Model.DevelopmentCard.CardColor;
import it.polimi.ingsw.Model.FaithTrack.FaithTrack;

import java.io.IOException;
import java.util.Scanner;

public class CLI implements EventToClientVisitor {
    private final ConnectionToServer connectionToServer;
    private String namePlayer;

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
        System.out.println("ho ricevuto il mio nome " + playerName.getPlayerName());
        namePlayer = playerName.getPlayerName();
        connectionToServer.setPlayerName(playerName.getPlayerName());
        System.out.println("scrivi il tuo nome");

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
        System.out.println("ho ricevuto la richiesta di numero di giocatori");
        System.out.println(numPlayer.getMessage());
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
        System.out.println("mi è arrivato un array forse");
        for(int i=0; i<leaderCardArray.getLeaderCardArray().size(); i++){
            System.out.println(leaderCardArray.getLeaderCardArray().get(i).getEffect());
            System.out.println(leaderCardArray.getLeaderCardArray().get(i).getVictoryPoint());
            System.out.println(leaderCardArray.getLeaderCardArray().get(i).getRequirement());
            System.out.println(leaderCardArray.getLeaderCardArray().get(i).getResourceType());
        }
        if(leaderCardArray.getLeaderCardArray().size()==4){
            System.out.println("dammi le due carte da scartare: num 1");
            Scanner scanIn = new Scanner(System.in);
            int num1 = scanIn.nextInt();
            System.out.println("num 2:");
            int num2 = scanIn.nextInt();
            connectionToServer.sendDiscardInitialLeaderCards(num1, num2);
        }
    }

    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------
    @Override
    public void visit(MarketTurnToClient market) {
        for(int i=0; i<market.getGrid().size(); i++){
            System.out.print("  " + market.getGrid().get(i).toString());
            if(i==3 || i==7 || i==11){
                System.out.println("");
            }
        }
        System.out.println(" ");
        System.out.println("out marble :" + market.getOutMarble().toString());
        System.out.println("dimmi una riga che scegli: ");
        Scanner scanIn = new Scanner(System.in);
        int line = scanIn.nextInt();
        connectionToServer.sendChooseLine(line);
    }

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
    public void visit(SendDevelopmentCardToClient developmentCard) {
        System.out.print("ho ricevuto: ");
        System.out.println(developmentCard.getColor() + developmentCard.getLevel() + developmentCard.getCost().toString() + developmentCard.getMaterialRequired().toString());
    }

    @Override
    public void visit(SendDevelopmentCardAvailableToClient availableDevelopmentCards) {
        System.out.println("ho ricevuto Array Dev disponibili: ");
        for (SendDevelopmentCardToClient[] cards : availableDevelopmentCards.getDevelopmentCardsAvailable()) {
            for (SendDevelopmentCardToClient card : cards)
                System.out.println("color: " + card.getColor() + " level: " + card.getLevel() + " cost: " + card.getCost() + " Req: " + card.getMaterialRequired() + " Grant: " + card.getProductionResult());
        }

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

        //System.out.println("scegli il colore della carta : 0 = green, 1 = blue, 2 = yellow, 3 = purple");
        //Scanner scanIn = new Scanner(System.in);
        //int color = scanIn.nextInt();
        //System.out.println("scegli il livello della carta: 0,1,2");
        //int level = scanIn.nextInt();
        System.out.println("Chose" + " " + color.name() + " " + level);
        connectionToServer.sendSelectedDevelopmentCard(colorForCard, level-1); //livello da 0 a 2

    }

    // ----------------------------------
    // OTHER EVENTS
    // ----------------------------------
    @Override
    public void visit(NotifyToClient message) {
        System.out.print("ho ricevuto: ");
        System.out.println(message.getMessage());
    }

    @Override
    public void visit(NewTurnToClient notify){
        System.out.println("è il mio turno: scrivo market, buydevelopment o usedevelopment (turn)");
        Scanner scanIn = new Scanner(System.in);
        String line = scanIn.nextLine();
        connectionToServer.sendTurnPlayed(line);
    }




}
