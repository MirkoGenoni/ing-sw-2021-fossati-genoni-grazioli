package it.polimi.ingsw.Controller.Turns;

import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.DevelopmentCard.CardColor;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Game.SinglePlayerGame;
import it.polimi.ingsw.Model.Lorenzo.LorenzoIlMagnifico;
import it.polimi.ingsw.Model.Lorenzo.SoloAction;


public class LorenzoTurn {
    private final ControllerToModel controllerToModel;
    private final LorenzoIlMagnifico lorenzoIlMagnifico;
    private final SinglePlayerGame singlePlayerGame;
    private final int lorenzoNumPlayer;
    private boolean lorenzoWin;

    public LorenzoTurn(ControllerToModel controllerToModel, SinglePlayerGame singlePlayerGame, int lorenzoNumPlayer) {
        this.controllerToModel = controllerToModel;
        this.singlePlayerGame = singlePlayerGame;
        lorenzoIlMagnifico = singlePlayerGame.getLorenzoIlMagnifico();
        this.lorenzoNumPlayer = lorenzoNumPlayer; //TODO CONTROLLO NUMERO LORENZO =1?
        lorenzoWin = false;
    }


    /**
     * Metod who permit Lorenzo to Play his turn
     * @return if Lorenzo has win
     */
    public boolean playLorenzo(){

        SoloAction action;
        action = lorenzoIlMagnifico.getToken();

        System.out.println("ho pescato lorenzo " + action.toString());
        controllerToModel.getConnectionsToClient().get(controllerToModel.getCurrentPlayerIndex()).sendLorenzoTurn(action);

        switch (action) {
            case MOVESHUFFLE:
                if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(lorenzoNumPlayer))
                    controllerToModel.controlPlayerPath(lorenzoNumPlayer);
                controlFaithTrackLorenzoWin();
                break;
            case ONLYMOVE:
                for(int i=0; i<2;i++){
                    if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(lorenzoNumPlayer))
                        controllerToModel.controlPlayerPath(lorenzoNumPlayer);
                }
                controlFaithTrackLorenzoWin();
                break;
            case DISCARDBLUE:
                discardCardColorForLorenzo(CardColor.BLUE);
                break;
            case DISCARDGREEN:
                discardCardColorForLorenzo(CardColor.GREEN);
                break;
            case DISCARDPURPLE:
                discardCardColorForLorenzo(CardColor.PURPLE);
                break;
            case DISCARDYELLOW:
                discardCardColorForLorenzo(CardColor.YELLOW);
                break;

        }

        return lorenzoWin;
    }


    /**
     * method discard two development cards of a specified color setting true a parameter if Lorenzo wins
     * @param color specifies the color of the card to discard
     */
    private void discardCardColorForLorenzo(CardColor color){

        int colorForCard;

        switch (color) {
            case GREEN:
                colorForCard = 0;
                break;
            case BLUE:
                colorForCard = 1;
                break;
            case YELLOW:
                colorForCard = 2;
                break;
            case PURPLE:
                colorForCard = 3;
                break;
            default:
                throw new RuntimeException(); //attention Exception
        }

        try {

            for (int i = 0; i < 2; i++) {
                if (controllerToModel.getGame().getDevelopmentCardsAvailable()[colorForCard][0] != null) {
                    controllerToModel.getGame().buyDevelopmentCard(colorForCard, 0);
                } else if (controllerToModel.getGame().getDevelopmentCardsAvailable()[colorForCard][1] != null) {
                    controllerToModel.getGame().buyDevelopmentCard(colorForCard, 1);
                } else if (controllerToModel.getGame().getDevelopmentCardsAvailable()[colorForCard][2] != null) {
                    controllerToModel.getGame().buyDevelopmentCard(colorForCard, 2);
                    if(controllerToModel.getGame().getDevelopmentCardsAvailable()[colorForCard][2] == null)
                        lorenzoWin = true; //se scarta tutte hai perso
                } else {
                    lorenzoWin = true;
                }
            }

        }
        catch (StartGameException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }

    /**
     * control if lorenzo has reach the last position in faithtrack
     */
    private void controlFaithTrackLorenzoWin(){
        if (controllerToModel.getGame().getPlayersFaithTrack().getPosition(lorenzoNumPlayer)==24){
            lorenzoWin = true;
        }
    }

}
