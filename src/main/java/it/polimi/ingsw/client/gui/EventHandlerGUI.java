package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ConnectionToServer;
import it.polimi.ingsw.client.gui.controllergui.*;
import it.polimi.ingsw.events.serverToClient.*;
import it.polimi.ingsw.events.serverToClient.initialconnectiontoclient.SendNamePlayerRequestToClient;
import it.polimi.ingsw.events.serverToClient.initialconnectiontoclient.SendNumPlayerRequestToClient;
import it.polimi.ingsw.events.serverToClient.initialconnectiontoclient.SendRoomRequestToClient;
import it.polimi.ingsw.events.serverToClient.TurnReselectionToClient;
import it.polimi.ingsw.events.serverToClient.SendSpaceDevelopmentCardToClient;
import it.polimi.ingsw.events.serverToClient.SendReorganizeDepositToClient;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.concurrent.CountDownLatch;

/**
 * This class implements the EventToClientVisitor interface. This class parse the type of event that
 * arrive from the connection with the server to do specific actions.
 * @see EventToClientVisitor
 *
 * @author Stefano Fossati
 */
public class EventHandlerGUI implements EventToClientVisitor {
    private final ConnectionToServer connectionToServer;
    private final GUI gui;

    /**
     * Constructs the class with the connection with the server and the GUI application.
     * @param connectionToServer The connection with the server.
     * @param gui The GUI application.
     */
    public EventHandlerGUI(ConnectionToServer connectionToServer, GUI gui) {
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


    /**
     * This method notify to the user the disconnection from server with an alert.
     */
    public void closeConnectionAlert(){
        changeSceneThread("playerView");
        Platform.runLater(new Thread(()-> gui.showAlert(Alert.AlertType.INFORMATION, "You are disconnected from the server")));
    }

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------
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

    // ----------------------------------
    // EVENT FOR THE INITIAL RESOURCES
    // ----------------------------------
    @Override
    public void visit(SendInitialResourcesToClient numResources) {
        changeSceneThread("initialResourcesView");
        InitialResourcesController controller = (InitialResourcesController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.arriveInitialResources(numResources.getNumResources())));
    }

    // -------------------------------------------
    // EVENTS THAT RECEIVE LEADER CARD INFORMATION
    // -------------------------------------------
    @Override
    public void visit(SendArrayLeaderCardsToClient leaderCardArray) {
        if(leaderCardArray.isInitialLeaderCards()){
            changeSceneThread("initialLeaderView");
            InitialLeaderController controller = (InitialLeaderController) gui.getCurrentController();
            Platform.runLater(new Thread(()-> controller.drawLeader(leaderCardArray.getLeaderCardArray())));
        }else{
            changeSceneThread("leaderCardView");
            LeaderCardController controller = (LeaderCardController) gui.getCurrentController();
            Platform.runLater(new Thread(() -> controller.drawLeader(leaderCardArray.getLeaderCardArray(), leaderCardArray.isFinal())));
        }
    }

    // -------------------------------------------------------------------
    // EVENT FOR THE NEW TURN, THIS EVENT UPDATE THE CLIENT INFORMATION
    // -------------------------------------------------------------------

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

    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------
    @Override
    public void visit(SendReorganizeDepositToClient newResources) {
        changeSceneThread("newDepositView");
        NewDepositController controller = (NewDepositController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.drawDeposit(newResources.getDepositResources(), newResources.getMarketResources(),
                newResources.isAdditionalDeposit(), newResources.getType(), newResources.getAdditionalDepositState(), gui.getLastTurn().getPlayers().get(gui.getNamePlayer()).getLeaderCardActive(), false)) );
    }

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------
    @Override
    public void visit(TurnReselectionToClient message) {
        changeSceneThread("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.tabTurnNotActive(false)));
        Platform.runLater(new Thread(() -> gui.showAlert(Alert.AlertType.ERROR, message.getMessage())));
    }

    @Override
    public void visit(SendSpaceDevelopmentCardToClient developmentCardSpace) {
        changeSceneThread("selectDevSpaceView");
        SelectDevelopmentSpaceController controller = (SelectDevelopmentSpaceController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.drawDevPlayer(developmentCardSpace.getDevelopmentCardSpace())));
    }

    // ----------------------------------
    // EVENT FOR NOTIFY THE CLIENT
    // ----------------------------------
    @Override
    public void visit(NotifyToClient message) {
        Platform.runLater(new Thread(() -> gui.showAlert(Alert.AlertType.INFORMATION, message.getMessage())));
    }

    // ----------------------------------
    // FINAL EVENT
    // ----------------------------------
    @Override
    public void visit(EndGameToClient message) {
        changeSceneThread("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> gui.showAlert(Alert.AlertType.INFORMATION, message.getMessage() + " -> You are disconnected from the server")));
        if(message.getPlayersPoint()!=null){
            Platform.runLater(new Thread(()-> controller.saveFinalPoints(message.getPlayersPoint())));
        }

        Platform.runLater(new Thread(()->controller.updatePlayerBoard(message.getPlayerInformation())));
        Platform.runLater(new Thread(() -> controller.updateTable(message.getDevCard(), message.getMarket())));
        if(message.isLorenzo()){
            Platform.runLater(new Thread(() -> controller.lorenzoFaith(message.getLorenzoPosition())));
        }
        System.out.println("\u001B[92m" + message.getMessage() + "\u001B[0;0m");
        System.out.println("\u001B[92m" + "GAME ENDED" + "\u001B[0;0m");
        System.out.println("\u001B[92m" +   "You are disconnected from the server" + "\u001B[0;0m");

        connectionToServer.closeConnection();

    }

    // ----------------------------------
    // EVENT FOR THE SINGLE GAME
    // ----------------------------------
    @Override
    public void visit(LorenzoActionToClient lorenzoAction) {
        changeSceneThread("lorenzoView");
        LorenzoController controller = (LorenzoController) gui.getCurrentController();
        Platform.runLater(new Thread(() -> controller.drawSoloAction(lorenzoAction.getLorenzoAction(), lorenzoAction.getLorenzoPosition())));

    }

    // ----------------------------------
    // PING EVENT
    // ----------------------------------
    @Override
    public void visit(PingToClient ping) {
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
