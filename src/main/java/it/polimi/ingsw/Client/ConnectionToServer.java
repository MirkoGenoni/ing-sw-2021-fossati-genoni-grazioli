package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.CLI.CLI;
import it.polimi.ingsw.Events.ClientToServer.*;
import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Model.Resource.Resource;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionToServer implements Runnable, EventToServerNotifier {
    private final Socket socket;
    private boolean active;
    private String playerName;
    private CLI cli;

    // Input and Output steams
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ConnectionToServer(Socket socket) {
        this.socket = socket;
        this.active = true;
        cli = new CLI(this);
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
                cli.receiveEvent(event);

            } catch (IOException | ClassNotFoundException e) {
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
        }
    }

    // send event to the server with a thread
    public synchronized void asyncSendEvent(EventToServer event){
        new Thread(() ->
                sendEvent(event)
        ).start();
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
    @Override
    public void sendNumPlayer(int numPlayer) {
        NumPlayerToServer numPlayerToServer = new NumPlayerToServer(numPlayer, this.playerName);
        new Thread(() -> asyncSendEvent(numPlayerToServer)).start();
    }

    @Override
    public void sendNewPlayerName(String newPlayerName) {
        PlayerNameToServer playerNameToServer = new PlayerNameToServer(newPlayerName, this.playerName);
        new Thread(() -> asyncSendEvent(playerNameToServer)).start();
    }

    @Override
    public void sendDiscardInitialLeaderCards(int leaderCard1, int leaderCard2) {
        DiscardInitialLeaderCards discardInitialLeaderCards = new DiscardInitialLeaderCards(leaderCard1, leaderCard2, this.playerName);
        new Thread(() -> asyncSendEvent(discardInitialLeaderCards)).start();
    }

    @Override
    public void sendNewDepositState(ArrayList<Resource> newDepositState, int discardResources) {
        NewDepositStateToServer newDepositStateToServer = new NewDepositStateToServer(newDepositState, discardResources, this.playerName);
        new Thread(() -> asyncSendEvent(newDepositStateToServer)).start();
    }

    @Override
    public void sendTurnPlayed(String turnType) {
        TurnPlayedToServer turnPlayedToServer = new TurnPlayedToServer(turnType, this.playerName);
        new Thread(()-> asyncSendEvent(turnPlayedToServer)).start();
    }

    @Override
    public void sendChooseLine(int numLine) {
        ChooseLineToServer chooseLineToServer = new ChooseLineToServer(numLine, this.playerName);
        new Thread(() -> asyncSendEvent(chooseLineToServer)).start();
    }


}
