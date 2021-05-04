package it.polimi.ingsw.Events.ClientToServer;

import java.util.ArrayList;

public class SendLeaderCardTurnToServer extends EventToServer{
    private final ArrayList<Integer> actions;
    private final String playerName;

    public SendLeaderCardTurnToServer(ArrayList<Integer> actions, String playerName) {
        this.actions = actions;
        this.playerName = playerName;
    }

    public ArrayList<Integer> getActions() {
        return actions;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
