package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;

import java.util.ArrayList;

public class SendArrayLeaderCardsToClient extends EventToClient{
    private final ArrayList<LeaderCardToClient> leaderCardArray;
    private final boolean initialLeaderCards;

    public SendArrayLeaderCardsToClient(ArrayList<LeaderCardToClient> leaderCardArray, boolean initialLeaderCards) {
        this.leaderCardArray = leaderCardArray;
        this.initialLeaderCards = initialLeaderCards;
    }

    public boolean isInitialLeaderCards() {
        return initialLeaderCards;
    }

    public ArrayList<LeaderCardToClient> getLeaderCardArray() {
        return leaderCardArray;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
