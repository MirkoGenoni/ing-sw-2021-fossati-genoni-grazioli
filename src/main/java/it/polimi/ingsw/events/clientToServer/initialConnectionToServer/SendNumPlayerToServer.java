package it.polimi.ingsw.events.clientToServer.initialConnectionToServer;

import it.polimi.ingsw.server.roomHandler.EventToServerInitialVisitor;

/**
 * This class represents the event to send to the server for the initial connection. In particular this event is the number of players answer send from the client to the server.
 *
 * @author Stefano Fossati
 */
public class SendNumPlayerToServer extends EventToServerInitial{
    private final int numPlayer;
    private final String PlayerName;

    /**
     * Constructs the event.
     * @param numPlayer The number of player choose by the player that has received the num player request event.
     * @param playerName The name of the player that send the event to the server.
     */
    public SendNumPlayerToServer(int numPlayer, String playerName) {
        this.numPlayer = numPlayer;
        PlayerName = playerName;
    }

    /**
     * Getter that returns the number of player choose by the player that has received the num player request event.
     * @return The number of player choose by the player that has received the num player request event.
     */
    public int getNumPlayer() {
        return numPlayer;
    }

    /**
     * Getter that returns the name of the player that send the event to the server.
     * @return The name of the player that send the event to the server.
     */
    public String getPlayerName() {
        return PlayerName;
    }

    @Override
    public void acceptInitialServerVisitor(EventToServerInitialVisitor visitor) {
        visitor.visit(this);
    }
}
