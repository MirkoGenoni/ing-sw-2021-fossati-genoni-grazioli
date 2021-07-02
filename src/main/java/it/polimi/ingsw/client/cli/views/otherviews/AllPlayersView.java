package it.polimi.ingsw.client.cli.views.otherviews;

import it.polimi.ingsw.client.cli.views.leadercardview.LeaderCardView;
import it.polimi.ingsw.client.cli.views.marketview.NewDepositView;
import it.polimi.ingsw.client.cli.views.productionview.DevelopmentCardView;
import it.polimi.ingsw.events.servertoclient.supportclass.LeaderCardToClient;
import it.polimi.ingsw.events.servertoclient.supportclass.PlayerInformationToClient;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * This class permit to a player to view other player's board or section of the board
 *
 * @author Mirko Genoni
 */
public class AllPlayersView {
    Map<String, PlayerInformationToClient> information;
    PlayerInformationToClient player;
    ArrayList<LeaderCardToClient> inactiveLeaders;
    DevelopmentCardView developmentCardView;
    NewDepositView depositState;
    LeaderCardView activeLeaderCard;
    LeaderCardView inactiveLeaderCard;
    FaithTrackView faithTrackView;

    /**
     * Constructor of the class initializes all the data structures
     * @param information contains all the information of all the players
     * @param currentPlayer contains the name of the current player
     * @param inactiveLeaders contains the leaderCard in hand of the current player
     */
    public AllPlayersView(Map<String, PlayerInformationToClient> information, String currentPlayer, ArrayList<LeaderCardToClient> inactiveLeaders){
        this.information = information;
        this.player = information.get(currentPlayer);
        this.inactiveLeaders = inactiveLeaders;

        updateInfo();
    }

    /**
     * Handles the user input for the selection of the player's gameboard the current player wants
     * to see or the part of the gameboard he wants to see
     */
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

    /**
     * This method updates the information when the user select another player or go back to his gameboard
     */
    private void updateInfo(){
        this.developmentCardView = new DevelopmentCardView(player.getDevelopmentCardPlayer(), null);
        this.depositState = new NewDepositView(player.getDeposit(), player.getStrongBox(), player.getAdditionalDepositType(), player.getAdditionalDeposit());
        this.activeLeaderCard = new LeaderCardView(player.getLeaderCardActive(), null);
        this.inactiveLeaderCard = new LeaderCardView(this.inactiveLeaders, null);
        this.faithTrackView = new FaithTrackView(player.getPopeFavorTiles(), player.getFaithMarkerPosition());
    }

    /**
     * Prints the visualization for the selection of other player's board or the part of the gameboard selected
     */
    private synchronized void printSelection(){
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
