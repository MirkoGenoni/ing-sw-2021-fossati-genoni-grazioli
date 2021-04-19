package it.polimi.ingsw.Events.ServerToClient;

// this event send the name to the client
public class SendPlayerNameToClient extends EventToClient{
    private final String playerName;

    public SendPlayerNameToClient(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
