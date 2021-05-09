package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

public class SendInitialResourcesToClient extends EventToClient{
    private final int numResources;
    private final ArrayList<Resource> depositState;

    public SendInitialResourcesToClient(int numResources, ArrayList<Resource> depositState) {
        this.numResources = numResources;
        this.depositState = depositState;
    }

    public int getNumResources() {
        return numResources;
    }

    public ArrayList<Resource> getDepositState() {
        return depositState;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
