package it.polimi.ingsw.Events.ServerToClient;

import java.io.IOException;

// this event ask at the client what type of turn want to do
public class NewTurnToClient extends EventToClient{
    private final int turnNumber;

    public NewTurnToClient(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor){
        visitor.visit(this);
    }
}
