package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.ControllerConnection;
import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Game.MultiPlayerGame;
import it.polimi.ingsw.Model.Game.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private final ServerSocket serverSocket;
    private final ArrayList<ConnectionToClient> clientList;
    private final ArrayList<Player> players;
    private MultiPlayerGame game;
    private ControllerConnection controllerConnection;
    private ControllerToModel controllerToModel;
    private int numPlayer;


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(12345);
        this.clientList = new ArrayList<>();
        this.players = new ArrayList<>();
        this.numPlayer = 3;
    }

    @Override
    public void run() {
        try{
            System.out.println("Server is running...");

            while(clientList.size() < 3){
                Socket clientSocket = serverSocket.accept();
                String playerName = "Player" + (players.size()+1);
                System.out.println("New client is connected" + playerName);
                players.add(new Player(playerName));
                ConnectionToClient connectionToClient = new ConnectionToClient(clientSocket);
                clientList.add(connectionToClient);
                new Thread(connectionToClient).start();
                connectionToClient.sendPlayerName(playerName);
            }
            System.out.println("tutti gli utenti sono connessi");

            // creo il game con i player
            game = new MultiPlayerGame(3);
            System.out.println("controllerToModel creato");
            for(int i = 0; i<players.size(); i++){
                game.addPlayer(players.get(i));
            }

            // creo le classi del controller
            controllerToModel = new ControllerToModel(clientList, players.toArray(new Player[players.size()]), game);
            controllerConnection = new ControllerConnection(controllerToModel);
            clientList.forEach(cl -> cl.setObserveConnectionToClient(controllerConnection));  // setto il controllerConnction come colui che riceve gli
                                                                                              // eventi dalla connection to server e li parsa
                                                                                              // invocando metodi del controllerTomModel


            System.out.println("inizia la partita");
            controllerToModel.startMatch();
            System.out.println("notificati tutti i client");

        } catch (IOException | StartGameException e) {
            e.printStackTrace();
        }
    }
}
