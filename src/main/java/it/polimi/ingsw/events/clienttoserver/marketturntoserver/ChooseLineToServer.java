package it.polimi.ingsw.events.clienttoserver.marketturntoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;

import java.util.ArrayList;

/**
 * This class represents the event to send to the server with the line of the market that tha player choose to take.
 *
 * @author Stefano Fossati
 */
public class ChooseLineToServer extends EventToServer {
    private final int numLine;
    private final ArrayList<Boolean> marketWhiteChangeActivation;
    private final String playerName;

    /**
     * Constructs the event.
     * @param numLine The number line of the market choose by the player.
     * @param marketWhiteChangeActivation  True indicates if the player has active the leader cards market white change, else false.
     * @param playerName The name of the player that sends the event.
     */
    public ChooseLineToServer(int numLine, ArrayList<Boolean> marketWhiteChangeActivation, String playerName) {
        this.numLine = numLine;
        this.marketWhiteChangeActivation = marketWhiteChangeActivation;
        this.playerName = playerName;
    }

    /**
     * Getter of the number line of the market choose by the player.
     * @return The number line of the market choose by the player.
     */
    public int getNumLine() {
        return numLine;
    }

    /**
     * Getter of the ArrayList that indicates if the player has active one or more leader cards market white change.
     * @return The the ArrayList that indicates if the player has active one or more leader cards market white change.
     */
    public ArrayList<Boolean> getMarketWhiteChangeActivation() {
        return marketWhiteChangeActivation;
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
