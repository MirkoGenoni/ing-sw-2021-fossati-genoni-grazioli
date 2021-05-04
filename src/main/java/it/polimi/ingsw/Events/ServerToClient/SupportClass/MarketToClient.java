package it.polimi.ingsw.Events.ServerToClient.SupportClass;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;
import it.polimi.ingsw.Model.Market.Marble;

import java.io.Serializable;
import java.util.ArrayList;

// this event send the structure of the market to the client. the client could visualize it
public class MarketToClient implements Serializable {
    private final ArrayList<Marble> grid;
    private final Marble outMarble;

    public MarketToClient(ArrayList<Marble> grid, Marble outMarble) {
        this.grid = grid;
        this.outMarble = outMarble;
    }

    public ArrayList<Marble> getGrid() {
        return grid;
    }

    public Marble getOutMarble() {
        return outMarble;
    }

}
