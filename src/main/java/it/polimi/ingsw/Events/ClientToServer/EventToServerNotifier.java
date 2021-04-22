package it.polimi.ingsw.Events.ClientToServer;

// list of the event that the client could send to the server
public interface EventToServerNotifier {
    void sendNumPlayer(int numPlayer);
    void sendDiscardInitialLeaderCards(int leaderCard1, int leaderCard2);
    void sendTurnPlayed(String turnType);
    void sendChooseLine(int numLine);
}
