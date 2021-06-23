package it.polimi.ingsw.Server.RoomHandler;

import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendNumPlayerToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendPlayerNameToServer;
import it.polimi.ingsw.Events.ClientToServer.InitialConnectionToServer.SendRoomToServer;

/**
 * This interface contains the list of the event of the initial connection of the client to the server.
 *
 * @author Stefano Fossati
 */
public interface EventToServerInitialVisitor {
    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------

    /**
     * Visit method of the room response event from the client.
     * @param room The response of the room request from the client.
     * @see SendRoomToServer
     */
    void visit(SendRoomToServer room);

    /**
     * Visit method of the player name response from the client.
     * @param playerName The response of the player name request from the client.
     * @see SendPlayerNameToServer
     */
    void visit(SendPlayerNameToServer playerName);

    /**
     * Visit method of the number player response from the client.
     * @param numPlayer The response of the number player request from the client.
     * @see SendNumPlayerToServer
     */
    void visit(SendNumPlayerToServer numPlayer);
}
