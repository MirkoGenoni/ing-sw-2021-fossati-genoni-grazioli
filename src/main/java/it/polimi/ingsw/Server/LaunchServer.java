package it.polimi.ingsw.Server;


public class LaunchServer {
    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start();
    }
}
