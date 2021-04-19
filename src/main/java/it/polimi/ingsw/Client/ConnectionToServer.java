package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.CLI.CLI;
import it.polimi.ingsw.Events.ClientToServer.ChooseLineToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServerNotifier;
import it.polimi.ingsw.Events.ClientToServer.TurnPlayedToServer;
import it.polimi.ingsw.Events.ServerToClient.EventToClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    // list of the event to send to server
    @Override
    public void sendTurnPlayed(String turnType) {
        TurnPlayedToServer turnPlayedToServer = new TurnPlayedToServer(turnType, this.playerName);
        new Thread(()-> asyncSendEvent(turnPlayedToServer)).start();
    }

    @Override
    public void sendChooseLine(int numLine) {
        ChooseLineToServer chooseLineToServer = new ChooseLineToServer(numLine);
        new Thread(() -> asyncSendEvent(chooseLineToServer)).start();
    }


}
