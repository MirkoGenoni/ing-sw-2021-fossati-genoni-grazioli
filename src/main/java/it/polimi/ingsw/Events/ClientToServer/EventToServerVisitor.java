package it.polimi.ingsw.Events.ClientToServer;

import it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer.ChooseLineToServer;
import it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer.NewDepositStateToServer;
import it.polimi.ingsw.Events.ClientToServer.StartConnectionToServer.NumPlayerToServer;
import it.polimi.ingsw.Events.ClientToServer.StartConnectionToServer.PlayerNameToServer;
import it.polimi.ingsw.Events.ClientToServer.StartGameToServer.DiscardInitialLeaderCards;

// list of the event that the server receive form client
public interface EventToServerVisitor {
    void visit(NumPlayerToServer numPlayer);
    void visit(PlayerNameToServer newPlayerName);
    void visit(DiscardInitialLeaderCards leaderCards);
    void visit(NewDepositStateToServer newDepositState);
    void visit(TurnPlayedToServer turn);
    void visit(ChooseLineToServer numLine);
}
