package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.clienttoserver.EventToServer;

public interface ObserveConnectionToClient {
    void observeEvent(EventToServer event);
}
