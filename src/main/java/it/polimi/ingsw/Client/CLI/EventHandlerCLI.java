package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.CLI.Views.CLIHandler;
import it.polimi.ingsw.Client.CLI.Views.Messages;
import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Events.ServerToClient.*;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendNamePlayerRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendNumPlayerRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendRoomRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendSpaceDevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.SendReorganizeDepositToClient;
import it.polimi.ingsw.Events.ServerToClient.TurnReselectionToClient;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class EventHandlerCLI implements EventToClientVisitor {
    boolean printing;
    private final ConnectionToServer connectionToServer;
    private final CLIHandler handler;
    private final Semaphore available = new Semaphore(1, true);
    private final Thread asyncPrint = new Thread(()->{
        System.out.print("                                                                                                                          \n" +
                          "                                                                                                                          \n" +
                          "                                                                                                                          \n");
        System.out.print("                                    WAITING FOR OTHER PLAYERS");

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
                System.out.print("\u001B[2J\u001B[3J\u001B[H");
                break;
            }

            for (int i = 0; i < 5; i++)
                System.out.print("\b");

            System.out.print("     ");

            for (int i = 0; i < 5; i++)
                System.out.print("\b");

        }});


    public EventHandlerCLI(ConnectionToServer connectionToServer) {
        this.connectionToServer = connectionToServer;
        this.handler = new CLIHandler(this.connectionToServer);
        this.printing = false;
    }

    public void receiveEvent(EventToClient event){
        event.acceptVisitor(this);
    }

    public void acquire(){
        try {
            available.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Events that arrive from ConnectionToServer, so from the server
    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------




    // -------------------------------------------
    // EVENTS THAT RECEIVE LEADER CARD INFORMATION
    // -------------------------------------------
    @Override
    public void visit(SendArrayLeaderCardsToClient leaderCardArray) {
        new Thread(() -> {
            acquire();
            handler.leaderCardSelection(leaderCardArray.getLeaderCardArray(), leaderCardArray.isInitialLeaderCards(), leaderCardArray.getNumberOfDevelopmentCards());
            available.release();
        }).start();
        /*
        if(leaderCardArray.isInitialLeaderCards()) {
            LeaderCardView leaderCardView = new LeaderCardView(leaderCardArray.getLeaderCardArray());
            int[] received = leaderCardView.StartInitialLeaderCardView();
            connectionToServer.sendDiscardInitialLeaderCards(received[0], received[1]);
        }else{
            LeaderCardView leaderCardView = new LeaderCardView(leaderCardArray.getLeaderCardArray());
            ArrayList<Integer> received = leaderCardView.StartCardView();
            connectionToServer.sendLeaderCardTurn(received);
        }*/
    }
    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------

    @Override
    public void visit(SendReorganizeDepositToClient newResources) {
        new Thread(() -> {
            acquire();
            handler.selectNewDeposit(newResources.getDepositResources(), newResources.getMarketResources(), newResources.isAdditionalDeposit(), newResources.getType(), newResources.getAdditionalDepositState());
            available.release();
        }).start();
        /*NewDepositView view = new NewDepositView(newResources.getDepositResources(), newResources.getMarketResources(), false, null, null);
        view.LaunchView();
        connectionToServer.sendNewDepositState(view.getDepositState(), view.getMarketReceived());*/
    }

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------

    @Override
    public void visit(SendSpaceDevelopmentCardToClient developmentCardSpace) {
        new Thread(() -> {
            acquire();
            handler.selectSpaceForDevelopment(developmentCardSpace.getDevelopmentCardSpace());
            available.release();
        }).start();
        /*boolean bought = true;
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
        connectionToServer.sendSelectedDevelopmentCardSpace(selectedPosition);*/
    }

    // ----------------------------------
    // OTHER EVENTS
    // ----------------------------------
    @Override
    public void visit(NotifyToClient message) {
        new Thread(() -> {
            acquire();
            switch (message.getMessage()) {

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

            if (!message.getMessage().equals("WaitForOtherPlayers") && !message.getMessage().equals("AllPlayersConnected")) {
                Messages messages = new Messages(message.getMessage(), false);
                messages.printMessage();
            }
            available.release();
        }).start();
    }

    @Override
    public void visit(TurnReselectionToClient message) {
        new Thread(handler::newTurn).start();
    }

    @Override
    public void visit(NewTurnToClient newTurn) {
        new Thread(() -> {
            acquire();
            if (newTurn.isYourTurn()) {
                handler.newState(newTurn.getPlayers(), newTurn.getMarket(), newTurn.getDevelopmentCards());
                handler.newTurn();

        /* FOR DEBUG! PRINTS THE CORRECT STATE OF THE CURRENT PLAYER WITHOUT THE USE OF THE CLI (check for visualization)
            for (int i = 0; i < newTurn.getPlayers().size(); i++) {
                if (newTurn.getPlayers().get(i).getPlayerNameSend().equals(namePlayer)) {
                    index = i;
                }
            }

            PlayerInformationToClient player = newTurn.getPlayers().get(index);

            System.out.println("il mio deposito");
            System.out.println(player.getDeposit());

            System.out.println("la mia strongbox");
            System.out.println(player.getStrongBox().toString());

            System.out.println("le leadercard attive");
            System.out.println(player.getLeaderCardActive().toString());

            System.out.println("le mie development");
            for (DevelopmentCardToClient card : player.getDevelopmentCardPlayer()) {
                if (card != null) {
                   System.out.println("id: " + card.getCardID() + " color: " + card.getColor() + " level: " + card.getLevel() + " cost: " + card.getCost() + " Req: " + card.getMaterialRequired() + " Grant: " + card.getProductionResult());
                } else {
                    System.out.println("null");
                }
            }

            System.out.println("la mia posizione");
            System.out.println(player.getFaithMarkerPosition() + "/24");

            System.out.println("i miei pope");
            System.out.println(player.getPopeFavorTiles().toString());*/
            }
            available.release();
        }).start();
    }

    @Override
    public void visit(EndGameToClient message) {
        new Thread(() -> {
            acquire();
            Messages messageEnd = new Messages("GAME ENDED", false);
            messageEnd.printMessage();
            connectionToServer.closeConnection();
            available.release();
        }).start();
    }

    @Override
    public void visit(SendInitialResourcesToClient numResources) {
        new Thread(() -> {
            acquire();
            handler.initialResourceSelection(numResources.getNumResources(), numResources.getDepositState());
            available.release();
        }).start();
    }

    @Override
    public void visit(LorenzoActionToClient lorenzoAction) {
        new Thread(() -> {
            acquire();
            System.out.println("lorenzo ha pescato -> " + lorenzoAction.getLorenzoAction().toString());
            System.out.println("lorenzo si trova " + lorenzoAction.getLorenzoPosition() + "/24");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectionToServer.sendReplayLorenzoAction();
            available.release();
        }).start();
    }

    @Override
    public void visit(PingToClient ping) {

    }

    @Override
    public void visit(SendRoomRequestToClient roomRequest) {
        /*
        System.out.println(roomRequest.getMessage());
        System.out.println("mi è arrivata la riciesta della stanza: scrivila");
        Scanner ScanIn = new Scanner(System.in);
        int room = ScanIn.nextInt();
        System.out.println("è nuova??");
        Scanner ScanIn1 = new Scanner(System.in);
        int num = ScanIn1.nextInt();
        boolean tmp;
        if(num == 1){
            tmp=true;
        }else{
            tmp=false;
        }
        System.out.println(tmp + " è il voolean");*/
        new Thread(() -> {
            acquire();
            handler.insertInitialData("isNewRoom");
            handler.insertInitialData("roomNumber");
            connectionToServer.sendRoom(handler.getTmpRoomNumber(), handler.isNewRoomOrNot());
            available.release();
        }).start();
    }

    @Override
    public void visit(SendNamePlayerRequestToClient nameRequest) {
        /*System.out.println(nameRequest.getRequest());
        System.out.print("                                                                                                                          \n");

        System.out.print("                                    INSERT NAME: ");

        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scanner scanIn = new Scanner(System.in);


        String line = scanIn.nextLine();
        connectionToServer.sendPlayerName(line);
        namePlayer = line;
        connectionToServer.setPlayerName(line);*/
        new Thread(() ->{
            acquire();
            if(!nameRequest.getRequest().equals("write your nickname")) {
                Messages messages = new Messages(nameRequest.getRequest(), false);
                messages.printMessage();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            handler.insertInitialData("namePlayer");
            available.release();
        }).start();
    }

    @Override
    public void visit(SendNumPlayerRequestToClient numPlayer) {
        /*System.out.println(numPlayer.getMessage());

        System.out.print("                                                                                                                           \n");

        System.out.print("                                    YOU'RE THE FIRST PLAYER! ");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0; i<60; i++)
            System.out.print("\b");

        System.out.print("                                   INSERT NUMBER OF PLAYERS: ");

        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scanner scanIn = new Scanner(System.in);
        int num = scanIn.nextInt();
        connectionToServer.sendNumPlayer(num);*/

        new Thread(() -> {
            acquire();
            handler.insertInitialData("numberOfPlayers");
            available.release();
        }).start();
    }
}
