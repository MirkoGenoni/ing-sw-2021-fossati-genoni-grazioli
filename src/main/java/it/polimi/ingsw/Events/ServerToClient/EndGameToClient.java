package it.polimi.ingsw.Events.ServerToClient;

import java.util.Map;

public class EndGameToClient extends EventToClient {
    private final String message;
    private final Map<String, Integer> playersPoint;

    public EndGameToClient(String message, Map<String, Integer> playersPoint) {
        this.message = message;
        this.playersPoint = playersPoint;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Integer> getPlayersPoint() {
        return playersPoint;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
