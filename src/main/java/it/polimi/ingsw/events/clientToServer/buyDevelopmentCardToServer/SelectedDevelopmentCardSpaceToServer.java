package it.polimi.ingsw.events.clientToServer.buyDevelopmentCardToServer;

import it.polimi.ingsw.events.clientToServer.EventToServer;
import it.polimi.ingsw.events.clientToServer.EventToServerVisitor;

public class SelectedDevelopmentCardSpaceToServer extends EventToServer {
    private final int space;
    private final String namePlayer;

    public SelectedDevelopmentCardSpaceToServer( int space, String namePlayer) {
        this.space = space;
        this.namePlayer = namePlayer;
    }

    public int getSpace() {
        return space;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
