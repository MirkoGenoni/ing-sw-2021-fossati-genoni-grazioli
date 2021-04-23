package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

public class SendReorganizeDepositToClient extends EventToClient{
    private final ArrayList<Resource> marketResources;
    private final ArrayList<Resource> depositResources;

    public SendReorganizeDepositToClient(ArrayList<Resource> marketResources, ArrayList<Resource> depositResources) {
        this.marketResources = marketResources;
        this.depositResources = depositResources;
    }

    public ArrayList<Resource> getMarketResources() {
        return marketResources;
    }

    public ArrayList<Resource> getDepositResources() {
        return depositResources;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
