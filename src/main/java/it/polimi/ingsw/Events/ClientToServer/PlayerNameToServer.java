package it.polimi.ingsw.Events.ClientToServer;

public class PlayerNameToServer extends EventToServer{
    private final String newPlayerName;
    private final String oldPlayerName;

    public PlayerNameToServer(String newPlayerName, String oldPlayerName) {
        this.newPlayerName = newPlayerName;
        this.oldPlayerName = oldPlayerName;
    }

    public String getNewPlayerName() {
        return newPlayerName;
    }

    public String getOldPlayerName() {
        return oldPlayerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
