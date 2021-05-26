package it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer;

import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServerVisitor;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;
import java.util.Map;

public class NewDepositStateToServer extends EventToServer {
    private final ArrayList<Resource> newDepositState;
    private final int discardResources;
    private final String playerName;
    boolean isAdditional;
    ArrayList<Resource> additionalDepositState;

    public NewDepositStateToServer(ArrayList<Resource> newDepositState, int discardResources, String playerName, boolean isAdditional, ArrayList<Resource> additionalDepositState) {
        this.newDepositState = newDepositState;
        this.discardResources = discardResources;
        this.playerName = playerName;
        this.isAdditional = isAdditional;
        this.additionalDepositState = additionalDepositState;
    }

    public ArrayList<Resource> getNewDepositState() {
        return newDepositState;
    }

    public int getDiscardResources() {
        return discardResources;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isAdditional() {
        return isAdditional;
    }

    public ArrayList<Resource> getAdditionalDepositState() {
        return additionalDepositState;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
