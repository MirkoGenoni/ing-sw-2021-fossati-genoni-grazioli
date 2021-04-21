package it.polimi.ingsw.Events.ClientToServer;

// this event send to the server the line choose by the client
public class ChooseLineToServer extends EventToServer{
    private final int numLine;
    private final String playerName;

    public ChooseLineToServer(int numLine, String playerName) {
        this.numLine = numLine;
        this.playerName = playerName;
    }

    public int getNumLine() {
        return numLine;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
