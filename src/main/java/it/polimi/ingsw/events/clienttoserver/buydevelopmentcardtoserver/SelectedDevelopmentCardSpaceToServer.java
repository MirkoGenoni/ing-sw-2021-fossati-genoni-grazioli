package it.polimi.ingsw.events.clienttoserver.buydevelopmentcardtoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;

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
