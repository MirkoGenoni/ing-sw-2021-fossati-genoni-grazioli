package it.polimi.ingsw.Events.ServerToClient;

import java.util.ArrayList;

public class SendArrayLeaderCardsToClient extends EventToClient{
    private final ArrayList<SendLeaderCardToClient> leaderCardArray;

    public SendArrayLeaderCardsToClient(ArrayList<SendLeaderCardToClient> leaderCardArray) {
        this.leaderCardArray = leaderCardArray;
    }

    public ArrayList<SendLeaderCardToClient> getLeaderCardArray() {
        return leaderCardArray;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
