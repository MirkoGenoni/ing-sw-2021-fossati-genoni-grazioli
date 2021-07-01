package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.EventHandlerCLI;
import it.polimi.ingsw.client.gui.EventHandlerGUI;
import it.polimi.ingsw.events.clienttoserver.*;
import it.polimi.ingsw.events.clienttoserver.buydevelopmentcardtoserver.SelectedDevelopmentCardSpaceToServer;
import it.polimi.ingsw.events.clienttoserver.buydevelopmentcardtoserver.SelectedDevelopmentCardToBuyToServer;
import it.polimi.ingsw.events.clienttoserver.initialconnectiontoserver.SendNumPlayerToServer;
import it.polimi.ingsw.events.clienttoserver.initialconnectiontoserver.SendPlayerNameToServer;
import it.polimi.ingsw.events.clienttoserver.initialconnectiontoserver.SendRoomToServer;
import it.polimi.ingsw.events.clienttoserver.marketturntoserver.ChooseLineToServer;
import it.polimi.ingsw.events.clienttoserver.marketturntoserver.NewDepositStateToServer;
import it.polimi.ingsw.events.clienttoserver.startgametoserver.DiscardInitialLeaderCards;
import it.polimi.ingsw.events.clienttoserver.startgametoserver.InitialResourcesChoose;
import it.polimi.ingsw.events.servertoclient.EventToClient;
import it.polimi.ingsw.model.developmentcard.ProductedMaterials;
import it.polimi.ingsw.model.resource.Resource;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionToServer implements Runnable, EventToServerNotifier {
    private final Socket socket;
    private String address;
    private boolean active;
    private String playerName;
    private boolean activeGui = false;
    private final EventHandlerCLI eventHandlerCli;
    private EventHandlerGUI eventHandlerGUI;

    // Input and Output steams
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ConnectionToServer(Socket socket) {
        this.socket = socket;
        this.active = true;
        this.eventHandlerCli = new EventHandlerCLI(this);
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

    public void setEventHandlerGUI(EventHandlerGUI eventHandlerGUI) {
        this.eventHandlerGUI = eventHandlerGUI;
        activeGui = true;
    }

    @Override
    public void run() {
        try{
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
            notifyDisconnection();
        }

        while(active){
            try{
                EventToClient event = receiveEvent();
                if(!event.getClass().getSimpleName().equals("PingToClient")){
                    if (activeGui){
                        eventHandlerGUI.receiveEvent(event);
                    }else{
                        eventHandlerCli.receiveEvent(event);
                    }
                }else{
                    sendPing();
                }

            } catch (IOException e) {
                closeConnection();
                notifyDisconnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                closeConnection();
                notifyDisconnection();
            }
        }
    }

    private void notifyDisconnection(){
        if(activeGui){
            eventHandlerGUI.closeConnectionAlert();
        }
    }

    private EventToClient receiveEvent() throws IOException, ClassNotFoundException {
        return (EventToClient) input.readObject();
    }

    // send event to the server
    private void sendEvent(EventToServer event){
        try{
            output.writeObject(event);
            output.flush();
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
            notifyDisconnection();
            closeConnection();
        }
    }

    // send event to the server with a thread
    public synchronized void asyncSendEvent(EventToServer event){
        new Thread(() ->
                sendEvent(event)
        ).start();
    }

    // close the connection
    public void closeConnection(){
        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        active = false;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // event that the cli/eventHandlerGUI calls to send event to the server

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE SERVER
    // -------------------------------------------------------

    @Override
    public void sendRoom(int room, boolean newRoom) {
        SendRoomToServer sendRoomToServer = new SendRoomToServer(room, newRoom);
        asyncSendEvent(sendRoomToServer);
    }

    @Override
    public void sendPlayerName(String playerName) {
        SendPlayerNameToServer sendPlayerNameToServer = new SendPlayerNameToServer(playerName);
        asyncSendEvent(sendPlayerNameToServer);
    }

    @Override
    public void sendNumPlayer(int num) {
        SendNumPlayerToServer sendNumPlayerToServer = new SendNumPlayerToServer(num, this.playerName);
        asyncSendEvent(sendNumPlayerToServer);
    }


    // -------------------------------------------
    // EVENTS FOR THE INITIAL RESOURCES SELECTION
    // -------------------------------------------
    @Override
    public void sendInitialDepositState(ArrayList<Resource> newInitialDepositState) {
        InitialResourcesChoose initialResourcesChoose = new InitialResourcesChoose(newInitialDepositState, this.playerName);
        asyncSendEvent(initialResourcesChoose);
    }

    // -------------------------------------------
    // EVENTS FOR THE LEADER CARD INTERACTION
    // -------------------------------------------
    @Override
    public void sendDiscardInitialLeaderCards(int leaderCard1, int leaderCard2) {
        DiscardInitialLeaderCards discardInitialLeaderCards = new DiscardInitialLeaderCards(leaderCard1, leaderCard2, this.playerName);
        asyncSendEvent(discardInitialLeaderCards);
    }

    @Override
    public void sendLeaderCardActions(ArrayList<Integer> actions, boolean isFinal) {
        SendLeaderCardToServer sendLeaderCardToServer = new SendLeaderCardToServer(actions, this.playerName, isFinal);
        asyncSendEvent(sendLeaderCardToServer);
    }

    // -------------------------------------------
    // EVENTS FOR THE MARKET TURN INTERACTION
    // -------------------------------------------
    @Override
    public void sendChooseLine(int numLine, ArrayList<Boolean> leaderMarketWhiteChange) {
        ChooseLineToServer chooseLineToServer = new ChooseLineToServer(numLine, leaderMarketWhiteChange, this.playerName);
        asyncSendEvent(chooseLineToServer);
    }

    @Override
    public void sendNewDepositState(ArrayList<Resource> newDepositState, int discardResources, boolean isAdditional, ArrayList<Resource> additionalDepositState) {
        NewDepositStateToServer newDepositStateToServer = new NewDepositStateToServer(newDepositState, discardResources, this.playerName, isAdditional, additionalDepositState);
        asyncSendEvent(newDepositStateToServer);
    }

    // ------------------------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN INTERACTION
    // ------------------------------------------------------
    @Override
    public void sendSelectedDevelopmentCard(int color, int level) {
        SelectedDevelopmentCardToBuyToServer selectedDevelopmentCardToBuyToServer = new SelectedDevelopmentCardToBuyToServer(color, level, this.playerName);
        asyncSendEvent(selectedDevelopmentCardToBuyToServer);
    }

    @Override
    public void sendSelectedDevelopmentCardSpace(int space) {
        SelectedDevelopmentCardSpaceToServer selectedDevelopmentCardSpaceToServer = new SelectedDevelopmentCardSpaceToServer(space, this.playerName);
        asyncSendEvent(selectedDevelopmentCardSpaceToServer);
    }

    // ------------------------------------------------------
    // EVENTS FOR THE USE DEVELOPMENT CARD
    // ------------------------------------------------------
    @Override
    public void sendSelectedProductionDevelopmentCard(boolean useBaseProduction, Resource resourceRequested1, Resource resourceRequested2, ProductedMaterials resourceGranted, ArrayList<Boolean> useLeaders, ArrayList<Resource> materialLeaders, ArrayList<Boolean> useDevelop) {
        SelectedProductionToServer selectedProductionToServer = new SelectedProductionToServer(useBaseProduction, resourceRequested1, resourceRequested2, resourceGranted, useLeaders, materialLeaders, useDevelop, this.playerName);
        asyncSendEvent(selectedProductionToServer);
    }

    // ------------------------------------------------------
    // EVENT FOR NEW TURN INTERACTION
    // ------------------------------------------------------
    @Override
    public void sendTurnPlayed(String turnType) {
        TurnPlayedToServer turnPlayedToServer = new TurnPlayedToServer(turnType, this.playerName);
        asyncSendEvent(turnPlayedToServer);
    }



    // ----------------------------------
    // EVENT FOR THE SINGLE GAME
    // ----------------------------------
    @Override
    public void sendReplayLorenzoAction() {
        ReplayLorenzoActionToServer replayLorenzoActionToServer = new ReplayLorenzoActionToServer(this.playerName);
        asyncSendEvent(replayLorenzoActionToServer);
    }

    // ----------------------------------
    // PING EVENT
    // ----------------------------------
    @Override
    public void sendPing() {
        PingToServer pingToServer = new PingToServer();
        asyncSendEvent(pingToServer);
    }


}
