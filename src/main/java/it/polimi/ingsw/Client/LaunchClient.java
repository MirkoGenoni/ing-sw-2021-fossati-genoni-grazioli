package it.polimi.ingsw.Client;

import java.io.IOException;

public class LaunchClient {
    public static void main(String[] args) {
        try{
            Client client = new Client("localhost", 12345);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
