package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.CLI.Views.Messages;
import it.polimi.ingsw.Client.ConnectionToServer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientCLI {
    private String serverAddress;
    private int serverPort;
    private Socket socket;

    public static void main(String[] args) {
        ClientCLI clientCLI = new ClientCLI("localhost", 12345);
        clientCLI.startClient();
    }

    public ClientCLI(String serverAddress, int serverPort) {
        this.serverAddress = "";
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void startClient() {
        System.out.print("\u001B[8;46;123t");
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
        //System.out.println("\u001B[48;5;234m");
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

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        for (int i = 0; i < 18; i++)
            System.out.println();

        while(true) {
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

            System.out.print("                                                                                                                          \n" +
                "                                                                                                                          \n" +
                "                                                                                                                          \n" +
                "                                                                                                                          \n");

            System.out.print("                                    INSERT SERVER ADDRESS: ");

            Scanner userIn = new Scanner(System.in);

            serverAddress = userIn.nextLine();

            System.out.print("                                    INSERT SERVER PORT: ");

            while(true) {
                String serverPortString = userIn.nextLine();

                if (!serverPortString.equals("default")) {
                    try {
                        serverPort = Integer.parseInt(serverPortString);
                        break;
                    } catch (NumberFormatException e) {
                        continue;
                    }
                } else {
                    break;
                }
            }

            try {
                socket = new Socket(serverAddress, serverPort);
                ConnectionToServer connectionToServer = new ConnectionToServer(socket);
                connectionToServer.setAddress(serverAddress);
                new Thread(connectionToServer).start();
                break;
            } catch (IOException e) {
                Messages messages = new Messages("Insert a correct address", true);
                messages.printMessage();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }

    }
}

