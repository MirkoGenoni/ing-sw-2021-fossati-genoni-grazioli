package it.polimi.ingsw.events.clienttoserver.startgametoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;

/**
 * This class represents the event to send to the server with the initial leader card thath the player choose to discard.
 *
 * @author Stefano Fossati
 */
public class DiscardInitialLeaderCards extends EventToServer {
    private final int leaderCard1;
    private final int leaderCard2;
    private final String playerName;

    /**
     * Constructs the event.
     * @param leaderCard1 The first initial leader card that the player decides to discard.
     * @param leaderCard2 The second initial leader card that the player decides to discard.
     * @param playerName The name of the player that sends the event.
     */
    public DiscardInitialLeaderCards(int leaderCard1, int leaderCard2, String playerName) {
        this.leaderCard1 = leaderCard1;
        this.leaderCard2 = leaderCard2;
        this.playerName = playerName;
    }

    /**
     * Getter of the first initial leader card that the player decides to discard.
     * @return The first initial leader card that the player decides to discard.
     */
    public int getLeaderCard1() {
        return leaderCard1;
    }

    /**
     * Getter of the second initial leader card that the player decides to discard.
     * @return The second initial leader card that the player decides to discard.
     */
    public int getLeaderCard2() {
        return leaderCard2;
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
