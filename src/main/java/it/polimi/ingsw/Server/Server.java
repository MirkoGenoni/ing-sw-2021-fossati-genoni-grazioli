package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.ControllerConnection;
import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Game.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private final ServerSocket serverSocket;
    private final ArrayList<ConnectionToClient> clientList;
    private final ArrayList<Player> players;
    private ControllerConnection controllerConnection;
    private ControllerToModel controllerToModel;
    private int numPlayer;


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(12345);
        this.clientList = new ArrayList<>();
        this.players = new ArrayList<>(); // in teoria non serve più
        this.numPlayer = 0;
    }

    @Override
    public void run() {
        try{
            int i = 0;
            System.out.println("Server is running...");

            // creo prima il controller
            controllerToModel = new ControllerToModel();
            controllerConnection = new ControllerConnection(controllerToModel);

            while(clientList.size() < numPlayer || numPlayer == 0){
                // inizio ad accettare connessioni
                System.out.println("attendo altre connessioni...");
                Socket clientSocket = serverSocket.accept();
                i++;
                String playerName = "Player " + (i);
                System.out.println("New client is connected " + playerName);
                // salvo la connessione con il client
                ConnectionToClient connectionToClient = new ConnectionToClient(clientSocket);

                clientList.add(connectionToClient);
                new Thread(connectionToClient).start();

                // setto il controller come colui che riceve gli eventi dalla connessione al client
                connectionToClient.setObserveConnectionToClient(controllerConnection);
                // aggiungo la connessione al client al Controller to model in modo che possa notificare gli eventi al client
                controllerToModel.addConnectionToClient(connectionToClient);
                connectionToClient.setNamePlayer(playerName);
                connectionToClient.sendPlayerName(playerName);

                if(connectionToClient.getNamePlayer().equals("Player " + (i))){
                    System.out.println("aspetto il nome utente");
                    while(connectionToClient.getNamePlayer().equals("Player " + (i))){
                        try{
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


                // se il numPlayer è ancora a zero significa che è il primo client connesso
                if(numPlayer == 0){
                    connectionToClient.sendNumPlayer("dammi il numero giocatori");
                    System.out.println("aspetto il numero giocatori");

                    // aspetta che il primo client connesso mandi il numero di giocatori al controller per poter creare la partita
                    while(controllerToModel.getNumPlayer() == 0){
                        try{
                            Thread.sleep(10 );
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    numPlayer = controllerToModel.getNumPlayer();
                }

            }
            System.out.println("tutti gli utenti sono connessi");

            System.out.println("inizia la partita");
            controllerToModel.startMatch();

            System.out.println("notificati tutti i client");

        } catch (IOException | StartGameException e) {
            e.printStackTrace();
        }
    }
}
