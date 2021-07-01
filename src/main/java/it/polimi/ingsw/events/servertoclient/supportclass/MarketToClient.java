package it.polimi.ingsw.events.servertoclient.supportclass;

import it.polimi.ingsw.model.market.Marble;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the structure of the market to send to the client.
 * This object could be only read from the client.
 * @see it.polimi.ingsw.model.market.Market
 * @see Serializable
 *
 * @author Stefano Fossati
 */
public class MarketToClient implements Serializable {
    private final ArrayList<Marble> grid;
    private final Marble outMarble;

    /**
     * Constructs the market structure.
     * @param grid The grid of marble of the the market.
     * @param outMarble The marble out of the market.
     */
    public MarketToClient(ArrayList<Marble> grid, Marble outMarble) {
        this.grid = grid;
        this.outMarble = outMarble;
    }

    /**
     * Getter that returns the grid of marble of the the market.
     * @return The grid of marble of the the market.
     */
    public ArrayList<Marble> getGrid() {
        return grid;
    }

    /**
     * Getter that returns the marble out of the market.
     * @return The marble out of the market.
     */
    public Marble getOutMarble() {
        return outMarble;
    }

}
