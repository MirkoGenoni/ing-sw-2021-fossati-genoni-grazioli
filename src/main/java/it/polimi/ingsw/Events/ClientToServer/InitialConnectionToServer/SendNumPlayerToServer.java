package it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer;

import it.polimi.ingsw.Server.RoomHandler.EventToServerInitialVisitor;

public class SendNumPlayerToServer extends EventToServerInitial{
    private final int numPlayer;
    private final String PlayerName;

    public SendNumPlayerToServer(int numPlayer, String playerName) {
        this.numPlayer = numPlayer;
        PlayerName = playerName;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    @Override
    public void acceptInitialServerVisitor(EventToServerInitialVisitor visitor) {
        visitor.visit(this);
    }
}
