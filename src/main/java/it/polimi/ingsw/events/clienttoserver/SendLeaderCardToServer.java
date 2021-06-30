package it.polimi.ingsw.events.clienttoserver;

import java.util.ArrayList;

/**
 * This class represents the event to send to the server with the information of the leader card action choose by the player.
 *
 * @author Stefano Fossati.
 */
public class SendLeaderCardToServer extends EventToServer{
    private final ArrayList<Integer> actions;
    private final String playerName;
    private final boolean isFinal;

    /**
     * Constructs the event.
     * @param actions The actions of the leader cards that the player choose.
     * @param playerName The name of the player that sends the event.
     * @param isFinal Is true if the event is sent at the final stage of the turn, is false if the event is sent at the initial stage of the turn.
     */
    public SendLeaderCardToServer(ArrayList<Integer> actions, String playerName, boolean isFinal) {
        this.actions = actions;
        this.playerName = playerName;
        this.isFinal = isFinal;
    }

    /**
     * Getter of the actions of the leader cards that the player choose.
     * @return The actions of the leader cards that the player choose.
     */
    public ArrayList<Integer> getActions() {
        return actions;
    }

    /**
     * Getter of the name of the player that sends the event.
     * @return The name of the player that sends the event.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Getter of the boolean that indicates if the event is sent from the client at the final stage or the initial stage of the turn.
     * @return The boolean that indicates if the event is sent from the client at the final stage or the initial stage of the turn.
     */
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
