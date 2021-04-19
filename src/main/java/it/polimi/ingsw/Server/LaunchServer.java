package it.polimi.ingsw.Server;

import java.io.IOException;

public class LaunchServer {
    public static void main(String[] args) {
        try{
            Server server = new Server();
            new Thread(server).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
