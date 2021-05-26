package it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

public class SendReorganizeDepositToClient extends EventToClient {
    private final ArrayList<Resource> marketResources;
    private final ArrayList<Resource> depositResources;
    boolean additionalDeposit;
    ArrayList<Resource> type;
    ArrayList<Resource> additionalDepositState;

    public SendReorganizeDepositToClient(ArrayList<Resource> marketResources, ArrayList<Resource> depositResources,
                                         boolean additionalDeposit, ArrayList<Resource> type, ArrayList<Resource> additionalDepositState) {
        this.marketResources = marketResources;
        this.depositResources = depositResources;
        this.additionalDeposit = additionalDeposit;
        this.type = type;
        this.additionalDepositState = additionalDepositState;
    }

    public ArrayList<Resource> getMarketResources() {
        return marketResources;
    }

    public ArrayList<Resource> getDepositResources() {
        return depositResources;
    }

    public boolean isAdditionalDeposit() {
        return additionalDeposit;
    }

    public ArrayList<Resource> getType() {
        return new ArrayList<>(type);
    }

    public ArrayList<Resource> getAdditionalDepositState() {
        return new ArrayList<>(additionalDepositState);
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
