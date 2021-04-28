package it.polimi.ingsw.Events.ClientToServer.StartConnectionToServer;

import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServerVisitor;

public class PlayerNameToServer extends EventToServer {
    private final String newPlayerName;
    private final String oldPlayerName;

    public PlayerNameToServer(String newPlayerName, String oldPlayerName) {
        this.newPlayerName = newPlayerName;
        this.oldPlayerName = oldPlayerName;
    }

    public String getNewPlayerName() {
        return newPlayerName;
    }

    public String getOldPlayerName() {
        return oldPlayerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
