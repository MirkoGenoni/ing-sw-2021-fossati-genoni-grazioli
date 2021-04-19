package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Events.ClientToServer.ChooseLineToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServer;
import it.polimi.ingsw.Events.ClientToServer.EventToServerVisitor;
import it.polimi.ingsw.Events.ClientToServer.TurnPlayedToServer;

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

    @Override
    public void visit(TurnPlayedToServer turn) {
        System.out.println("mi è arrivato il messaggio di turno giocato");
        if(turn.getTurnType().equals("turn")){
            controllerToModel.newTurn();
        }else if(turn.getTurnType().equals("market")){
            controllerToModel.marketTurn();
        }
    }

    @Override
    public void visit(ChooseLineToServer numLine) {
        System.out.println("mi è arrivato il turno chooseline");
        controllerToModel.marketChooseLine(numLine.getNumLine());
    }
}
