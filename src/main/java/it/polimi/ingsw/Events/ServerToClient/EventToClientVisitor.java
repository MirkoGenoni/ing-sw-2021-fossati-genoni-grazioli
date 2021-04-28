package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendDevelopmentCardAvailableToClient;
import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendDevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.MarketTurnToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.SendReorganizeDepositToClient;
import it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient.SendNumPlayerToClient;
import it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient.SendPlayerNameToClient;

// list of the event that the client receive from the server
public interface EventToClientVisitor {
    void visit(SendPlayerNameToClient playerName);
    void visit(SendNumPlayerToClient numPlayer);
    void visit(SendLeaderCardToClient leaderCard);
    void visit(SendArrayLeaderCardsToClient leaderCardArray);
    void visit(SendReorganizeDepositToClient newResources);
    void visit(SendDevelopmentCardToClient developmentCard);
    void visit (SendDevelopmentCardAvailableToClient availableDevelopmentCards);
    void visit(NotifyToClient message);
    void visit(NewTurnToClient notify);
    void visit(MarketTurnToClient market);

}
