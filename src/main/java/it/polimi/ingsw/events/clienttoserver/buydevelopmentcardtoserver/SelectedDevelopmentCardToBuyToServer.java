package it.polimi.ingsw.events.clienttoserver.buydevelopmentcardtoserver;

import it.polimi.ingsw.events.clienttoserver.EventToServer;
import it.polimi.ingsw.events.clienttoserver.EventToServerVisitor;

public class SelectedDevelopmentCardToBuyToServer extends EventToServer {
    private final int color;
    private final int level;
    private final String playerName;

    public SelectedDevelopmentCardToBuyToServer(int color, int level, String playerName) {
        this.color = color;
        this.level = level;
        this.playerName = playerName;
    }

    public int getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
