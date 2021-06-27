package it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer;

import it.polimi.ingsw.Server.RoomHandler.EventToServerInitialVisitor;

/**
 * This class represents the event to send to the server for the initial connection. In particular this event is the name player answer send from the client to the server.
 *
 * @author Stefano Fossati
 */
public class SendPlayerNameToServer extends EventToServerInitial{
    private final String playerName;

    /**
     * Constructs the event.
     * @param playerName The name choose by the player to send to the server.
     */
    public SendPlayerNameToServer(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Getter that returns the name choose by the player.
     * @return The name choose by the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptInitialServerVisitor(EventToServerInitialVisitor visitor) {
        visitor.visit(this);
    }
}
