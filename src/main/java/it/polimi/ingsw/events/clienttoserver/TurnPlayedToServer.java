package it.polimi.ingsw.events.clienttoserver;

/**
 * This class represent the event send to the server for the choose of the type of turn.
 * With the development of the project this event is use only for debug: if the player type 'turn' on the CLI the player skip the turn.
 *
 * @author Stefano Fossati
 */
public class TurnPlayedToServer extends EventToServer{
    private final String turnType;
    private final String playerName;

    /**
     * Construct the event.
     * @param turnType The string that represents the type of turn.
     * @param playerName The name of the player that sends the event.
     */
    public TurnPlayedToServer(String turnType, String playerName) {
        this.turnType = turnType;
        this.playerName = playerName;
    }

    /**
     * Getter of the string that represents the type of turn.
     * @return The string that represents the type of turn.
     */
    public String getTurnType() {
        return turnType;
    }

    /**
     * Getter of the name of the player that sends the event.
     * @return The name of the player that sends the event.
     */
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
