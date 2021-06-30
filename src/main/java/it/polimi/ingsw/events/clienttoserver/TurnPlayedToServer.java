package it.polimi.ingsw.events.clienttoserver;

// this event send to the server the type of turn choose by the client
public class TurnPlayedToServer extends EventToServer{
    private final String turnType;
    private final String playerName;

    public TurnPlayedToServer(String turnType, String playerName) {
        this.turnType = turnType;
        this.playerName = playerName;
    }

    public String getTurnType() {
        return turnType;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
