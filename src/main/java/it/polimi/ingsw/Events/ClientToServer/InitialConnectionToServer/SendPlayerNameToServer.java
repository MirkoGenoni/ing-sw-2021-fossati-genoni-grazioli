package it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer;

import it.polimi.ingsw.Server.RoomHandler.EventToServerInitialVisitor;

public class SendPlayerNameToServer extends EventToServerInitial{
    private final String playerName;

    public SendPlayerNameToServer(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptInitialServerVisitor(EventToServerInitialVisitor visitor) {
        visitor.visit(this);
    }
}
