package it.polimi.ingsw.Events.ServerToClient;

import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendDevelopmentCardAvailableToClient;
import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendDevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.MarketTurnToClient;
import it.polimi.ingsw.Events.ServerToClient.MarketTurnToClient.SendReorganizeDepositToClient;
import it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient.SendNumPlayerToClient;
import it.polimi.ingsw.Events.ServerToClient.StartConnectionToClient.SendPlayerNameToClient;

// list of the event that the client receive from the server
public interface EventToClientVisitor {

    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE CLIENT
    // -------------------------------------------------------
    void visit(SendPlayerNameToClient playerName);
    void visit(SendNumPlayerToClient numPlayer);

    // -------------------------------------------
    // EVENTS THAT RECEIVE LEADER CARD INFORMATION
    // -------------------------------------------
    void visit(SendLeaderCardToClient leaderCard);
    void visit(SendArrayLeaderCardsToClient leaderCardArray);

    // ----------------------------------
    // EVENTS FOR THE MARKET TURN
    // ----------------------------------
    void visit(MarketTurnToClient market);
    void visit(SendReorganizeDepositToClient newResources);

    // ----------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN
    // ----------------------------------------
    void visit(SendDevelopmentCardToClient developmentCard);
    void visit(SendDevelopmentCardAvailableToClient availableDevelopmentCards);

    // ----------------------------------
    // OTHER EVENTS
    // ----------------------------------
    void visit(NotifyToClient message);
    void visit(NewTurnToClient notify);


}
