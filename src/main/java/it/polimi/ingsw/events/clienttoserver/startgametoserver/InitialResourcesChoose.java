package it.polimi.ingsw.events.clienttoserver.startgametoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This class represent the event to send to the server with the state of the deposit with the initial resources choose by the player.
 *
 * @author Stefano Fossati
 */
public class InitialResourcesChoose extends EventToServer {
    private final ArrayList<Resource> initialResourcesChoose;
    private final String playerName;

    /**
     * Constructs the event.
     * @param initialResourcesChoose The new deposit state with the initial resource choose and reorganize by the player.
     * @param playerName The name of the player that sends the event.
     */
    public InitialResourcesChoose(ArrayList<Resource> initialResourcesChoose, String playerName) {
        this.initialResourcesChoose = initialResourcesChoose;
        this.playerName = playerName;
    }

    /**
     * Getter of the new deposit state with the initial resource choose and reorganize by the player.
     * @return The new deposit state with the initial resource choose and reorganize by the player.
     */
    public ArrayList<Resource> getInitialResourcesChoose() {
        return initialResourcesChoose;
    }

    /**
     * Getter of the name of the player that sends the event.
     * @return The name of the player that sends the event.
     */
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
