package it.polimi.ingsw.Client.CLI;

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

    // Events that arrive from ConnectionToServer
    @Override
    public void visit(SendPlayerNameToClient playerName) {
        System.out.println("ho ricevuto il mio nome" + playerName.getPlayerName());
        namePlayer = playerName.getPlayerName();
        connectionToServer.setPlayerName(playerName.getPlayerName());
    }
    @Override
    public void visit(StartMatchToClient message) {
        System.out.print("ho ricevuto: ");
        System.out.println(message.getMessage());
    }

    @Override
    public void visit(NotifyClient message) {
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
    public void visit(MarketToClient market) {
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
