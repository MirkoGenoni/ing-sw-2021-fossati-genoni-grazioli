package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.clienttoserver.*;
import it.polimi.ingsw.events.clienttoserver.buydevelopmentcardtoserver.SelectedDevelopmentCardSpaceToServer;
import it.polimi.ingsw.events.clienttoserver.buydevelopmentcardtoserver.SelectedDevelopmentCardToBuyToServer;
import it.polimi.ingsw.events.clienttoserver.marketturntoserver.ChooseLineToServer;
import it.polimi.ingsw.events.clienttoserver.marketturntoserver.NewDepositStateToServer;
import it.polimi.ingsw.events.clienttoserver.startgametoserver.DiscardInitialLeaderCards;
import it.polimi.ingsw.events.clienttoserver.startgametoserver.InitialResourcesChoose;

/**
 * This class implements the EventToServerVisitor and ObserveConnectionToClient interface. This class parse the type of event that
 * arrive from the connection with the client to do specific actions.
 * @see ObserveConnectionToClient
 * @see EventToServerVisitor
 * @author Stefano Fossati
 */
public class ControllerConnection implements EventToServerVisitor, ObserveConnectionToClient {
    private final Controller controller;

    /**
     * constructor of the class
     * @param controller controller to model that manages the game for players
     */
    public ControllerConnection(Controller controller) {
        this.controller = controller;
    }
    /*
     parse and constructs events
     */


    // This method observe events that arrive from the ConnectionToClient
    @Override
    public void observeEvent(EventToServer event) {
        event.acceptServerVisitor(this);
    }

    // Events that arrive from the ConnectionToClient
    // -------------------------------------------
    // EVENTS FOR THE LEADER CARD INTERACTION
    // -------------------------------------------
    @Override
    public void visit(DiscardInitialLeaderCards leaderCards) {
        //System.out.println("discard the leader card of " + leaderCards.getPlayerName());
        controller.discardInitialLeaderCards(leaderCards.getPlayerName(), leaderCards.getLeaderCard1(), leaderCards.getLeaderCard2());
    }

    @Override
    public void visit(SendLeaderCardToServer leaderCardTurn) {
        //System.out.println("arrives played leader turn");
        controller.leaderCardTurn(leaderCardTurn.getPlayerName(), leaderCardTurn.getActions(), leaderCardTurn.isFinal());

    }

    // -------------------------------------------
    // EVENTS FOR THE MARKET TURN INTERACTION
    // -------------------------------------------
    @Override
    public void visit(ChooseLineToServer numLine) {
        //System.out.println("arrived market turn choose line");
        controller.marketChooseLine(numLine.getPlayerName() ,numLine.getNumLine(), numLine.getMarketWhiteChangeActivation());
    }

    @Override
    public void visit(NewDepositStateToServer newDepositState) {
        //System.out.println("received the new deposit");
        controller.saveNewDepositState(newDepositState.getNewDepositState(), newDepositState.getDiscardResources(), newDepositState.isAdditional(), newDepositState.getAdditionalDepositState());
    }

    // ------------------------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN INTERACTION
    // ------------------------------------------------------
    @Override
    public void visit(SelectedDevelopmentCardToBuyToServer selectedDevelopmentCard) {
        //System.out.println("received the development card selected");
        controller.buyDevelopmentCard(selectedDevelopmentCard.getColor(), selectedDevelopmentCard.getLevel());
    }

    @Override
    public void visit(SelectedDevelopmentCardSpaceToServer selectedDevelopmentCardSpace) {
        //System.out.println("arrived selected space development");
        controller.spaceDevelopmentCard(selectedDevelopmentCardSpace.getSpace());
    }

    // ------------------------------------------------------
    // EVENTS FOR THE USE DEVELOPMENT CARD
    // ------------------------------------------------------
    @Override
    public void visit(SelectedProductionToServer sendProductionDevelopmentCard) {
        //System.out.println("arrived activate production turn");
        controller.activateProduction(sendProductionDevelopmentCard.isUseBaseProduction(), sendProductionDevelopmentCard.getResourceRequested1(),
                                                sendProductionDevelopmentCard.getResourceRequested2(), sendProductionDevelopmentCard.getResourceGranted(),
                                                sendProductionDevelopmentCard.getUseLeaders(), sendProductionDevelopmentCard.getMaterialLeaders(),
                                                sendProductionDevelopmentCard.getUseDevelop(), sendProductionDevelopmentCard.getPlayerName());
    }

    // ------------------------------------------------------
    // EVENT FOR NEW TURN INTERACTION
    // ------------------------------------------------------
    @Override
    public void visit(TurnPlayedToServer turn) {
        //System.out.println("turn skipped for testing");
        if(turn.getTurnType().equals("turn") && controller.checkMultiplayer()){
            controller.newTurn( true);
        }
    }

    @Override
    public void visit(InitialResourcesChoose newInitialDepositState) {
        controller.initialResourcesChoose(newInitialDepositState.getInitialResourcesChoose(), newInitialDepositState.getPlayerName());
    }

    @Override
    public void visit(ReplayLorenzoActionToServer replayLorenzoAction) {
        controller.newTurn(true);
    }

}
