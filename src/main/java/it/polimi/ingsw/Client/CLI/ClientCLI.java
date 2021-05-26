package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.ConnectionToServer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientCLI {
    private String serverAddress;
    private final int serverPort;
    private Socket socket;

    public static void main(String[] args) {
        ClientCLI clientCLI = new ClientCLI("localhost", 12345);
        clientCLI.startClient();
    }

    public ClientCLI(String serverAddress, int serverPort) {
        this.serverAddress ="";
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void startClient(){
        try{
            System.out.print("\u001B[8;46;123t");
            System.out.print("\u001B[2J\u001B[3J\u001B[H");
            System.out.println("\u001B[48;5;234m");
            System.out.println("\n" +
                    "                   ┌───────────────────────────────────────────────────────────────────────────────────┐                   \n" +
                    "                   │      ╔═══╦═══╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗       ╔═════╗ ╔═════╗      │                   \n" +
                    "                   │      ║ ╔╗ ╔╗ ║ ║ ╔═╗ ║ ║ ╔═══╝ ╚═╗ ╔═╝ ║ ╔═══╝ ║  ══ ║       ║ ╔═╗ ║ ║  ══╦╝      │                   \n" +
                    "                   │      ║ ║║ ║║ ║ ║ ╠═╣ ║ ║ ╚═══╗   ║ ║   ║ ╠═══  ║ ╔══╗║       ║ ║ ║ ║ ║ ╔══╝       │                   \n" +
                    "                   │      ║ ║╚═╝║ ║ ║ ║ ║ ║ ╠════ ║   ║ ║   ║ ╚═══╗ ║ ║  ║║       ║ ╚═╝ ║ ║ ║          │                   \n" +
                    "                   │      ╚═╝   ╚═╝ ╚═╝ ╚═╝ ╚═════╝   ╚═╝   ╚═════╝ ╚═╝  ╚╝       ╚═════╝ ╚═╝          │                   \n" +
                    "                   │                                                                                   │                   \n" +
                    "                   │╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═════╗│                   \n" +
                    "                   │║  ══ ║ ║ ╔═══╝ ║  ╚╝ ║ ║ ╔═╗ ║ ║ ║ ║ ╔═══╝ ║ ╔═══╝ ║ ╔═╗ ║ ║  ╚╝ ║ ║ ╔═══╝ ║ ╔═══╝│                   \n" +
                    "                   │║ ╔══╗║ ║ ╠═══  ║ ╔╗  ║ ║ ╠═╣ ║ ║ ║ ║ ╚═══╗ ║ ╚═══╗ ║ ╠═╣ ║ ║ ╔╗  ║ ║ ║     ║ ╠═══ │                   \n" +
                    "                   │║ ║  ║║ ║ ╚═══╗ ║ ║║  ║ ║ ║ ║ ║ ║ ║ ╠════ ║ ╠════ ║ ║ ║ ║ ║ ║ ║║  ║ ║ ╚═══╗ ║ ╚═══╗│                   \n" +
                    "                   │╚═╝  ╚╝ ╚═════╝ ╚═╝╚══╝ ╚═╝ ╚═╝ ╚═╝ ╚═════╝ ╚═════╝ ╚═╝ ╚═╝ ╚═╝╚══╝ ╚═════╝ ╚═════╝│                   \n" +
                    "                   └───────────────────────────────────────────────────────────────────────────────────┘                   \n" +
                    "                                                                                                                           \n" +
                    "                                                                                                                           \n" +
                    "                                 ▓▓▓                                                                                       \n" +
                    "                           ▓    ▓   ▓                                                                                      \n" +
                    "                            ▓  ▓   ▓                                                                                       \n" +
                    "                             ▓▓   ▓                                                                                        \n" +
                    "                              ▓▓▓▓▓                                                                                        \n" +
                    "                              ▓                                                                                            \n" +
                    "                             ▓                                                                   ▓▓                        \n" +
                    "                            ▓          ▓▓▓                                                   ▓  ▓  ▓                       \n" +
                    "                        ▓▓▓▓▓  ▓▓▓▓▓▓▓    ▓   ▓▓▓    ▓▓ ▓▓      ▓▓▓▓▓▓  ▓▓▓▓▓▓                 ▓  ▓                        \n" +
                    "                       ▓  ▓  ▓ ▓   ▓      ▓  ▓   ▓  ▓  ▓  ▓    ▓    ▓  ▓   ▓                ▓  ▓ ▓                         \n" +
                    "                      ▓  ▓    ▓▓   ▓      ▓  ▓   ▓ ▓   ▓  ▓   ▓    ▓   ▓   ▓               ▓▓   ▓                          \n" +
                    "                     ▓  ▓      ▓   ▓       ▓  ▓▓▓  ▓       ▓  ▓   ▓    ▓   ▓              ▓ ▓  ▓ ▓                         \n" +
                    "                     ▓▓▓        ▓▓▓         ▓▓   ▓▓         ▓▓   ▓▓▓▓▓  ▓▓▓            ▓▓▓   ▓▓   ▓▓                       \n" +
                    "                                                                                                                           \n" +
                    "                                                                                                                           \n" +
                    "                                                                              ▓▓                                           \n" +
                    "                         ▓▓▓▓ ▓▓▓▓ ▓▓▓▓                                      ▓  ▓                                          \n" +
                    "                        ▓    ▓    ▓    ▓                              ▓      ▓  ▓    ▓                                     \n" +
                    "                        ▓    ▓    ▓    ▓   ▓▓▓     ▓▓▓▓     ▓▓ ▓▓            ▓  ▓         ▓▓▓  ▓▓▓▓▓▓                      \n" +
                    "                        ▓    ▓    ▓    ▓  ▓   ▓   ▓    ▓   ▓  ▓  ▓    ▓    ▓ ▓ ▓     ▓   ▓     ▓   ▓                       \n" +
                    "                        ▓    ▓    ▓    ▓  ▓   ▓   ▓    ▓  ▓   ▓  ▓   ▓▓     ▓ ▓     ▓▓   ▓     ▓   ▓                       \n" +
                    "                       ▓               ▓  ▓   ▓    ▓  ▓  ▓       ▓  ▓ ▓    ▓ ▓▓    ▓ ▓   ▓     ▓   ▓                       \n" +
                    "                      ▓                 ▓▓ ▓▓▓ ▓▓▓  ▓▓  ▓         ▓▓   ▓▓▓▓  ▓ ▓▓▓▓   ▓▓▓ ▓▓▓▓▓ ▓▓▓                        \n" +
                    "                                                   ▓  ▓                      ▓                                             \n" +
                    "                                                  ▓   ▓                      ▓                                             \n" +
                    "                                                 ▓   ▓                       ▓                                             \n" +
                    "                                                  ▓▓▓                                                                      ");

            TimeUnit.SECONDS.sleep(5);

            for(int i=0; i<18; i++)
                System.out.println();

            System.out.print("\u001B[2J\u001B[3J\u001B[H");
            System.out.print("\n" +
                    "                   ┌───────────────────────────────────────────────────────────────────────────────────┐                   \n" +
                    "                   │      ╔═══╦═══╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗       ╔═════╗ ╔═════╗      │                   \n" +
                    "                   │      ║ ╔╗ ╔╗ ║ ║ ╔═╗ ║ ║ ╔═══╝ ╚═╗ ╔═╝ ║ ╔═══╝ ║  ══ ║       ║ ╔═╗ ║ ║  ══╦╝      │                   \n" +
                    "                   │      ║ ║║ ║║ ║ ║ ╠═╣ ║ ║ ╚═══╗   ║ ║   ║ ╠═══  ║ ╔══╗║       ║ ║ ║ ║ ║ ╔══╝       │                   \n" +
                    "                   │      ║ ║╚═╝║ ║ ║ ║ ║ ║ ╠════ ║   ║ ║   ║ ╚═══╗ ║ ║  ║║       ║ ╚═╝ ║ ║ ║          │                   \n" +
                    "                   │      ╚═╝   ╚═╝ ╚═╝ ╚═╝ ╚═════╝   ╚═╝   ╚═════╝ ╚═╝  ╚╝       ╚═════╝ ╚═╝          │                   \n" +
                    "                   │                                                                                   │                   \n" +
                    "                   │╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═════╗│                   \n" +
                    "                   │║  ══ ║ ║ ╔═══╝ ║  ╚╝ ║ ║ ╔═╗ ║ ║ ║ ║ ╔═══╝ ║ ╔═══╝ ║ ╔═╗ ║ ║  ╚╝ ║ ║ ╔═══╝ ║ ╔═══╝│                   \n" +
                    "                   │║ ╔══╗║ ║ ╠═══  ║ ╔╗  ║ ║ ╠═╣ ║ ║ ║ ║ ╚═══╗ ║ ╚═══╗ ║ ╠═╣ ║ ║ ╔╗  ║ ║ ║     ║ ╠═══ │                   \n" +
                    "                   │║ ║  ║║ ║ ╚═══╗ ║ ║║  ║ ║ ║ ║ ║ ║ ║ ╠════ ║ ╠════ ║ ║ ║ ║ ║ ║ ║║  ║ ║ ╚═══╗ ║ ╚═══╗│                   \n" +
                    "                   │╚═╝  ╚╝ ╚═════╝ ╚═╝╚══╝ ╚═╝ ╚═╝ ╚═╝ ╚═════╝ ╚═════╝ ╚═╝ ╚═╝ ╚═╝╚══╝ ╚═════╝ ╚═════╝│                   \n" +
                    "                   └───────────────────────────────────────────────────────────────────────────────────┘                   \n");

            System.out.print("                                                                                                                          \n"+
                    "                                                                                                                          \n" +
                    "                                                                                                                          \n" +
                    "                                                                                                                          \n");

            System.out.print("                                    INSERT SERVER ADDRESS: ");

            Scanner userIn = new Scanner(System.in);

            serverAddress = userIn.nextLine();


            socket = new Socket(serverAddress, serverPort);
            ConnectionToServer connectionToServer = new ConnectionToServer(socket);
            new Thread(connectionToServer).start();
        } catch (IOException e) {
            // System.out.println("connessione rifiutata: server non disponibile");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
