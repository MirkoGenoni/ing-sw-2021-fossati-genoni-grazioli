package it.polimi.ingsw.server;

import it.polimi.ingsw.events.clientToServer.EventToServer;
import it.polimi.ingsw.events.serverToClient.*;
import it.polimi.ingsw.controller.ObserveConnectionToClient;
import it.polimi.ingsw.events.serverToClient.initialConnectionToClient.SendNamePlayerRequestToClient;
import it.polimi.ingsw.events.serverToClient.initialConnectionToClient.SendNumPlayerRequestToClient;
import it.polimi.ingsw.events.serverToClient.initialConnectionToClient.SendRoomRequestToClient;
import it.polimi.ingsw.events.serverToClient.TurnReselectionToClient;
import it.polimi.ingsw.events.serverToClient.supportClass.DevelopmentCardToClient;
import it.polimi.ingsw.events.serverToClient.buyDevelopmentCardTurnToClient.SendSpaceDevelopmentCardToClient;
import it.polimi.ingsw.events.serverToClient.supportClass.LeaderCardToClient;
import it.polimi.ingsw.events.serverToClient.supportClass.MarketToClient;
import it.polimi.ingsw.events.serverToClient.marketTurnToClient.SendReorganizeDepositToClient;
import it.polimi.ingsw.events.serverToClient.supportClass.PlayerInformationToClient;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.LeaderCardException;
import it.polimi.ingsw.model.faithTrack.FaithTrack;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.gameboard.Gameboard;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.lorenzo.SoloAction;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resource.Resource;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This class is the remote view of the MVC patter.
 * It is used to manage the connection with the client.
 * It communicate with che controller to send information to the client and to send information from the client to the controller
 * Implements EventToClientNotifier interface and Runnable interface.
 * @see EventToClientNotifier
 * @see Runnable
 *
 * @author Stefano Fossati
 */
public class ConnectionToClient implements Runnable, EventToClientNotifier {
    private Socket clientSocket;
    private Thread ping;
    private boolean active;
    private String namePlayer;

    private DisconnectionHandler disconnectionHandler;

    //receiver of the event from the client
    private ObserveConnectionToClient observeConnectionToClient;

    // Input and Output steams
    private ObjectInputStream input;
    private ObjectOutputStream output;

    /**
     * Constructs the class and the InputStream and OutputStream of the connection.
     * @param clientSocket The socket of the connection with the client that this remote view represent.
     */
    public ConnectionToClient(Socket clientSocket){
        this.clientSocket = clientSocket;
        active = true;

        try{
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();

        }
        try {
            clientSocket.setSoTimeout(21000);
        } catch (SocketException e) {
            e.printStackTrace();
            closeConnection();
            disconnectionHandler.setNullConnection(namePlayer);
        }

        ping = new Thread(() -> {
            while(active){
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendPing();
            }
        });
        ping.start();
    }

    //---------------------
    //  SETTERS
    //---------------------
    /**
     * Sets the DisconnectionHandler of this connection.
     * @param disconnectionHandler The DisconnectionHandler to set.
     */
    public void setDisconnectionHandler(DisconnectionHandler disconnectionHandler) {
        this.disconnectionHandler = disconnectionHandler;
    }

    /**
     * Sets the class that observe the events that arrive form the client of this connection.
     * @param observeConnectionToClient The class that observe the events that arrive from the client of this connection.
     */
    public synchronized void setObserveConnectionToClient(ObserveConnectionToClient observeConnectionToClient){
        this.observeConnectionToClient = observeConnectionToClient;
    }

    /**
     * Sets the name of the client/player of this connection.
     * @param namePlayer The name of the client/player of this connection.
     */
    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    //---------------------
    //  GETTER
    //---------------------
    /**
     * Getter of the name of the client/player of this connection.
     * @return The name of the client/player of this connection.
     */
    public String getNamePlayer() {
        return namePlayer;
    }


    // -------------------------------------------------------
    // PRIVATE METHODS FOR MANAGING CONNECTION
    // -------------------------------------------------------
    /**
     * Writes events to ObjectOutputStream and sends to client of this connection through the socket.
     * In case of an IOException calls the disconnection handler to manage the connection.
     * @param event The event to send to the client.
     */
    private synchronized void sendEvent(EventToClient event){
        if(active){
            try{
                output.writeObject(event);   // write the event
                output.flush();              // send the event
                output.reset();              // clean buffer
            } catch (IOException e) { //TODO specifico
                closeConnection();
                if(namePlayer!=null){
                    disconnectionHandler.setNullConnection(namePlayer); //TODO specifico
                }
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends the event with a thread to the client of this connection.
     * @param event The event to send to the client.
     */
    private synchronized void asyncSendEvent(EventToClient event){
        new Thread(() -> sendEvent(event)).start();
    }

    /**
     * Receives the event form the client of this connection.
     * @return The event receive form the client.
     * @throws IOException if the ObjectInputStream throws an error.
     * @throws ClassNotFoundException if the object received from the client is not a class known.
     */
    private EventToServer receiveEvent() throws IOException, ClassNotFoundException {
        return (EventToServer) input.readObject();
    }

    /**
     * Close the connection and set the client like inactive
     */
    private void closeConnection(){
        active = false;
        try{
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        try{
            while(active){
                EventToServer event = receiveEvent();
                if(!event.getClass().getSimpleName().equals("PingToServer")){
                    observeConnectionToClient.observeEvent(event); // ControllerConnection or RoomHandler
                }
                //Thread.sleep(10);
            }
        } catch (IOException e) {
            closeConnection();
            if(namePlayer!=null){
                disconnectionHandler.setNullConnection(namePlayer); //TODO specifico
            }
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------
    @Override
    public void sendRoomRequestToClient(String message) {
        SendRoomRequestToClient sendRoomRequestToClient = new SendRoomRequestToClient(message);
        asyncSendEvent(sendRoomRequestToClient);
    }

    @Override
    public void sendNamePlayerRequest(String message) {
        SendNamePlayerRequestToClient sendNamePlayerRequestToClient = new SendNamePlayerRequestToClient(message);
        asyncSendEvent(sendNamePlayerRequestToClient);
    }

    @Override
    public void sendNumPlayerRequest(String message) {
        SendNumPlayerRequestToClient sendNumPlayerRequestToClient = new SendNumPlayerRequestToClient(message);
        asyncSendEvent(sendNumPlayerRequestToClient);
    }

    // ----------------------------------
    // EVENT FOR THE INITIAL RESOURCES
    // ----------------------------------
    @Override
    public void sendInitialResources(int numResources, ArrayList<Resource> depositState) {
        SendInitialResourcesToClient sendInitialResourcesToClient = new SendInitialResourcesToClient(numResources, depositState);
        asyncSendEvent(sendInitialResourcesToClient);
    }

    // ----------------------------------------
    // EVENTS THAT SEND LEADER CARD INFORMATION
    // ----------------------------------------
    @Override
    public void sendArrayLeaderCards(ArrayList<LeaderCard> leaderCards, boolean initialLeaderCards, Player currentPlayer, boolean isFinal) {
        Map<String, Integer> countOfDevelopmentCards = countNumberOfDevelopmentCards(currentPlayer);
        SendArrayLeaderCardsToClient sendArrayLeaderCardsToClient = new SendArrayLeaderCardsToClient(leaderCardToSend(leaderCards), countOfDevelopmentCards, initialLeaderCards, isFinal);
        asyncSendEvent(sendArrayLeaderCardsToClient);
    }

    // -------------------------------------------------------------------
    // EVENT FOR THE NEW TURN, THIS EVENT UPDATE THE CLIENT INFORMATION
    // -------------------------------------------------------------------
    @Override
    public void sendNewTurn(int turnNumber, Market market, DevelopmentCard[][] developmentCards, Player[] players, FaithTrack faithTrack, boolean yourTurn) {
        NewTurnToClient newTurnToClient = new NewTurnToClient(turnNumber, marketToSend(market),
                developmentCardAvailableToSend(developmentCards), playerInformationToSend(players, faithTrack), yourTurn);
        asyncSendEvent(newTurnToClient);
    }

    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------

    @Override
    public void sendReorganizeDeposit(ArrayList<Resource> marketResources, ArrayList<Resource> depositState,
                                      boolean isAdditional, ArrayList<Resource> typeAdditional, ArrayList<Resource> additionalState) {
        SendReorganizeDepositToClient sendReorganizeDepositToClient = new SendReorganizeDepositToClient(marketResources, depositState, isAdditional, typeAdditional, additionalState);
        asyncSendEvent(sendReorganizeDepositToClient);
    }

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------

    @Override
    public void sendTurnReselection(String message) {
        TurnReselectionToClient turnReselectionToClient = new TurnReselectionToClient(message);
        asyncSendEvent(turnReselectionToClient);
    }

    @Override
    public void sendDevelopmentCardSpace(ArrayList<Boolean> developmentCardSpace) {
        SendSpaceDevelopmentCardToClient sendSpaceDevelopmentCardToClient = new SendSpaceDevelopmentCardToClient(developmentCardSpace);
        asyncSendEvent(sendSpaceDevelopmentCardToClient);
    }

    // ----------------------------------
    // EVENT FOR NOTIFY THE CLIENT
    // ----------------------------------
    @Override
    public void sendNotify(String message) {
        NotifyToClient notifyToClient = new NotifyToClient(message);
        asyncSendEvent(notifyToClient);
    }

    // ----------------------------------
    // FINAL EVENT
    // ----------------------------------
    @Override
    public void sendEndGame(String message, Map<String, Integer> playersPoint, Player[] players, FaithTrack faithTrack,
                            boolean lorenzo, int lorenzoPosition, DevelopmentCard[][] devCard, Market market) {
        EndGameToClient endGameToClient = new EndGameToClient(message, playersPoint, playerInformationToSend(players, faithTrack),
                                            lorenzo, lorenzoPosition, developmentCardAvailableToSend(devCard), marketToSend(market));
        asyncSendEvent(endGameToClient);
        closeConnection();
        disconnectionHandler.removeRoom();
    }

    // ----------------------------------
    // EVENT FOR THE SINGLE GAME
    // ----------------------------------
    @Override
    public void sendLorenzoTurn(SoloAction lorenzoAction, int lorenzoPosition) {
        LorenzoActionToClient lorenzoActionToClient = new LorenzoActionToClient(lorenzoAction, lorenzoPosition);
        asyncSendEvent(lorenzoActionToClient);
    }

    // ----------------------------------
    // PING EVENT
    // ----------------------------------
    @Override
    public void sendPing() {
        PingToClient pingToClient = new PingToClient();
        asyncSendEvent(pingToClient);
    }



    // -------------------------------------------
    // PRIVATE METHODS FOR INFORMATION CONVERSION
    // -------------------------------------------
    /**
     * Generates LeaderCardToClient to send to the client of this connection from the LeaderCard ArrayList of the player.
     * @param leaderCards The LeaderCard of the player.
     * @return The LeaderCardToClient to send to the client.
     */
    private ArrayList<LeaderCardToClient> leaderCardToSend(ArrayList<LeaderCard> leaderCards){
        ArrayList<LeaderCardToClient> tmp = new ArrayList<>();
        if(leaderCards!=null) {
            for (int i = 0; i < leaderCards.size(); i++) {
                LeaderCardToClient leaderCardToClient = new LeaderCardToClient(leaderCards.get(i).getName(),
                        leaderCards.get(i).getSpecialAbility().getRequirements(),
                        leaderCards.get(i).getSpecialAbility().getVictoryPoints(),
                        leaderCards.get(i).getSpecialAbility().getEffect(),
                        leaderCards.get(i).getSpecialAbility().getMaterialType().toString());
                tmp.add(leaderCardToClient);
            }
        }
        return tmp;
    }

    /**
     * Generates a structure with all the development card of a player to send to the client.
     * @param developmentCardActive The development card of the player.
     * @return The structure generates to send to the client.
     */
    private ArrayList<DevelopmentCardToClient> developmentCardActiveToSend( ArrayList<DevelopmentCard> developmentCardActive){
        ArrayList<DevelopmentCardToClient> tmp = new ArrayList<>();
        for(int i=0; i<developmentCardActive.size(); i++){
            DevelopmentCard tmpD = developmentCardActive.get(i);
            if(tmpD!=null){
                tmp.add(new DevelopmentCardToClient(tmpD.getCardID(), tmpD.getColor().name(), tmpD.getLevel(),
                        tmpD.getCost(), tmpD.getVictoryPoint(), tmpD.getMaterialRequired(), tmpD.getProductionResult()));
            }else{
                tmp.add(null);
            }

        }
        return tmp;
    }

    /**
     * Generates a structure with all the information of the development cards on the table of the game to send to the client.
     * @param devCards The development card of the game on the table.
     * @return The structure generates to send to the client.
     */
    private DevelopmentCardToClient[][] developmentCardAvailableToSend(DevelopmentCard[][] devCards){
        DevelopmentCardToClient[][] availableToSend = new DevelopmentCardToClient[4][3];
        DevelopmentCard cardToCopy;

        for(int i=0; i<devCards.length; i++){
            for(int j=0; j<devCards[i].length; j++){
                cardToCopy = devCards[i][j];
                if(cardToCopy!=null) {
                    availableToSend[i][j] = new DevelopmentCardToClient(cardToCopy.getCardID() ,cardToCopy.getColor().name(), cardToCopy.getLevel(),
                            cardToCopy.getCost(), cardToCopy.getVictoryPoint(), cardToCopy.getMaterialRequired(), cardToCopy.getProductionResult());
                }
                else{
                    availableToSend[i][j] = null;
                }
            }
        }
        return availableToSend;
    }

    /**
     * Generates a structure with all the information of market of the game to send to the client.
     * @param market The market of the game.
     * @return The structure generates form the market of the game to send to the client.
     */
    private MarketToClient marketToSend(Market market){
        MarketToClient marketToClient = new MarketToClient(market.getGrid(), market.getOutMarble());
        return marketToClient;
    }

    /**
     * Generates a map with all the information of each player of the game to send to the client.
     * @param players The players of the game.
     * @param faithTrack The faith track of the game.
     * @return The map with all the information of each player of the game to send to the client.
     */
    private  Map<String, PlayerInformationToClient> playerInformationToSend(Player[] players, FaithTrack faithTrack){
        Map<String, PlayerInformationToClient> playerInformation = new HashMap<>();

        for(int i=0; i<players.length; i++){
            Gameboard tmpGameBoard = players[i].getPlayerBoard();
            ArrayList<Resource> additionalType = new ArrayList<>();
            try {
                for(LeaderCard r: players[i].getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive()){
                    if(r!=null){
                        if(r.getSpecialAbility().getEffect().equals("biggerDeposit")){
                            additionalType.add(r.getSpecialAbility().getMaterialType());
                        }
                    }
                }

                PlayerInformationToClient tmp = new PlayerInformationToClient(players[i].getName(), tmpGameBoard.getResourceHandler().getDepositState(),
                        tmpGameBoard.getResourceHandler().getStrongboxState(), additionalType, tmpGameBoard.getResourceHandler().getAdditionalDeposit(), leaderCardToSend(tmpGameBoard.getLeaderCardHandler().getLeaderCardsActive()),
                        developmentCardActiveToSend(tmpGameBoard.getDevelopmentCardHandler().getActiveDevelopmentCard()),
                        tmpGameBoard.getPopeFavorTilesState(), faithTrack.getPosition(i));
                playerInformation.put(players[i].getName(),tmp);
            } catch (LeaderCardException e) {
                PlayerInformationToClient tmp = new PlayerInformationToClient(players[i].getName(), tmpGameBoard.getResourceHandler().getDepositState(),
                        tmpGameBoard.getResourceHandler().getStrongboxState(),additionalType, tmpGameBoard.getResourceHandler().getAdditionalDeposit(), leaderCardToSend(null),
                        developmentCardActiveToSend(tmpGameBoard.getDevelopmentCardHandler().getActiveDevelopmentCard()),
                        tmpGameBoard.getPopeFavorTilesState(), faithTrack.getPosition(i));
                playerInformation.put(players[i].getName(),tmp);
            }

        }
        return playerInformation;
    }

    /**
     * Generates a map with all information of each development card in possession of the player and the sum of all his resources.
     * @param currentPlayer The player whose information generates the map.
     * @return The map with all information of each development card in possession of the player and the sum of all his resources.
     */
    private Map<String, Integer> countNumberOfDevelopmentCards(Player currentPlayer){
        ArrayList<DevelopmentCard> devCollection = currentPlayer.getPlayerBoard().getDevelopmentCardHandler().getDevelopmentCardColl();
        Map<String, Integer> numberOfDevelopment = new HashMap<>();
        String cardKey;

        for(DevelopmentCard developmentCard : devCollection){
            cardKey = developmentCard.getColor().name() + " level: " + developmentCard.getLevel();
            if(numberOfDevelopment.containsKey(cardKey)){
                numberOfDevelopment.put(cardKey, numberOfDevelopment.get(cardKey)+1);
            }else{
                numberOfDevelopment.put(cardKey,1);
            }
        }

        for(Resource r : Resource.values())
            numberOfDevelopment.put(r.toString(), currentPlayer.getPlayerBoard().getResourceHandler().getStrongboxState().get(r));

        ArrayList<Resource> depositTmp = currentPlayer.getPlayerBoard().getResourceHandler().getDepositState();
        for(Resource r: depositTmp){
            if(r!=null)
                numberOfDevelopment.put(r.toString(), numberOfDevelopment.get(r.toString())+1);
        }

        if(currentPlayer.getPlayerBoard().getResourceHandler().getAdditionalDeposit().size()!=0){
            ArrayList<Resource> additionalTmp = currentPlayer.getPlayerBoard().getResourceHandler().getAdditionalDeposit();
            for(Resource r: additionalTmp){
                if(r!=null)
                    numberOfDevelopment.put(r.toString(), numberOfDevelopment.get(r.toString())+1);
            }
        }

        return numberOfDevelopment;
    }


}
