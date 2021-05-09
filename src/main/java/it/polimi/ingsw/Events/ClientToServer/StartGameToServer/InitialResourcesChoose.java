package it.polimi.ingsw.Events.ClientToServer.StartGameToServer;

import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServerVisitor;
import it.polimi.ingsw.Model.Resource.Resource;

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
