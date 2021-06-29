package it.polimi.ingsw.events.clientToServer;

public class ReplayLorenzoActionToServer extends EventToServer{
    private final String playerName;

    public ReplayLorenzoActionToServer(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
