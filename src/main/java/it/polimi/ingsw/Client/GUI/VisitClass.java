package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Client.GUI.ControllerGUI.PlayerNameController;
import it.polimi.ingsw.Client.GUI.ControllerGUI.NewDepositViewController;
import it.polimi.ingsw.Client.GUI.ControllerGUI.PlayerViewController;
import it.polimi.ingsw.Client.GUI.ControllerGUI.SelectDevelopmentSpaceView;
import it.polimi.ingsw.Events.ServerToClient.*;
import it.polimi.ingsw.Events.ServerToClient.TurnReselection;
import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendSpaceDevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.SendReorganizeDepositToClient;
import it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient.SendNumPlayerToClient;
import it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient.SendPlayerNameToClient;
import it.polimi.ingsw.Model.Resource.Resource;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class VisitClass implements EventToClientVisitor {
    private final ConnectionToServer connectionToServer;
    private final GUI gui;


    public VisitClass(ConnectionToServer connectionToServer, GUI gui) {
        this.connectionToServer = connectionToServer;
        new Thread(connectionToServer).start();
        this.gui = gui;
    }

    public void receiveEvent(EventToClient event){
        event.acceptVisitor(this);
    }


    @Override
    public void visit(SendPlayerNameToClient playerName) {

        connectionToServer.setPlayerName(playerName.getPlayerName());
        CountDownLatch threadCount = new CountDownLatch(1);
        Platform.runLater(new Thread(() -> {
            gui.changeScene("playerName");
            threadCount.countDown();
        }));
        try {
            threadCount.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PlayerNameController controller = (PlayerNameController) gui.getCurrentController();
    }

    @Override
    public void visit(SendNumPlayerToClient numPlayer) {
        System.out.println("arrivato num player");
        PlayerNameController controller = (PlayerNameController) gui.getCurrentController();
        controller.arriveNumPlayer();
    }

    @Override
    public void visit(SendArrayLeaderCardsToClient leaderCardArray) {
        if(leaderCardArray.isInitialLeaderCards()){
            connectionToServer.sendDiscardInitialLeaderCards(1,2);
        }else{
            ArrayList<Integer> tmp = new ArrayList<>();
            tmp.add(2);
            tmp.add(2);
            connectionToServer.sendLeaderCardTurn(tmp);
        }
        //TODO non implementato
    }

    @Override
    public void visit(SendReorganizeDepositToClient newResources) {
        CountDownLatch threadCount = new CountDownLatch(1);
        Platform.runLater(new Thread(() -> {
            gui.changeScene("newDepositView");
            threadCount.countDown();
        }));
        try {
            threadCount.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NewDepositViewController controller = (NewDepositViewController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.drawDeposit(newResources.getDepositResources(), newResources.getMarketResources())));
    }

    @Override
    public void visit(TurnReselection message) {

    }

    @Override
    public void visit(SendSpaceDevelopmentCardToClient developmentCardSpace) {
        System.out.println("dove metto la carta??");
        CountDownLatch threadCount = new CountDownLatch(1);
        Platform.runLater(new Thread(()-> {
            gui.changeScene("selectDevSpaceView");
            threadCount.countDown();
        }));
        try {
            threadCount.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SelectDevelopmentSpaceView controller = (SelectDevelopmentSpaceView) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.drawDevPlayer(developmentCardSpace.getDevelopmentCardSpace())));
    }

    @Override
    public void visit(NotifyToClient message) {
        System.out.println(message.getMessage());
        //TODO non implementato
    }

    @Override
    public void visit(NewTurnToClient newTurn) {
        CountDownLatch threadCount = new CountDownLatch(1);
        System.out.println("nuovo turno");
        Platform.runLater(new Thread(()-> {
            gui.changeScene("playerView");
            threadCount.countDown();
        }));
        try {
            threadCount.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gui.setLastTurn(newTurn);
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.tabTurnActive(false)));
        Platform.runLater(new Thread(() -> controller.updateTable(newTurn.getDevelopmentCards(), newTurn.getMarket())));
        Platform.runLater(new Thread(()-> controller.updatePlayerBoard(newTurn.getPlayers())));


    }

    @Override
    public void visit(EndGameToClient message) {

    }

    @Override
    public void visit(SendInitialResourcesToClient numResources) {
        ArrayList<Resource> res = new ArrayList<>();
        for(int i=0; i<6; i++){
            res.add(null);
        }
        connectionToServer.sendInitialDepositState(res);
    }

    @Override
    public void visit(LorenzoActionToClient lorenzoAction) {

    }
    //TODO non implementato

}
