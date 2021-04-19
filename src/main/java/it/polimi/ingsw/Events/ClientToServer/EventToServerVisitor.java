package it.polimi.ingsw.Events.ClientToServer;

// list of the event that the server receive form client
public interface EventToServerVisitor {
    void visit(TurnPlayedToServer turn);
    void visit(ChooseLineToServer numLine);
}
