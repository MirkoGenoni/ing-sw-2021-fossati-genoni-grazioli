package it.polimi.ingsw.Server;

import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ServerToClient.*;
import it.polimi.ingsw.Controller.ObserveConnectionToClient;
import it.polimi.ingsw.Model.Market.Marble;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionToClient implements Runnable, EventToClientNotifier {
    private Socket clientSocket;
    private boolean active;
    private String namePlayer;
    private int numPlayer;

    //receiver of the event from the client
    private ObserveConnectionToClient observeConnectionToClient;

    // Input and Output steams
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ConnectionToClient(Socket clientSocket){
        this.clientSocket = clientSocket;
        active = true;
        numPlayer = 0;

        try{
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try{
            while(isActive()){
                EventToServer event = receiveEvent();
                observeConnectionToClient.observeEvent(event); // ControllerConnection
                Thread.sleep(10);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void sendEvent(EventToClient event){
        if(isActive()){
            try{
                output.writeObject(event);   // write the event
                output.flush();              // send the event
                output.reset();              // clean buffer
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // send the event with a thread to the client of this connection
    private synchronized void asyncSendEvent(EventToClient event){
        new Thread(() ->{
            sendEvent(event);
        }).start();
    }

    // receive the event form the client of this connection
    private EventToServer receiveEvent() throws IOException, ClassNotFoundException {
        return (EventToServer) input.readObject();
    }

    // close the connection and set the client like inactive
    private void closeConnection(){
        try{
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setActive(false);
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public void setObserveConnectionToClient(ObserveConnectionToClient observeConnectionToClient){
        this.observeConnectionToClient = observeConnectionToClient;
    }


    @Override
    public void sendPlayerName(String playerName) {
        SendPlayerNameToClient sendPlayerNameToClient = new SendPlayerNameToClient(playerName);
        new Thread(() -> asyncSendEvent(sendPlayerNameToClient)).start();
    }

    @Override
    public void sendNumPlayer(String message) {
        SendNumPlayerToClient sendNumPlayerToClient = new SendNumPlayerToClient(message);
        new Thread(() -> asyncSendEvent(sendNumPlayerToClient)).start();
    }

    @Override
    public void sendNotify(String message) {
        NotifyClient notifyClient = new NotifyClient(message);
        new Thread(() -> asyncSendEvent(notifyClient)).start();
    }

    @Override
    public void sendNewTurn(int turnNumber) {
        NewTurnToClient newTurnToClient = new NewTurnToClient(turnNumber);
        new Thread(() -> asyncSendEvent(newTurnToClient)).start();
    }

    @Override
    public void sendMarket(ArrayList<Marble> grid, Marble outMarble) {
        MarketToClient marketToClient = new MarketToClient(grid, outMarble);
        new Thread(() -> asyncSendEvent(marketToClient)).start();
    }
}
