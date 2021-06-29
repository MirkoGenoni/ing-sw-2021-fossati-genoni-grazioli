package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.clientToServer.EventToServer;

public interface ObserveConnectionToClient {
    void observeEvent(EventToServer event);
}
