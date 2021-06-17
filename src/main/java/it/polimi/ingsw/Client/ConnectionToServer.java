package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.CLI.CLI;
import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ClientToServer.*;
import it.polimi.ingsw.Events.ClientToServer.BuyDevelopmentCardToServer.SelectedDevelopmentCardSpaceToServer;
import it.polimi.ingsw.Events.ClientToServer.BuyDevelopmentCardToServer.SelectedDevelopmentCardToBuyToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendNumPlayerToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendPlayerNameToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendRoomToServer;
import it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer.ChooseLineToServer;
import it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer.NewDepositStateToServer;
import it.polimi.ingsw.Events.ClientToServer.StartGameToServer.DiscardInitialLeaderCards;
import it.polimi.ingsw.Events.ClientToServer.StartGameToServer.InitialResourcesChoose;
import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Resource.Resource;

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
    private CLI cli;
    private GUI gui;

    // Input and Output steams
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ConnectionToServer(Socket socket) {
        this.socket = socket;
        this.active = true;
        cli = new CLI(this);
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
        activeGui = true;
    }

    @Override
    public void run() {
        try{
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(isActive()){
            try{
                EventToClient event = receiveEvent();
                if (activeGui){
                    gui.getVisit().receiveEvent(event);
                }else{
                    cli.receiveEvent(event);
                }
            } catch (IOException e) {
                closeConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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
        setActive(false);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // event that the cli/gui calls to send event to the server

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE SERVER
    // -------------------------------------------------------


    // -------------------------------------------
    // EVENTS FOR THE LEADER CARD INTERACTION
    // -------------------------------------------
    @Override
    public void sendDiscardInitialLeaderCards(int leaderCard1, int leaderCard2) {
        DiscardInitialLeaderCards discardInitialLeaderCards = new DiscardInitialLeaderCards(leaderCard1, leaderCard2, this.playerName);
        asyncSendEvent(discardInitialLeaderCards);
    }

    @Override
    public void sendLeaderCardTurn(ArrayList<Integer> positions) {
        SendLeaderCardTurnToServer sendLeaderCardTurnToServer = new SendLeaderCardTurnToServer(positions, this.playerName);
        asyncSendEvent(sendLeaderCardTurnToServer);
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
        SelectedProductionDevelopmentCardToServer selectedProductionDevelopmentCardToServer = new SelectedProductionDevelopmentCardToServer(useBaseProduction, resourceRequested1, resourceRequested2, resourceGranted, useLeaders, materialLeaders, useDevelop, this.playerName);
        asyncSendEvent(selectedProductionDevelopmentCardToServer);
    }

    // ------------------------------------------------------
    // EVENT FOR NEW TURN INTERACTION
    // ------------------------------------------------------
    @Override
    public void sendTurnPlayed(String turnType) {
        TurnPlayedToServer turnPlayedToServer = new TurnPlayedToServer(turnType, this.playerName);
        asyncSendEvent(turnPlayedToServer);
    }

    @Override
    public void sendInitialDepositState(ArrayList<Resource> newInitialDepositState) {
        InitialResourcesChoose initialResourcesChoose = new InitialResourcesChoose(newInitialDepositState, this.playerName);
        asyncSendEvent(initialResourcesChoose);
    }

    @Override
    public void sendReplayLorenzoAction() {
        ReplayLorenzoActionToServer replayLorenzoActionToServer = new ReplayLorenzoActionToServer(this.playerName);
        asyncSendEvent(replayLorenzoActionToServer);
    }

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


}
