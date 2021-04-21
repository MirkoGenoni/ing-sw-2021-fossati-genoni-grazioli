package it.polimi.ingsw.Events.ClientToServer;

// list of the event that the client could send to the server
public interface EventToServerNotifier {
    void sendNumPlayer(int numPlayer);
    void sendTurnPlayed(String turnType);
    void sendChooseLine(int numLine);
}
