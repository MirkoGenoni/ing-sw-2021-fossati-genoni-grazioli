package it.polimi.ingsw.Events.ServerToClient;

import java.util.ArrayList;

public class SendArrayLeaderCardsToClient extends EventToClient{
    private final ArrayList<SendLeaderCardToClient> leaderCardArray;
    private final boolean initialLeaderCards;

    public SendArrayLeaderCardsToClient(ArrayList<SendLeaderCardToClient> leaderCardArray, boolean initialLeaderCards) {
        this.leaderCardArray = leaderCardArray;
        this.initialLeaderCards = initialLeaderCards;
    }

    public boolean isInitialLeaderCards() {
        return initialLeaderCards;
    }

    public ArrayList<SendLeaderCardToClient> getLeaderCardArray() {
        return leaderCardArray;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
