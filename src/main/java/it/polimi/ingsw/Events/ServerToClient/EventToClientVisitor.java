package it.polimi.ingsw.Events.ServerToClient;

// list of the event that the client receive from the server
public interface EventToClientVisitor {
    void visit(SendPlayerNameToClient playerName);
    void visit(SendNumPlayerToClient numPlayer);
    void visit(SendLeaderCardToClient leaderCard);
    void visit(SendArrayLeaderCardsToClient leaderCardArray);
    void visit(NotifyToClient message);
    void visit(NewTurnToClient notify);
    void visit(MarketToClient market);

}
