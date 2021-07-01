package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.cli.views.CLIHandler;
import it.polimi.ingsw.client.cli.views.Messages;
import it.polimi.ingsw.client.ConnectionToServer;
import it.polimi.ingsw.events.servertoclient.*;
import it.polimi.ingsw.events.servertoclient.initialconnectiontoclient.SendNamePlayerRequestToClient;
import it.polimi.ingsw.events.servertoclient.initialconnectiontoclient.SendNumPlayerRequestToClient;
import it.polimi.ingsw.events.servertoclient.initialconnectiontoclient.SendRoomRequestToClient;
import it.polimi.ingsw.events.servertoclient.SendSpaceDevelopmentCardToClient;
import it.polimi.ingsw.events.servertoclient.SendReorganizeDepositToClient;
import it.polimi.ingsw.events.servertoclient.TurnReselectionToClient;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class EventHandlerCLI implements EventToClientVisitor {
    boolean printing;
    private final ConnectionToServer connectionToServer;
    private final CLIHandler handler;
    private final Semaphore available = new Semaphore(1, true);


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
            handler.leaderCardSelection(leaderCardArray.getLeaderCardArray(), leaderCardArray.isInitialLeaderCards(), leaderCardArray.getNumberOfDevelopmentCards(), leaderCardArray.isFinal());
            available.release();
        }).start();
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
    }

    // ----------------------------------
    // OTHER EVENTS
    // ----------------------------------
    @Override
    public void visit(NotifyToClient message) {
        new Thread(() -> {
            acquire();
            if (!message.getMessage().equals("WaitForOtherPlayers") && !message.getMessage().equals("AllPlayersConnected")
                    && !message.getMessage().equals("It's your turn")) {
                handler.stopAsyncPrint();
                Messages messages = new Messages(message.getMessage(), false);
                messages.printMessage();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    System.out.println("Error in sleep");
                }
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
        new Thread(() -> {
            acquire();
            handler.insertInitialData("numberOfPlayers");
            available.release();
        }).start();
    }
}
