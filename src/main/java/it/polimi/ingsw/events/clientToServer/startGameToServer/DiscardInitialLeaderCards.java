package it.polimi.ingsw.events.clientToServer.startGameToServer;

import it.polimi.ingsw.events.clientToServer.EventToServer;
import it.polimi.ingsw.events.clientToServer.EventToServerVisitor;

public class DiscardInitialLeaderCards extends EventToServer {
    private final int leaderCard1;
    private final int leaderCard2;
    private final String playerName;

    public DiscardInitialLeaderCards(int leaderCard1, int leaderCard2, String playerName) {
        this.leaderCard1 = leaderCard1;
        this.leaderCard2 = leaderCard2;
        this.playerName = playerName;
    }

    public int getLeaderCard1() {
        return leaderCard1;
    }

    public int getLeaderCard2() {
        return leaderCard2;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
