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

/**
 * This class handles the send of information from the client to the server
 * Implements Runnable and EventToServerNotifier interface
 */
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

    /**
     * The socket created is passed to this class
     * @param socket is the socket created between client and server
     */
    public ConnectionToServer(Socket socket) {
        this.socket = socket;
        this.active = true;
        this.eventHandlerCli = new EventHandlerCLI(this);
    }

    /**
     * Set the ip address to the connection
     * @param address is the ip address of the server
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * Gets the ip address to the connection
     * @return the address of the server
     */
    public String getAddress(){
        return this.address;
    }

    /**
     * Set the event handler for the gui
     * @param eventHandlerGUI is the event handler of the GUI that interface the client with the server
     */
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
            }
        }
    }

    /**
     * Notifies a disconnection to the client
     */
    private void notifyDisconnection(){
        if(activeGui){
            eventHandlerGUI.closeConnectionAlert();
        }else{
            eventHandlerCli.notifyDisconnection();
        }
    }

    /**
     * Receives the event form the server of this connection.
     * @return The event receive form the server.
     * @throws IOException if the ObjectInputStream throws an error.
     * @throws ClassNotFoundException if the object received from the server is not a class known.
     */
    private EventToClient receiveEvent() throws IOException, ClassNotFoundException {
        return (EventToClient) input.readObject();
    }

    /**
     * Writes events to ObjectOutputStream and sends to server of this connection through the socket.
     * In case of an IOException calls the disconnection handler to manage the connection.
     * @param event The event to send to the server.
     */
    private void sendEvent(EventToServer event){
        try{
            output.writeObject(event);
            output.flush();
            output.reset();
        } catch (IOException e) {
            closeConnection();
            notifyDisconnection();
        }
    }

    /**
     * Sends the event with a thread to the server of this connection.
     * @param event The event to send to the server.
     */
    public synchronized void asyncSendEvent(EventToServer event){
        new Thread(() ->
                sendEvent(event)
        ).start();
    }

    /**
     * Close the connection and set the server like inactive
     */
    public void closeConnection(){
        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        active = false;
    }

    /**
     * Sets the name of the client/player of this connection.
     * @param playerName The name of the client/player of this connection.
     */
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
