package it.polimi.ingsw.events.clienttoserver.marketturntoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;

import java.util.ArrayList;

// this event send to the server the line choose by the client
public class ChooseLineToServer extends EventToServer {
    private final int numLine;
    private final ArrayList<Boolean> marketWhiteChangeActivation;
    private final String playerName;

    public ChooseLineToServer(int numLine, ArrayList<Boolean> marketWhiteChangeActivation, String playerName) {
        this.numLine = numLine;
        this.marketWhiteChangeActivation = marketWhiteChangeActivation;
        this.playerName = playerName;
    }

    public int getNumLine() {
        return numLine;
    }

    public ArrayList<Boolean> getMarketWhiteChangeActivation() {
        return marketWhiteChangeActivation;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
