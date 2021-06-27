package it.polimi.ingsw.Client.CLI.Views;

import it.polimi.ingsw.Client.CLI.Views.LeaderCardView.LeaderCardView;
import it.polimi.ingsw.Client.CLI.Views.MarketView.NewDepositView;
import it.polimi.ingsw.Client.CLI.Views.ProductionView.DevelopmentCardView;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.PlayerInformationToClient;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class AllPlayersView {
    Map<String, PlayerInformationToClient> information;
    PlayerInformationToClient player;
    ArrayList<LeaderCardToClient> inactiveLeaders;
    DevelopmentCardView developmentCardView;
    NewDepositView depositState;
    LeaderCardView activeLeaderCard;
    LeaderCardView inactiveLeaderCard;
    FaithTrackView faithTrackView;

    AllPlayersView(Map<String, PlayerInformationToClient> information, String currentPlayer, ArrayList<LeaderCardToClient> inactiveLeaders){
        this.information = information;
        this.player = information.get(currentPlayer);
        this.inactiveLeaders = inactiveLeaders;

        updateInfo();
        //LeaderCardView leaderCardRack = new LeaderCardView();
    }

    public void test() {
        while(true) {
            this.printSelection();
            String input = "";
            Scanner in = new Scanner(System.in);

            if (in.hasNext())
                input = in.nextLine();

            input = input.toLowerCase();

            if(input.equals("quit"))
                break;

            if (this.information.containsKey(input)) {
                this.player = this.information.get(input);
                updateInfo();
            }

            switch(input){
                case "deposit":
                    this.depositState.draw();
                    break;
                case"development":
                    this.developmentCardView.draw();
                    break;
                case"leaders":
                    this.activeLeaderCard.draw();
                    break;
                case"myinactiveleader":
                    this.inactiveLeaderCard.draw();
                    break;
                case"faithtrack":
                    this.faithTrackView.draw();
            }
        }
    }

    private void updateInfo(){
        this.developmentCardView = new DevelopmentCardView(player.getDevelopmentCardPlayer(), null);
        this.depositState = new NewDepositView(player.getDeposit(), player.getStrongBox(), player.getAdditionalDepositType(), player.getAdditionalDeposit());
        this.activeLeaderCard = new LeaderCardView(player.getLeaderCardActive(), null);
        this.inactiveLeaderCard = new LeaderCardView(this.inactiveLeaders, null);
        this.faithTrackView = new FaithTrackView(player.getPopeFavorTiles(), player.getFaithMarkerPosition());
    }

    private void printSelection(){
        System.out.print("\u001B[2J\u001B[3J\u001B[H");

        System.out.print("                                                  ╔═══════════════╗\n" +
                "                                                  ║  VIEW CHOICE  ║\n" +
                "                                                  ╚═══════════════╝\n" +
                "\n" +
                "\n" +
                "\n" +
                "                                               YOU CAN CHOOSE BETWEEN:       \n" +
                "                           \n" +
                "\n" +
                "\n" +
                "                   -> Select another player: ");

        System.out.print("[");
        for(String p: information.keySet()) {
            System.out.print(p);
            System.out.print(", ");
        }
        System.out.print("\b\b");
        System.out.print("]");

        System.out.print("\n" +
                "                                           \n" +
                "\n" +
                "                   -> Select one of the part from the board:\n\n" +
                "                      [deposit, development, leaders, myinactiveleader, faithtrack]\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "                                         \n" +
                "                   " + "\u001B[4m" + "PLAYER CURRENTLY SELECTED" + "\u001B[0;0m" + ": " + player.getPlayerNameSend() + "\n" +
                "                                          \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "                  INSERT YOUR CHOISE: ");
    }
}
