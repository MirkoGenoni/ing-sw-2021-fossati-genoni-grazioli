package it.polimi.ingsw.events.clienttoserver.buydevelopmentcardtoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;

/**
 * This class represents the event to send to the server with the space in which the player wants to put the development card bought.
 *
 * @author Stefano Fossati
 */
public class SelectedDevelopmentCardSpaceToServer extends EventToServer {
    private final int space;
    private final String namePlayer;

    /**
     * Constructs the event.
     * @param space The space in which the player decides to put the development bought.
     * @param namePlayer The name of the player that sends the event.
     */
    public SelectedDevelopmentCardSpaceToServer( int space, String namePlayer) {
        this.space = space;
        this.namePlayer = namePlayer;
    }

    /**
     * Getter of the space in which the player decides to put the development bought.
     * @return The space in which the player decides to put the development bought.
     */
    public int getSpace() {
        return space;
    }

    /**
     * Getter of the name of the player that sends the event.
     * @return The name of the player that sends the event.
     */
    public String getNamePlayer() {
        return namePlayer;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
