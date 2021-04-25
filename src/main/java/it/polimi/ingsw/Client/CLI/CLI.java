package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.CLI.Views.NewDepositView;
import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Events.ServerToClient.*;

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

    @Override
    public void visit(SendReorganizeDepositToClient newResources) {
        System.out.println("mi è arrivato il deposito");

        NewDepositView view = new NewDepositView(newResources.getDepositResources(), newResources.getMarketResources());

        try{
            view.LaunchView();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

        connectionToServer.sendNewDepositState(view.getDepositState(), view.getMarketReceived());
        // qui dovrebbe riorganizzare il nuovo deposito
        // il metodo nel connectionToServer è il newDeposit state
    }


    @Override
    public void visit(NotifyToClient message) {
        System.out.print("ho ricevuto: ");
        System.out.println(message.getMessage());
    }

    @Override
    public void visit(NewTurnToClient notify){
        System.out.println("è il mio turno: scrivo market, buydevelopment o usedevelopment (turn)");
        Scanner scanIn = new Scanner(System.in); // da risolvere
        String line = scanIn.nextLine();
        connectionToServer.sendTurnPlayed(line);
    }

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


}
