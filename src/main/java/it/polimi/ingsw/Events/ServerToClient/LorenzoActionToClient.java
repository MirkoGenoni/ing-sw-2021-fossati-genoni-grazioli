package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Model.Lorenzo.SoloAction;

public class LorenzoActionToClient extends EventToClient{
    private final SoloAction lorenzoAction;
    private final int lorenzoPosition;

    public LorenzoActionToClient(SoloAction lorenzoAction, int lorenzoPosition) {
        this.lorenzoAction = lorenzoAction;
        this.lorenzoPosition = lorenzoPosition;
    }

    public SoloAction getLorenzoAction() {
        return lorenzoAction;
    }

    public int getLorenzoPosition() {
        return lorenzoPosition;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
