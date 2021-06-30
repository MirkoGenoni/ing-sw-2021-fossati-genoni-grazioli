package it.polimi.ingsw.events.clienttoserver;

import java.util.ArrayList;

public class SendLeaderCardToServer extends EventToServer{
    private final ArrayList<Integer> actions;
    private final String playerName;
    private final boolean isFinal;

    public SendLeaderCardToServer(ArrayList<Integer> actions, String playerName, boolean isFinal) {
        this.actions = actions;
        this.playerName = playerName;
        this.isFinal = isFinal;
    }

    public ArrayList<Integer> getActions() {
        return actions;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
