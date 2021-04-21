package it.polimi.ingsw.Events.ServerToClient;

import java.io.IOException;

// list of the event that the client receive from the server
public interface EventToClientVisitor {
    void visit(SendPlayerNameToClient playerName);
    void visit(SendNumPlayerToClient numPlayer);
    void visit(NotifyClient message);
    void visit(NewTurnToClient notify);
    void visit(MarketToClient market);

}
