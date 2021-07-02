package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.clienttoserver.EventToServer;

/**
 * this interface observe the events that arrive to the server
 * @author Stefano Fossati
 */
public interface ObserveConnectionToClient {
    /**
     * Observes the events that arrive to the connection to client.
     * @param event The event that arrive to the connection to client.
     */
    void observeEvent(EventToServer event);
}
