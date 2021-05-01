package it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient;

import it.polimi.ingsw.Events.ServerToClient.EventToClient;
import it.polimi.ingsw.Events.ServerToClient.EventToClientVisitor;

import java.util.ArrayList;

public class SendSpaceDevelopmentCardToClient extends EventToClient {
    private final ArrayList<Boolean> developmentCardSpace;

    public SendSpaceDevelopmentCardToClient(ArrayList<Boolean> developmentCardSpace) {
        this.developmentCardSpace = developmentCardSpace;
    }

    public ArrayList<Boolean> getDevelopmentCardSpace() {
        return developmentCardSpace;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
