package it.polimi.ingsw.events.servertoclient;

import java.util.ArrayList;

/**
 * This class represents the event to send to the client to choose the space in which put the development card bought by the player.
 *
 * @author Stefano Fossati
 */
public class SendSpaceDevelopmentCardToClient extends EventToClient {
    private final ArrayList<Boolean> developmentCardSpace;

    /**
     * Construct the event.
     * @param developmentCardSpace The development card available of the player.
     */
    public SendSpaceDevelopmentCardToClient(ArrayList<Boolean> developmentCardSpace) {
        this.developmentCardSpace = developmentCardSpace;
    }

    /**
     * Getter of the development card available of the player.
     * @return The development card available of the player.
     */
    public ArrayList<Boolean> getDevelopmentCardSpace() {
        return developmentCardSpace;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
