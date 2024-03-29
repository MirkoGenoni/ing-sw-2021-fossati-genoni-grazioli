package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.cli.views.Messages;
import it.polimi.ingsw.client.ConnectionToServer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This class is the first page of the cli but it initializes only the socket, asking the user to insert only
 * the ip address and the server port
 *
 * @author Mirko Genoni
 */
public class StartCLI {
    private String serverAddress;
    private int serverPort;
    private Socket socket;

    /**
     * Constructor of the class, initializes only the standard ip address and port, that will be modified later
     * @param serverAddress standard ip address
     * @param serverPort standart server port
     */
    public StartCLI(String serverAddress, int serverPort) {
        this.serverAddress = "";
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * This method prints the login page and parse the input only of the address and the port
     */
    public void startClient() {
        System.out.print("\u001B[8;46;123t");
        System.out.print("\u001B[2J\u001B[3J\u001B[H");
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
                    } catch (IllegalArgumentException e) {
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

