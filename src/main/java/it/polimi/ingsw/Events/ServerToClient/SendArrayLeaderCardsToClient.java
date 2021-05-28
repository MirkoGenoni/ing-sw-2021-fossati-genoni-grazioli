package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;

import java.util.ArrayList;
import java.util.Map;

public class SendArrayLeaderCardsToClient extends EventToClient{
    private final ArrayList<LeaderCardToClient> leaderCardArray;
    private final Map<String, Integer> numberOfDevelopmentCards;
    private final boolean initialLeaderCards;


    public SendArrayLeaderCardsToClient(ArrayList<LeaderCardToClient> leaderCardArray, Map<String, Integer> numberOfDevelopmentCards, boolean initialLeaderCards) {
        this.leaderCardArray = leaderCardArray;
        this.numberOfDevelopmentCards = numberOfDevelopmentCards;
        this.initialLeaderCards = initialLeaderCards;
    }

    public boolean isInitialLeaderCards() {
        return initialLeaderCards;
    }

    public ArrayList<LeaderCardToClient> getLeaderCardArray() {
        return leaderCardArray;
    }

    public Map<String, Integer> getNumberOfDevelopmentCards() {
        return numberOfDevelopmentCards;
    }

    @Override
    public void acceptVisitor(EventToClientVisitor visitor) {
        visitor.visit(this);
    }
}
