package it.polimi.ingsw.events.clienttoserver.startgametoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

public class InitialResourcesChoose extends EventToServer {
    private final ArrayList<Resource> initialResourcesChoose;
    private final String playerName;

    public InitialResourcesChoose(ArrayList<Resource> initialResourcesChoose, String playerName) {
        this.initialResourcesChoose = initialResourcesChoose;
        this.playerName = playerName;
    }

    public ArrayList<Resource> getInitialResourcesChoose() {
        return initialResourcesChoose;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
