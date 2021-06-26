package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Client.GUI.ControllerGUI.*;
import it.polimi.ingsw.Events.ServerToClient.*;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendNamePlayerRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendNumPlayerRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.InitialConnectionToClient.SendRoomRequestToClient;
import it.polimi.ingsw.Events.ServerToClient.TurnReselectionToClient;
import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendSpaceDevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.SendReorganizeDepositToClient;
import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;

/**
 * This class implements the EventToClientVisitor interface. This class parse the type of event that arrive from the connection with the server to do specific actions.
 * @see EventToClientVisitor
 *
 * @author Stefano Fossati
 */
public class VisitClass implements EventToClientVisitor {
    private final ConnectionToServer connectionToServer;
    private final GUI gui;


    /**
     * Constructs the class with the connection with the server and the GUI application.
     * @param connectionToServer The connection with the server.
     * @param gui The GUI application.
     */
    public VisitClass(ConnectionToServer connectionToServer, GUI gui) {
        this.connectionToServer = connectionToServer;
        new Thread(connectionToServer).start();
        this.gui = gui;
    }

    /**
     * This method permits to the connection with the server to pass the receive event to this class.
     * @param event The event passed by the connection with the server.
     */
    public void receiveEvent(EventToClient event){
        event.acceptVisitor(this);
    }




    @Override
    public void visit(SendArrayLeaderCardsToClient leaderCardArray) {
        if(leaderCardArray.isInitialLeaderCards()){
            changeSceneThread("initialLeaderView");
            InitialLeaderController controller = (InitialLeaderController) gui.getCurrentController();
            Platform.runLater(new Thread(()-> controller.drawLeader(leaderCardArray.getLeaderCardArray())));
        }else{
            changeSceneThread("leaderCardView");
            LeaderCardController controller = (LeaderCardController) gui.getCurrentController();
            Platform.runLater(new Thread(() -> controller.drawLeader(leaderCardArray.getLeaderCardArray())));
        }
    }

    @Override
    public void visit(SendReorganizeDepositToClient newResources) {
        changeSceneThread("newDepositView");
        NewDepositController controller = (NewDepositController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.drawDeposit(newResources.getDepositResources(), newResources.getMarketResources(),
                newResources.isAdditionalDeposit(), newResources.getType(), newResources.getAdditionalDepositState(), gui.getLastTurn().getPlayers().get(gui.getNamePlayer()).getLeaderCardActive(), false)) );
    }

    @Override
    public void visit(TurnReselectionToClient message) {
        changeSceneThread("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.tabTurnNotActive(false)));
    }

    @Override
    public void visit(SendSpaceDevelopmentCardToClient developmentCardSpace) {
        changeSceneThread("selectDevSpaceView");
        SelectDevelopmentSpaceController controller = (SelectDevelopmentSpaceController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.drawDevPlayer(developmentCardSpace.getDevelopmentCardSpace())));
    }

    @Override
    public void visit(NotifyToClient message) {
        System.out.println(message.getMessage());
        //TODO non implementato
    }

    @Override
    public void visit(NewTurnToClient newTurn) {
        changeSceneThread("playerView");
        gui.setLastTurn(newTurn);
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        if(newTurn.isYourTurn()){
            Platform.runLater(new Thread(() -> controller.tabTurnNotActive(false)));
        }else{
            Platform.runLater(new Thread(() -> controller.tabTurnNotActive(true)));
        }
        Platform.runLater(new Thread(() -> controller.updateTable(newTurn.getDevelopmentCards(), newTurn.getMarket())));
        Platform.runLater(new Thread(()-> controller.updatePlayerBoard(newTurn.getPlayers())));
    }

    @Override
    public void visit(EndGameToClient message) {
        connectionToServer.setActive(false);
        changeSceneThread("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        controller.updatePlayerBoard(message.getPlayerInformation());
        controller.updateTable(message.getDevCard(), message.getMarket());
        if(message.isLorenzo()){
            controller.lorenzoFaith(message.getLorenzoPosition());
        }
        //TODO allert
        connectionToServer.closeConnection();
    }

    @Override
    public void visit(SendInitialResourcesToClient numResources) {
        changeSceneThread("initialResourcesView");
        InitialResourcesController controller = (InitialResourcesController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.arriveInitialResources(numResources.getNumResources())));
    }

    @Override
    public void visit(LorenzoActionToClient lorenzoAction) {
        changeSceneThread("lorenzoView");
        LorenzoController controller = (LorenzoController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.drawSoloAction(lorenzoAction.getLorenzoAction(), lorenzoAction.getLorenzoPosition())));

    }

    @Override
    public void visit(PingToClient ping) {

    }

    @Override
    public void visit(SendRoomRequestToClient roomRequest) {
        changeSceneThread("playerName");
        PlayerNameController controller = (PlayerNameController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.arriveRoomPlayer(roomRequest.getMessage())));
    }

    @Override
    public void visit(SendNamePlayerRequestToClient nameRequest) {
        PlayerNameController controller = (PlayerNameController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.arriveNamePlayer(nameRequest.getRequest())));
    }

    @Override
    public void visit(SendNumPlayerRequestToClient numPlayer) {
        PlayerNameController controller = (PlayerNameController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.arriveNumPlayer(numPlayer.getMessage())));
    }

    /**
     * This method creates a thread to update scene od the GUI application. Alsa This method uses a semaphore to wait that the GUI finishes to change scene.
     * @param scene The scene into GUI will change.
     */
    private void changeSceneThread(String scene){
        CountDownLatch threadCount = new CountDownLatch(1);
        Platform.runLater(new Thread(() -> {
            gui.changeScene(scene);
            threadCount.countDown();
        }));
        try {
            threadCount.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
