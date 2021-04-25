package it.polimi.ingsw.Events.ClientToServer;

import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

public class NewDepositStateToServer extends EventToServer{
    private final ArrayList<Resource> newDepositState;
    private final int discardResources;
    private final String playerName;

    public NewDepositStateToServer(ArrayList<Resource> newDepositState, int discardResources, String playerName) {
        this.newDepositState = newDepositState;
        this.discardResources = discardResources;
        this.playerName = playerName;
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

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
