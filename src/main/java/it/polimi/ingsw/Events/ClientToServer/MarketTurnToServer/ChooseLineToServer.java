package it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer;

import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServerVisitor;

// this event send to the server the line choose by the client
public class ChooseLineToServer extends EventToServer {
    private final int numLine;
    private final String playerName;

    public ChooseLineToServer(int numLine, String playerName) {
        this.numLine = numLine;
        this.playerName = playerName;
    }

    public int getNumLine() {
        return numLine;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
