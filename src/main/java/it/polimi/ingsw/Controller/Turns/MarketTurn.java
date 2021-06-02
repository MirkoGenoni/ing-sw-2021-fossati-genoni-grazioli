package it.polimi.ingsw.Controller.Turns;

import it.polimi.ingsw.Controller.ControllerToModel;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Market.Marble;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

public class MarketTurn {
    private final ControllerToModel controllerToModel;

    // tmp attributes
    private ArrayList<Resource> tmpMarketReturn;

    public MarketTurn(ControllerToModel controllerToModel) {
        this.controllerToModel = controllerToModel;
    }

    public void marketChooseLine(String namePlayer, int line, int currentPlayerIndex, ArrayList<Boolean> leaderMarketWhiteChange){
        System.out.println("aggiungo al player");
        ArrayList<Marble> tmpM =  controllerToModel.getGame().getMarketBoard().chooseLine(line);
        System.out.println(tmpM);

        if(tmpM.contains(Marble.FAITH)){    // se ci sono marble di tipo faith incrementa direttamente la pedina del giocatore sul tracciato fede
            if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(currentPlayerIndex)){
                controllerToModel.controlPlayerPath(currentPlayerIndex);
            }
            tmpM.remove(Marble.FAITH);
        }

        if(tmpM.contains(Marble.NOTHING)){
            try{
                ArrayList<LeaderCard> tmpL = controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive();
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
                System.out.println(controllerToModel.getPlayers()[currentPlayerIndex].getName() + e.getMessage());
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
        sendMarketDepositData(currentPlayerIndex);
    }

    public boolean saveNewDepositState(ArrayList<Resource> newDepositState, int discardResources, int currentPlayerIndex, boolean isAdditional, ArrayList<Resource> additionalDepositState){
        System.out.println(" riscorse scartate " +discardResources);
        boolean tmp = false;
        int savePlayer = -1;
        // deve fare il check del nuovo stato del deposito se non va bene rimanda l'evento di riorganizzare il deposito
        try{
            if(isAdditional)
                controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getResourceHandler().newAdditionalDepositState(additionalDepositState);

            controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getResourceHandler().newDepositState(newDepositState);

            for(int i=0; i<discardResources; i++){
                for( int j=0; j<controllerToModel.getPlayers().length; j++){
                    if(j!=currentPlayerIndex){
                        if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(j)){
                            tmp = true;
                            savePlayer = j;
                        }
                    }
                }
                if(tmp && savePlayer != -1){
                    controllerToModel.controlPlayerPath(savePlayer);
                }
                if(controllerToModel.getPlayers().length==1){
                    if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(1)){
                        controllerToModel.controlPlayerPath(1);
                    }

                }

            }
            return true;
        } catch (ResourceException e) {
            controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify(e.getMessage());
            sendMarketDepositData(currentPlayerIndex);
            return false;
        }

    }

    public void sendMarketDepositData(int currentPlayerIndex){

        boolean isAdditional = false;
        ArrayList<Resource> additionalType = new ArrayList<>();
        ArrayList<Resource> additionalState = controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getResourceHandler().getAdditionalDeposit();

        try {   //controlla se il giocatore ha attivato una leadercard biggerDeposit e crea l'evento di conseguenza
            for(LeaderCard r: controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive()){
                if(r!=null){
                    if(r.getSpecialAbility().getEffect().equals("biggerDeposit")){
                        isAdditional = true;
                        additionalType.add(r.getSpecialAbility().getMaterialType());
                    }
                }
            }

        } catch (LeaderCardException e) {

        }
        controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendReorganizeDeposit(new ArrayList<>(this.tmpMarketReturn), controllerToModel.getPlayers()[currentPlayerIndex].getPlayerBoard().getResourceHandler().getDepositState(), isAdditional, additionalType, additionalState);
    }

}
