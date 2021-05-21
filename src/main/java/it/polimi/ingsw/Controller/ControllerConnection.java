package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Events.ClientToServer.*;
import it.polimi.ingsw.Events.ClientToServer.BuyDevelopmentCardToServer.SelectedDevelopmentCardSpaceToServer;
import it.polimi.ingsw.Events.ClientToServer.BuyDevelopmentCardToServer.SelectedDevelopmentCardToBuyToServer;
import it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer.ChooseLineToServer;
import it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer.NewDepositStateToServer;
import it.polimi.ingsw.Events.ClientToServer.StartConnectionToServer.NumPlayerToServer;
import it.polimi.ingsw.Events.ClientToServer.StartConnectionToServer.PlayerNameToServer;
import it.polimi.ingsw.Events.ClientToServer.StartGameToServer.DiscardInitialLeaderCards;
import it.polimi.ingsw.Events.ClientToServer.StartGameToServer.InitialResourcesChoose;

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

    // Events that arrive from the ConnectionToClient // Questi sono per il multiplayer...
    // -------------------------------------------------------
    // EVENTS FOR THE START OF THE CONNECTION WITH THE SERVER
    // -------------------------------------------------------
    @Override
    public void visit(NumPlayerToServer numPlayer) {
        System.out.println("ho ricevuto il numero di giocatori " + numPlayer.getNumPlayer());
        controllerToModel.setNumPlayer(numPlayer.getNumPlayer());
    }

    @Override
    public void visit(PlayerNameToServer newPlayerName) {
        System.out.println(" mi è arrivato il nome del giocatore " + newPlayerName.getOldPlayerName());
        System.out.println(" il nuovo è : " + newPlayerName.getNewPlayerName());
        controllerToModel.SetPlayerName(newPlayerName.getNewPlayerName(), newPlayerName.getOldPlayerName());
    }

    // -------------------------------------------
    // EVENTS FOR THE LEADER CARD INTERACTION
    // -------------------------------------------
    @Override
    public void visit(DiscardInitialLeaderCards leaderCards) {
        System.out.println("scarto le leaderCard di " + leaderCards.getPlayerName());
        controllerToModel.discardInitialLeaderCards(leaderCards.getPlayerName(), leaderCards.getLeaderCard1(), leaderCards.getLeaderCard2());
    }

    @Override
    public void visit(SendLeaderCardTurnToServer leaderCardTurn) {
        System.out.println("mi è arrivato il turno gioca-leader");
        controllerToModel.leaderCardTurn(leaderCardTurn.getPlayerName(), leaderCardTurn.getActions());

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
        controllerToModel.saveNewDepositState(newDepositState.getNewDepositState(), newDepositState.getDiscardResources());
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
    public void visit(SelectedProductionDevelopmentCardToServer sendProductionDevelopmentCard) {
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
            controllerToModel.newTurn();
        }
    }

    @Override
    public void visit(InitialResourcesChoose newInitialDepositState) {
        System.out.println("mi è arrivat il deposito iniziale");
        controllerToModel.initialResourcesChoose(newInitialDepositState.getInitialResourcesChoose(), newInitialDepositState.getPlayerName());
    }

    @Override
    public void visit(ReplayLorenzoActionToServer replayLorenzoAction) {
        controllerToModel.newTurn();
    }

}
