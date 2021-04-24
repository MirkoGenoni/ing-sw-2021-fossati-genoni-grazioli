package it.polimi.ingsw.Events.ClientToServer;

import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

// list of the event that the client could send to the server
public interface EventToServerNotifier {
    void sendNumPlayer(int numPlayer);
    void sendNewPlayerName(String newPlayerName);
    void sendDiscardInitialLeaderCards(int leaderCard1, int leaderCard2);
    void sendNewDepositState(ArrayList<Resource> newDepositState, ArrayList<Resource> discardResources);
    void sendTurnPlayed(String turnType);
    void sendChooseLine(int numLine);
}
