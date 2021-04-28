package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Events.ClientToServer.*;
import it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer.ChooseLineToServer;
import it.polimi.ingsw.Events.ClientToServer.MarketTurnToServer.NewDepositStateToServer;
import it.polimi.ingsw.Events.ClientToServer.StartConnectionToServer.NumPlayerToServer;
import it.polimi.ingsw.Events.ClientToServer.StartConnectionToServer.PlayerNameToServer;
import it.polimi.ingsw.Events.ClientToServer.StartGameToServer.DiscardInitialLeaderCards;

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

    @Override
    public void visit(DiscardInitialLeaderCards leaderCards) {
        System.out.println("scarto le leaderCard di " + leaderCards.getPlayerName());
        controllerToModel.discardInitialLeaderCards(leaderCards.getPlayerName(), leaderCards.getLeaderCard1(), leaderCards.getLeaderCard2());
    }

    @Override
    public void visit(NewDepositStateToServer newDepositState) {
        System.out.println("ho ricevuto il nuovo stato del deposito");
        controllerToModel.saveNewDepositState(newDepositState.getNewDepositState(), newDepositState.getDiscardResources());
    }

    @Override
    public void visit(TurnPlayedToServer turn) {
        System.out.println("mi è arrivato il messaggio di turno giocato");
        if(turn.getTurnType().equals("turn")){
            controllerToModel.newTurn();
        }else if(turn.getTurnType().equals("market")){
            controllerToModel.marketTurn();
        }else if(turn.getTurnType().equals("buydevelopment")){
            controllerToModel.developmentCardTurn();
        }
    }

    @Override
    public void visit(ChooseLineToServer numLine) {
        System.out.println("mi è arrivato il turno chooseline");
        controllerToModel.marketChooseLine(numLine.getPlayerName() ,numLine.getNumLine());
    }
}
