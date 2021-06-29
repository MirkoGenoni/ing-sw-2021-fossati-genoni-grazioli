package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.clientToServer.*;
import it.polimi.ingsw.events.clientToServer.buyDevelopmentCardToServer.SelectedDevelopmentCardSpaceToServer;
import it.polimi.ingsw.events.clientToServer.buyDevelopmentCardToServer.SelectedDevelopmentCardToBuyToServer;
import it.polimi.ingsw.events.clientToServer.marketTurnToServer.ChooseLineToServer;
import it.polimi.ingsw.events.clientToServer.marketTurnToServer.NewDepositStateToServer;
import it.polimi.ingsw.events.clientToServer.startGameToServer.DiscardInitialLeaderCards;
import it.polimi.ingsw.events.clientToServer.startGameToServer.InitialResourcesChoose;

public class ControllerConnection implements EventToServerVisitor, ObserveConnectionToClient {
    private final ControllerToModel controllerToModel;

    public ControllerConnection(ControllerToModel controllerToModel) {
        this.controllerToModel = controllerToModel;
    }
    /*
     parsa e costruisce eventiiiii
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
        System.out.println("scarto le leaderCard di " + leaderCards.getPlayerName());
        controllerToModel.discardInitialLeaderCards(leaderCards.getPlayerName(), leaderCards.getLeaderCard1(), leaderCards.getLeaderCard2());
    }

    @Override
    public void visit(SendLeaderCardToServer leaderCardTurn) {
        System.out.println("mi è arrivato il turno gioca-leader");
        controllerToModel.leaderCardTurn(leaderCardTurn.getPlayerName(), leaderCardTurn.getActions(), leaderCardTurn.isFinal());

    }

    // -------------------------------------------
    // EVENTS FOR THE MARKET TURN INTERACTION
    // -------------------------------------------
    @Override
    public void visit(ChooseLineToServer numLine) {
        System.out.println("mi è arrivato il turno chooseline");
        controllerToModel.marketChooseLine(numLine.getPlayerName() ,numLine.getNumLine(), numLine.getMarketWhiteChangeActivation());
    }

    @Override
    public void visit(NewDepositStateToServer newDepositState) {
        System.out.println("ho ricevuto il nuovo stato del deposito");
        controllerToModel.saveNewDepositState(newDepositState.getNewDepositState(), newDepositState.getDiscardResources(), newDepositState.isAdditional(), newDepositState.getAdditionalDepositState());
    }

    // ------------------------------------------------------
    // EVENTS FOR THE BUY DEVELOPMENT CARD TURN INTERACTION
    // ------------------------------------------------------
    @Override
    public void visit(SelectedDevelopmentCardToBuyToServer selectedDevelopmentCard) {
        System.out.println("ho ricevuto la carta da acquistare");
        controllerToModel.buyDevelopmentCard(selectedDevelopmentCard.getColor(), selectedDevelopmentCard.getLevel());
    }

    @Override
    public void visit(SelectedDevelopmentCardSpaceToServer selectedDevelopmentCardSpace) {
        System.out.println("ho ricevuto lo spazio development card");
        controllerToModel.spaceDevelopmentCard(selectedDevelopmentCardSpace.getSpace());
    }

    // ------------------------------------------------------
    // EVENTS FOR THE USE DEVELOPMENT CARD
    // ------------------------------------------------------
    @Override
    public void visit(SelectedProductionToServer sendProductionDevelopmentCard) {
        System.out.println("ho ricevuto il turno di usare produzioni");
        controllerToModel.activateProduction(sendProductionDevelopmentCard.isUseBaseProduction(), sendProductionDevelopmentCard.getResourceRequested1(),
                                                sendProductionDevelopmentCard.getResourceRequested2(), sendProductionDevelopmentCard.getResourceGranted(),
                                                sendProductionDevelopmentCard.getUseLeaders(), sendProductionDevelopmentCard.getMaterialLeaders(),
                                                sendProductionDevelopmentCard.getUseDevelop(), sendProductionDevelopmentCard.getPlayerName());
    }

    // ------------------------------------------------------
    // EVENT FOR NEW TURN INTERACTION
    // ------------------------------------------------------
    @Override
    public void visit(TurnPlayedToServer turn) {
        System.out.println("mi è arrivato il messaggio di turno giocato");
        if(turn.getTurnType().equals("turn") && controllerToModel.checkMultiplayer()){
            controllerToModel.newTurn( true);
        }
    }

    @Override
    public void visit(InitialResourcesChoose newInitialDepositState) {
        controllerToModel.initialResourcesChoose(newInitialDepositState.getInitialResourcesChoose(), newInitialDepositState.getPlayerName());
    }

    @Override
    public void visit(ReplayLorenzoActionToServer replayLorenzoAction) {
        controllerToModel.newTurn(true);
    }

}
