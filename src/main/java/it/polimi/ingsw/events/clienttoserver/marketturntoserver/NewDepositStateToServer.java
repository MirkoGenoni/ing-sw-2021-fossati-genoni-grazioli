package it.polimi.ingsw.events.clienttoserver.marketturntoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This class represents the event to sent to the server with the information of the new deposit state after that the player menages them.
 *
 * @author Stefano Fossati
 */
public class NewDepositStateToServer extends EventToServer {
    private final ArrayList<Resource> newDepositState;
    private final int discardResources;
    private final String playerName;
    boolean isAdditional;
    ArrayList<Resource> additionalDepositState;

    /**
     * Constructs the event.
     * @param newDepositState The new deposit state choose by the player.
     * @param discardResources The resources from the market that the player has discarded.
     * @param playerName The name of the player that sends the event.
     * @param isAdditional Is true if the player has some active additional deposit, else is false.
     * @param additionalDepositState The new state of the active additional deposit of the player.
     */
    public NewDepositStateToServer(ArrayList<Resource> newDepositState, int discardResources, String playerName, boolean isAdditional, ArrayList<Resource> additionalDepositState) {
        this.newDepositState = newDepositState;
        this.discardResources = discardResources;
        this.playerName = playerName;
        this.isAdditional = isAdditional;
        this.additionalDepositState = additionalDepositState;
    }

    /**
     * Getter of the new deposit state choose by the player.
     * @return The new deposit state choose by the player.
     */
    public ArrayList<Resource> getNewDepositState() {
        return newDepositState;
    }

    /**
     * Getter of the number of resources from the market that the player has discarded.
     * @return The number of resources from the market that the player has discarded.
     */
    public int getDiscardResources() {
        return discardResources;
    }

    /**
     * Getter of the name of the player that sends the event.
     * @return The name of the player that sends the event.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Getter of the boolean that indicates if the player has some active additional deposit.
     * @return The boolean that indicates if the player has some active additional deposit.
     */
    public boolean isAdditional() {
        return isAdditional;
    }

    /**
     * Getter of the new state of the active additional deposit of the player.
     * @return The new state of the active additional deposit of the player.
     */
    public ArrayList<Resource> getAdditionalDepositState() {
        return additionalDepositState;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
