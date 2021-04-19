package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Events.ClientToServer.EventToServer;

public interface ObserveConnectionToClient {
    void observeEvent(EventToServer event);
}
