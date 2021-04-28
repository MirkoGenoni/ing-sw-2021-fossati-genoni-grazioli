package it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;
import it.polimi.ingsw.Model.Market.Marble;

import java.util.ArrayList;

// this event send the structure of the market to the client. the client could visualize it
public class MarketTurnToClient extends EventToClient {
    private final ArrayList<Marble> grid;
    private final Marble outMarble;

    public MarketTurnToClient(ArrayList<Marble> grid, Marble outMarble) {
        this.grid = grid;
        this.outMarble = outMarble;
    }

    public ArrayList<Marble> getGrid() {
        return grid;
    }

    public Marble getOutMarble() {
        return outMarble;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
