package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.model.lorenzo.SoloAction;

/**
 * This class represents the event to send to the client for the notification of a Lorenzo action.
 *
 * @author Stefano Fossati
 */
public class LorenzoActionToClient extends EventToClient{
    private final SoloAction lorenzoAction;
    private final int lorenzoPosition;

    /**
     * Constructs the event.
     * @param lorenzoAction The action done by Lorenzo.
     * @param lorenzoPosition The Lorenzo position in the faith track.
     */
    public LorenzoActionToClient(SoloAction lorenzoAction, int lorenzoPosition) {
        this.lorenzoAction = lorenzoAction;
        this.lorenzoPosition = lorenzoPosition;
    }

    /**
     * Getter of the Lorenzo action.
     * @return The Lorenzo Action.
     */
    public SoloAction getLorenzoAction() {
        return lorenzoAction;
    }

    /**
     * Getter of Lorenzo position in the faith track.
     * @return The Lorenzo position in the faith track.
     */
    public int getLorenzoPosition() {
        return lorenzoPosition;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
