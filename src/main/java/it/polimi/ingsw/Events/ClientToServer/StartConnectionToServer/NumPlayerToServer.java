package it.polimi.ingsw.Events.ClientToServer.StartConnectionToServer;

import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServerVisitor;

public class NumPlayerToServer extends EventToServer {
    private final int numPlayer;
    private final String PlayerName;

    public NumPlayerToServer(int numPlayer, String PlayerName) {
        this.numPlayer = numPlayer;
        this.PlayerName = PlayerName;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
