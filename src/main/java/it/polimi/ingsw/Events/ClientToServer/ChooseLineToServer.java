package it.polimi.ingsw.Events.ClientToServer;

// this event send to the server the line choose by the client
public class ChooseLineToServer extends EventToServer{
    private final int numLine;

    public ChooseLineToServer(int numLine) {
        this.numLine = numLine;
    }

    public int getNumLine() {
        return numLine;
    }


    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
