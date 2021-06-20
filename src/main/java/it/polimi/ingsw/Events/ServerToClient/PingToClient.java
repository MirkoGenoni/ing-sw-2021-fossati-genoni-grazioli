package it.polimi.ingsw.Events.ServerToClient;

public class PingToClient extends EventToClient{
    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
