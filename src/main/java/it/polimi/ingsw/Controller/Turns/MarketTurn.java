package it.polimi.ingsw.Controller.Turns;

import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Market.Marble;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

/**
 * class to manage the turn to take resources from market
 */
public class MarketTurn {
    private final ControllerToModel controllerToModel;

    // tmp attributes
    private ArrayList<Resource> tmpMarketReturn;

    /**
     * constructor of the class
     * @param controllerToModel controller to model that manages the game for players
     */
    public MarketTurn(ControllerToModel controllerToModel) {
        this.controllerToModel = controllerToModel;
    }

    /**
     *
     * @param namePlayer name of the player who is taking resources from the market
     * @param line line choose from the player in the market
     * @param leaderMarketWhiteChange Arraylist of boolean that specify if a player is using a market-white-change leader's
     *                                power and in which position, the boolean is in the same position of the leader card
     *                                the player want to use
     */
    public void marketChooseLine(String namePlayer, int line, ArrayList<Boolean> leaderMarketWhiteChange){
        System.out.println("aggiungo al player");
        int currentPlayerIndex = controllerToModel.getCurrentPlayerIndex();
        Player activePlayer = controllerToModel.getPlayers()[currentPlayerIndex];
        ArrayList<Marble> tmpM =  controllerToModel.getGame().getMarketBoard().chooseLine(line);
        System.out.println(tmpM);
        int section = controllerToModel.getGame().getPlayersFaithTrack().getSection(controllerToModel.getCurrentPlayerIndex());

        if(tmpM.contains(Marble.FAITH)){    // se ci sono marble di tipo faith incrementa direttamente la pedina del giocatore sul tracciato fede
            if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(currentPlayerIndex)){
                controllerToModel.controlPlayerPath(currentPlayerIndex,section);
            }
            tmpM.remove(Marble.FAITH);
        }

        if(tmpM.contains(Marble.NOTHING)){
            try{
                ArrayList<LeaderCard> tmpL = activePlayer.getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive();
                //TODO Probabile isEmpty non necessario
                //nel caso in cui entrambi i valori sui leader da usare fossero true, converte solo il primo valore
                if(!tmpL.isEmpty()){                                        // controlla se ci sono carte leader attive
                    for(int i=0; i< tmpL.size() ; i++){
                        if(leaderMarketWhiteChange.get(i) && tmpL.get(i).getSpecialAbility().getEffect().equals("marketWhiteChange")){ // se ci sono carte leader attive di tipo marketWhiteChenge converte le marble
                            while (tmpM.contains(Marble.NOTHING)) {
                                tmpM.remove(Marble.NOTHING);
                                tmpM.add(Marble.valueOf(tmpL.get(i).getSpecialAbility().getMaterialType().toString()));
                            }
                        }
                    }
                //TODO forse altro controllo se c'è white-change e ho due false
                }
            } catch (LeaderCardException e) {
                System.out.println(activePlayer.getName() + e.getMessage());
            }
        }
        System.out.println(tmpM);
        while(tmpM.contains(Marble.NOTHING)){           // toglie le marble nothing in eccesso
            tmpM.remove(Marble.NOTHING);
        }

        ArrayList<Resource> tmpR = new ArrayList<>();  // converte da marble a resource
        for( Marble m : tmpM){
            tmpR.add(Resource.valueOf(m.toString()));
        }

        this.tmpMarketReturn = new ArrayList<>(tmpR);
        sendMarketDepositData(activePlayer);
    }

    /**
     * method to check if the state of the reorganized deposit from a player is valid and manage
     * the discarding of resource from the player
     * @param newDepositState Arraylist that specify the state of the reorganized deposit
     * @param discardResources the number of the discarded resources
     * @param isAdditional boolean that specify if there's an aditional deposit
     * @param additionalDepositState The Arraylist that specify the state of the additional deposit
     * @return if a player has reorganised correctly its deposit
     */
    public boolean saveNewDepositState(ArrayList<Resource> newDepositState, int discardResources, boolean isAdditional, ArrayList<Resource> additionalDepositState){
        Player activePlayer = controllerToModel.getActivePlayer();
        System.out.println(" riscorse scartate " +discardResources);
        boolean tmp = false;
        int savePlayer = -1;
        int section=0;
        // deve fare il check del nuovo stato del deposito se non va bene rimanda l'evento di riorganizzare il deposito
        try{
            if(isAdditional)
                activePlayer.getPlayerBoard().getResourceHandler().newAdditionalDepositState(additionalDepositState);

            activePlayer.getPlayerBoard().getResourceHandler().newDepositState(newDepositState);

            for(int i=0; i<discardResources; i++){
                for( int j=0; j<controllerToModel.getPlayers().length; j++){
                    if(!controllerToModel.getPlayers()[j].equals(activePlayer)){
                        if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(j)){
                            tmp = true;
                            savePlayer = j;
                            section = controllerToModel.getGame().getPlayersFaithTrack().getSection(j);
                        }
                    }
                }
                if(tmp && savePlayer != -1 && section!=0){
                    controllerToModel.controlPlayerPath(savePlayer,section);
                }
                if(controllerToModel.getPlayers().length==1){
                    if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(1)){ //avanza lorenzo
                        section = controllerToModel.getGame().getPlayersFaithTrack().getSection(1); //Lorenzo's section
                        controllerToModel.controlPlayerPath(1,section); //controllo rispetto a lorenzo
                    }

                }

            }
            return true;
        } catch (ResourceException e) {
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify(e.getMessage());
            controllerToModel.getConnections().get(activePlayer.getName()).sendNotify(e.getMessage());
            sendMarketDepositData(activePlayer);
            return false;
        }

    }

    /**
     * method to send the state of the deposit of a specified player
     * @param activePlayer the player who's playing the market turn
     */
    private void sendMarketDepositData(Player activePlayer){

        boolean isAdditional = false;
        ArrayList<Resource> additionalType = new ArrayList<>();
        ArrayList<Resource> additionalState = activePlayer.getPlayerBoard().getResourceHandler().getAdditionalDeposit();

        try {   //controlla se il giocatore ha attivato una leadercard biggerDeposit e crea l'evento di conseguenza
            for(LeaderCard r: activePlayer.getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive()){
                if(r!=null){
                    if(r.getSpecialAbility().getEffect().equals("biggerDeposit")){
                        isAdditional = true;
                        additionalType.add(r.getSpecialAbility().getMaterialType());
                    }
                }
            }

        } catch (LeaderCardException e) {

        }
        //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendReorganizeDeposit(new ArrayList<>(this.tmpMarketReturn), controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getResourceHandler().getDepositState(), isAdditional, additionalType, additionalState);
        controllerToModel.getConnections().get(activePlayer.getName()).sendReorganizeDeposit(new ArrayList<>(this.tmpMarketReturn),
                             activePlayer.getPlayerBoard().getResourceHandler().getDepositState(), isAdditional, additionalType, additionalState);
    }

}
