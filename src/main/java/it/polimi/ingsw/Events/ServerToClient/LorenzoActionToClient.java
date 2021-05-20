package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Model.Lorenzo.SoloAction;

public class LorenzoActionToClient extends EventToClient{
    private final SoloAction lorenzoAction;

    public LorenzoActionToClient(SoloAction lorenzoAction) {
        this.lorenzoAction = lorenzoAction;
    }

    public SoloAction getLorenzoAction() {
        return lorenzoAction;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
