package it.polimi.ingsw.controller.turns;

import it.polimi.ingsw.controller.ControllerToModel;
import it.polimi.ingsw.model.developmentcard.CardColor;
import it.polimi.ingsw.model.exceptions.StartGameException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.SinglePlayerGame;
import it.polimi.ingsw.model.lorenzo.LorenzoIlMagnifico;
import it.polimi.ingsw.model.lorenzo.SoloAction;

/**
 * Class Lorenzo turn to manage the single player game against lorenzo
 * @author davide grazioli
 */
public class LorenzoTurn {
    private final ControllerToModel controllerToModel;
    private final LorenzoIlMagnifico lorenzoIlMagnifico;
    private final SinglePlayerGame singlePlayerGame;
    private final int lorenzoNumPlayer;
    private boolean lorenzoWin;

    /**
     * constructor of the class
     * @param controllerToModel controller to model that manages the game for the single player
     * @param singlePlayerGame the game playing from the single player and lorenzo
     */
    public LorenzoTurn(ControllerToModel controllerToModel, SinglePlayerGame singlePlayerGame) {
        this.controllerToModel = controllerToModel;
        this.singlePlayerGame = singlePlayerGame;
        lorenzoIlMagnifico = singlePlayerGame.getLorenzoIlMagnifico();
        this.lorenzoNumPlayer = 1;
        lorenzoWin = false;
    }


    /**
     * Metod who permit Lorenzo to perform his turn
     * @return true if Lorenzo has win
     */
    public boolean playLorenzo(){

        SoloAction action;
        action = lorenzoIlMagnifico.getToken();

        System.out.println("ho pescato lorenzo " + action.toString());

        switch (action) {
            case MOVESHUFFLE:
                if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(lorenzoNumPlayer))
                    controllerToModel.controlPlayerPath(lorenzoNumPlayer, controllerToModel.getGame().getPlayersFaithTrack().getSection(lorenzoNumPlayer));
                controlFaithTrackLorenzoWin();
                break;
            case ONLYMOVE:
                for(int i=0; i<2;i++){
                    if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(lorenzoNumPlayer))
                        controllerToModel.controlPlayerPath(lorenzoNumPlayer, controllerToModel.getGame().getPlayersFaithTrack().getSection(lorenzoNumPlayer));
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
        //controllerToModel.getConnectionsToClient().get(controllerToModel.getCurrentPlayerIndex()).sendLorenzoTurn(action, controllerToModel.getGame().getPlayersFaithTrack().getPosition(1));
        controllerToModel.getConnections().get(controllerToModel.getActivePlayer().getName()).sendLorenzoTurn(action, controllerToModel.getGame().getPlayersFaithTrack().getPosition(1));
        return lorenzoWin;
    }


    /**
     * method to discard two development cards of a specified color, setting true a parameter if Lorenzo wins
     * @param color specifies the color of the card to discard
     */
    private void discardCardColorForLorenzo(CardColor color){
        Game game = controllerToModel.getGame();
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
                if (game.getDevelopmentCardsAvailable()[colorForCard][0] != null) {
                    game.buyDevelopmentCard(colorForCard, 0);
                } else if (game.getDevelopmentCardsAvailable()[colorForCard][1] != null) {
                    game.buyDevelopmentCard(colorForCard, 1);
                } else if (game.getDevelopmentCardsAvailable()[colorForCard][2] != null) {
                    game.buyDevelopmentCard(colorForCard, 2);
                    if(game.getDevelopmentCardsAvailable()[colorForCard][2] == null)
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
     * control if lorenzo has reached the last position in faith track
     */
    private void controlFaithTrackLorenzoWin(){
        if (controllerToModel.getGame().getPlayersFaithTrack().getPosition(lorenzoNumPlayer)==24){
            lorenzoWin = true;
        }
    }

}
