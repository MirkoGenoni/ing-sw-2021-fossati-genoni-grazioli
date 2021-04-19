package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Model.Market.Marble;

import java.util.ArrayList;

// list of the event that the server could sent to the client
public interface EventToClientNotifier {
    void sendPlayerName(String playerName);
    void sendStartGame(String message);
    void sendNotify(String message);
    void sendNewTurn(int turnNumber);
    void sendMarket(ArrayList<Marble> grid, Marble outMarble);
}
