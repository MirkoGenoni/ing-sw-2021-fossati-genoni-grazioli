package it.polimi.ingsw.Server.RoomHandler;

import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendNumPlayerToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendPlayerNameToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendRoomToServer;

public interface EventToServerInitialVisitor {
    void visit(SendRoomToServer room);
    void visit(SendPlayerNameToServer playerName);

    void visit(SendNumPlayerToServer numPlayer);
}
