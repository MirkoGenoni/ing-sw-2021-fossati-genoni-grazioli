package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This class represents the event to send to the client for the choice of the initial resources in the set up of the game.
 *
 * @author Stefano Fossati
 */
public class SendInitialResourcesToClient extends EventToClient{
    private final int numResources;
    private final ArrayList<Resource> depositState;

    /**
     * Constructs the event.
     * @param numResources The number of resources that the player has to choose.
     * @param depositState The initial deposit state that is empty.
     */
    public SendInitialResourcesToClient(int numResources, ArrayList<Resource> depositState) {
        this.numResources = numResources;
        this.depositState = depositState;
    }

    /**
     * Getter of number of resources that the player has to choose.
     * @return The number of resources that the player has to choose.
     */
    public int getNumResources() {
        return numResources;
    }

    /**
     * Getter of the deposit state of the player.
     * @return The deposit state of the player.
     */
    public ArrayList<Resource> getDepositState() {
        return depositState;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
