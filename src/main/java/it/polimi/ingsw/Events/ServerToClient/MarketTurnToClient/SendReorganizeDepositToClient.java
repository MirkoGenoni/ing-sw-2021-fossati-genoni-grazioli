package it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

/**
 * This class represents the event to notify the client that has to reorganize the deposit.
 *
 * @author Stefano Fossati
 */
public class SendReorganizeDepositToClient extends EventToClient {
    private final ArrayList<Resource> marketResources;
    private final ArrayList<Resource> depositResources;
    boolean additionalDeposit;
    ArrayList<Resource> type;
    ArrayList<Resource> additionalDepositState;

    /**
     * Constructs the event.
     * @param marketResources The resources taken by the player from the market.
     * @param depositResources The state of the player deposit.
     * @param additionalDeposit If the player has additional deposit leader card active.
     * @param type The type of resources in the additional deposits of the player.
     * @param additionalDepositState The state of the additional deposit of the player.
     */
    public SendReorganizeDepositToClient(ArrayList<Resource> marketResources, ArrayList<Resource> depositResources,
                                         boolean additionalDeposit, ArrayList<Resource> type, ArrayList<Resource> additionalDepositState) {
        this.marketResources = marketResources;
        this.depositResources = depositResources;
        this.additionalDeposit = additionalDeposit;
        this.type = type;
        this.additionalDepositState = additionalDepositState;
    }

    /**
     * Getter of the resources taken by the player from the market.
     * @return The resources taken by the player from the market.
     */
    public ArrayList<Resource> getMarketResources() {
        return marketResources;
    }

    /**
     * Getter of the state of the player deposit.
     * @return The state of the player deposit.
     */
    public ArrayList<Resource> getDepositResources() {
        return depositResources;
    }

    /**
     * Getter of the boolean that indicates if the player has additional deposit leader card active.
     * @return The boolean that indicates if the player has additional deposit leader card active.
     */
    public boolean isAdditionalDeposit() {
        return additionalDeposit;
    }

    /**
     * Getter of the type of resources in the additional deposits of the player.
     * @return The type of resources in the additional deposits of the player.
     */
    public ArrayList<Resource> getType() {
        return new ArrayList<>(type);
    }

    /**
     * Getter of the state of the additional deposit of the player.
     * @return The state of the additional deposit of the player.
     */
    public ArrayList<Resource> getAdditionalDepositState() {
        return new ArrayList<>(additionalDepositState);
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
