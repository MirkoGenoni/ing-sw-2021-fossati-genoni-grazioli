package it.polimi.ingsw.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientCLI {
    private String serverAddress;
    private final int serverPort;
    private Socket socket;

    public ClientCLI(String serverAddress, int serverPort) {
        this.serverAddress ="";
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void startClient() throws IOException {
        try{
            System.out.printf("\u001B[2J\u001B[3J\u001B[H");
            System.out.println("\u001B[48;5;234m");
            System.out.println("\n" +
                    "                 ┌───────────────────────────────────────────────────────────────────────────────────┐                   \n" +
                    "                 │      ╔═══╦═══╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗       ╔═════╗ ╔═════╗      │                   \n" +
                    "                 │      ║ ╔╗ ╔╗ ║ ║ ╔═╗ ║ ║ ╔═══╝ ╚═╗ ╔═╝ ║ ╔═══╝ ║  ══ ║       ║ ╔═╗ ║ ║  ══╦╝      │                   \n" +
                    "                 │      ║ ║║ ║║ ║ ║ ╠═╣ ║ ║ ╚═══╗   ║ ║   ║ ╠═══  ║ ╔══╗║       ║ ║ ║ ║ ║ ╔══╝       │                   \n" +
                    "                 │      ║ ║╚═╝║ ║ ║ ║ ║ ║ ╠════ ║   ║ ║   ║ ╚═══╗ ║ ║  ║║       ║ ╚═╝ ║ ║ ║          │                   \n" +
                    "                 │      ╚═╝   ╚═╝ ╚═╝ ╚═╝ ╚═════╝   ╚═╝   ╚═════╝ ╚═╝  ╚╝       ╚═════╝ ╚═╝          │                   \n" +
                    "                 │                                                                                   │                   \n" +
                    "                 │╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═════╗│                   \n" +
                    "                 │║  ══ ║ ║ ╔═══╝ ║  ╚╝ ║ ║ ╔═╗ ║ ║ ║ ║ ╔═══╝ ║ ╔═══╝ ║ ╔═╗ ║ ║  ╚╝ ║ ║ ╔═══╝ ║ ╔═══╝│                   \n" +
                    "                 │║ ╔══╗║ ║ ╠═══  ║ ╔╗  ║ ║ ╠═╣ ║ ║ ║ ║ ╚═══╗ ║ ╚═══╗ ║ ╠═╣ ║ ║ ╔╗  ║ ║ ║     ║ ╠═══ │                   \n" +
                    "                 │║ ║  ║║ ║ ╚═══╗ ║ ║║  ║ ║ ║ ║ ║ ║ ║ ╠════ ║ ╠════ ║ ║ ║ ║ ║ ║ ║║  ║ ║ ╚═══╗ ║ ╚═══╗│                   \n" +
                    "                 │╚═╝  ╚╝ ╚═════╝ ╚═╝╚══╝ ╚═╝ ╚═╝ ╚═╝ ╚═════╝ ╚═════╝ ╚═╝ ╚═╝ ╚═╝╚══╝ ╚═════╝ ╚═════╝│                   \n" +
                    "                 └───────────────────────────────────────────────────────────────────────────────────┘                   \n" +
                    "                                                                                                                         \n" +
                    "                               ▓▓▓                                                                                       \n" +
                    "                         ▓    ▓   ▓                                                                                      \n" +
                    "                          ▓  ▓   ▓                                                                                       \n" +
                    "                           ▓▓   ▓                                                                                        \n" +
                    "                            ▓▓▓▓▓                                                                                        \n" +
                    "                            ▓                                                                                            \n" +
                    "                           ▓                                                                   ▓▓                        \n" +
                    "                          ▓          ▓▓▓                                                   ▓  ▓  ▓                       \n" +
                    "                      ▓▓▓▓▓  ▓▓▓▓▓▓▓    ▓   ▓▓▓    ▓▓ ▓▓      ▓▓▓▓▓▓  ▓▓▓▓▓▓                 ▓  ▓                        \n" +
                    "                     ▓  ▓  ▓ ▓   ▓      ▓  ▓   ▓  ▓  ▓  ▓    ▓    ▓  ▓   ▓                ▓  ▓ ▓                         \n" +
                    "                    ▓  ▓    ▓▓   ▓      ▓  ▓   ▓ ▓   ▓  ▓   ▓    ▓   ▓   ▓               ▓▓   ▓                          \n" +
                    "                   ▓  ▓      ▓   ▓       ▓  ▓▓▓  ▓       ▓  ▓   ▓    ▓   ▓              ▓ ▓  ▓ ▓                         \n" +
                    "                   ▓▓▓        ▓▓▓         ▓▓   ▓▓         ▓▓   ▓▓▓▓▓  ▓▓▓            ▓▓▓   ▓▓   ▓▓                       \n" +
                    "                                                                                                                         \n" +
                    "                                                                                                                         \n" +
                    "                                                                            ▓▓                                           \n" +
                    "                       ▓▓▓▓ ▓▓▓▓ ▓▓▓▓                                      ▓  ▓                                          \n" +
                    "                      ▓    ▓    ▓    ▓                              ▓      ▓  ▓    ▓                                     \n" +
                    "                      ▓    ▓    ▓    ▓   ▓▓▓     ▓▓▓▓     ▓▓ ▓▓            ▓  ▓         ▓▓▓  ▓▓▓▓▓▓                      \n" +
                    "                      ▓    ▓    ▓    ▓  ▓   ▓   ▓    ▓   ▓  ▓  ▓    ▓    ▓ ▓ ▓     ▓   ▓     ▓   ▓                       \n" +
                    "                      ▓    ▓    ▓    ▓  ▓   ▓   ▓    ▓  ▓   ▓  ▓   ▓▓     ▓ ▓     ▓▓   ▓     ▓   ▓                       \n" +
                    "                     ▓               ▓  ▓   ▓    ▓  ▓  ▓       ▓  ▓ ▓    ▓ ▓▓    ▓ ▓   ▓     ▓   ▓                       \n" +
                    "                    ▓                 ▓▓ ▓▓▓ ▓▓▓  ▓▓  ▓         ▓▓   ▓▓▓▓  ▓ ▓▓▓▓   ▓▓▓ ▓▓▓▓▓ ▓▓▓                        \n" +
                    "                                                 ▓  ▓                      ▓                                             \n" +
                    "                                                ▓   ▓                      ▓                                             \n" +
                    "                                               ▓   ▓                       ▓                                             \n" +
                    "                                                ▓▓▓                                                                        \n");

            TimeUnit.SECONDS.sleep(5);

            for(int i=0; i<18; i++)
                System.out.println();

            System.out.printf("\u001B[2J\u001B[3J\u001B[H");
            System.out.printf("\n" +
                    "                 ┌───────────────────────────────────────────────────────────────────────────────────┐\n" +
                    "                 │      ╔═══╦═══╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔═════╗       ╔═════╗ ╔═════╗      │\n" +
                    "                 │      ║ ╔╗ ╔╗ ║ ║ ╔═╗ ║ ║ ╔═══╝ ╚═╗ ╔═╝ ║ ╔═══╝ ║  ══ ║       ║ ╔═╗ ║ ║  ══╦╝      │\n" +
                    "                 │      ║ ║║ ║║ ║ ║ ╠═╣ ║ ║ ╚═══╗   ║ ║   ║ ╠═══  ║ ╔══╗║       ║ ║ ║ ║ ║ ╔══╝       │\n" +
                    "                 │      ║ ║╚═╝║ ║ ║ ║ ║ ║ ╠════ ║   ║ ║   ║ ╚═══╗ ║ ║  ║║       ║ ╚═╝ ║ ║ ║          │\n" +
                    "                 │      ╚═╝   ╚═╝ ╚═╝ ╚═╝ ╚═════╝   ╚═╝   ╚═════╝ ╚═╝  ╚╝       ╚═════╝ ╚═╝          │\n" +
                    "                 │                                                                                   │\n" +
                    "                 │╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═╗ ╔═════╗ ╔═════╗ ╔═════╗ ╔══╗╔═╗ ╔═════╗ ╔═════╗│\n" +
                    "                 │║  ══ ║ ║ ╔═══╝ ║  ╚╝ ║ ║ ╔═╗ ║ ║ ║ ║ ╔═══╝ ║ ╔═══╝ ║ ╔═╗ ║ ║  ╚╝ ║ ║ ╔═══╝ ║ ╔═══╝│\n" +
                    "                 │║ ╔══╗║ ║ ╠═══  ║ ╔╗  ║ ║ ╠═╣ ║ ║ ║ ║ ╚═══╗ ║ ╚═══╗ ║ ╠═╣ ║ ║ ╔╗  ║ ║ ║     ║ ╠═══ │\n" +
                    "                 │║ ║  ║║ ║ ╚═══╗ ║ ║║  ║ ║ ║ ║ ║ ║ ║ ╠════ ║ ╠════ ║ ║ ║ ║ ║ ║ ║║  ║ ║ ╚═══╗ ║ ╚═══╗│\n" +
                    "                 │╚═╝  ╚╝ ╚═════╝ ╚═╝╚══╝ ╚═╝ ╚═╝ ╚═╝ ╚═════╝ ╚═════╝ ╚═╝ ╚═╝ ╚═╝╚══╝ ╚═════╝ ╚═════╝│\n" +
                    "                 └───────────────────────────────────────────────────────────────────────────────────┘\n");

            System.out.printf("                                                                                                                          \n"+
                    "                                                                                                                          \n" +
                    "                                                                                                                          \n" +
                    "                                                                                                                          \n");

            System.out.print("                                   INSERT SERVER ADDRESS: ");

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
