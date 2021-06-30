package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.clienttoserver.EventToServer;

/**
 * this interface observe the events that arrive to the server
 * @author Stefano Fossati
 */
public interface ObserveConnectionToClient {
    void observeEvent(EventToServer event);
}
