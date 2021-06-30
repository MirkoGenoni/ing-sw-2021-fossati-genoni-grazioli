package it.polimi.ingsw.events.clienttoserver.buydevelopmentcardtoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;

/**
 * This class represents the event to send to the server with the development card that the player choose to buy.
 *
 * @author Stefano Fossati.
 */
public class SelectedDevelopmentCardToBuyToServer extends EventToServer {
    private final int color;
    private final int level;
    private final String playerName;

    /**
     * Constructs the event.
     * @param color The color of the development card choose by the player.
     * @param level The level og the development card choose by the player.
     * @param playerName The name of the player that sends the event.
     */
    public SelectedDevelopmentCardToBuyToServer(int color, int level, String playerName) {
        this.color = color;
        this.level = level;
        this.playerName = playerName;
    }

    /**
     * Getter of the color of the development card choose by the player.
     * @return The color of the development card choose by the player.
     */
    public int getColor() {
        return color;
    }

    /**
     * Getter of the level og the development card choose by the player.
     * @return The level og the development card choose by the player.
     */
    public int getLevel() {
        return level;
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
