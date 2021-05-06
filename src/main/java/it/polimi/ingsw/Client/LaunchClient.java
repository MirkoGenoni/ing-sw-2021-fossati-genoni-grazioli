package it.polimi.ingsw.Client;

import java.io.IOException;

public class LaunchClient {
    public static void main(String[] args) {

        /*
        System.out.println("Indirizzo del server:");
        Scanner scanIn = new Scanner(System.in);
        String address = scanIn.nextLine();
        System.out.println("Numero porta del server:");
        int port = scanIn.nextInt();
        try {
            Client client = new Client(address, port);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        try{
            ClientCLI clientCLI = new ClientCLI("localhost", 12345);
            clientCLI.startClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
