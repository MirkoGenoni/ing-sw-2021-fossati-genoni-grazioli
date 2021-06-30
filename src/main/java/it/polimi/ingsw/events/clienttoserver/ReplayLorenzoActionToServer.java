package it.polimi.ingsw.events.clienttoserver;

/**
 * This class represents the event to send to the server as reply of the LorenzoActionToClient.
 *
 * @author Stefano Fossati.
 */
public class ReplayLorenzoActionToServer extends EventToServer{
    private final String playerName;

    /**
     * Constructs the event.
     * @param playerName The name of the client that sends the event.
     */
    public ReplayLorenzoActionToServer(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Getter of the name of the client that sends the event.
     * @return The name of the client that sends the event.
     */
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
