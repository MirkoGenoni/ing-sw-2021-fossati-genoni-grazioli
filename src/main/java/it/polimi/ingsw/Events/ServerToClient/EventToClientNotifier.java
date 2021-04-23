package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Market.Marble;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

// list of the event that the server could sent to the client
public interface EventToClientNotifier {
    void sendPlayerName(String playerName);
    void sendNumPlayer(String message);
    void sendLeaderCard(LeaderCard leaderCard);
    void sendArrayLeaderCards(ArrayList<LeaderCard> leaderCards);
    void sendReorganizeDeposit(ArrayList<Resource> marketResources, ArrayList<Resource> depositState);
    void sendNotify(String message);
    void sendNewTurn(int turnNumber);
    void sendMarket(ArrayList<Marble> grid, Marble outMarble);
}
