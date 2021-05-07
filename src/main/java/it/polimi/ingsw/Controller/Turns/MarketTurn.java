package it.polimi.ingsw.Controller.Turns;

import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.Model.Game.MultiPlayerGame;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.SinglePlayerGame;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Market.Marble;
import it.polimi.ingsw.Model.Resource.Resource;
import it.polimi.ingsw.Server.ConnectionToClient;

import java.util.ArrayList;

public class MarketTurn {
    private Player[] players;
    private final ArrayList<ConnectionToClient> connectionsToClient;
    private MultiPlayerGame multiGame;
    private SinglePlayerGame singleGame;
    private Game game;

    // tmp attributes
    private ArrayList<Resource> tmpMarketReturn;

    public MarketTurn(Player[] players, ArrayList<ConnectionToClient> connectionsToClient, MultiPlayerGame multiGame) {
        this.players = players;
        this.connectionsToClient = connectionsToClient;
        this.multiGame = multiGame;
        this.game = multiGame;
    }

    public MarketTurn(Player[] players, ArrayList<ConnectionToClient> connectionsToClient, SinglePlayerGame singleGame){
        this.players = players;
        this.connectionsToClient = connectionsToClient;
        this.singleGame = singleGame;
        this.game = singleGame;
    }

    public void marketChooseLine(String namePlayer, int line, int currentPlayerIndex){
        System.out.println("aggiungo al player");
        ArrayList<Marble> tmpM =  game.getMarketBoard().chooseLine(line);
        System.out.println(tmpM);

        if(tmpM.contains(Marble.FAITH)){    // se ci sono marble di tipo faith incrementa direttamente la pedina del giocatore sul tracciato fede
            if(game.getPlayersFaithTrack().forwardPos(currentPlayerIndex)){
                controlPlayerPath(currentPlayerIndex);
            }
            tmpM.remove(Marble.FAITH);
        }

        if(tmpM.contains(Marble.NOTHING)){   // TODO le leader card da usare sarebbero da scegliere....
            try{
                ArrayList<LeaderCard> tmpL = players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive();
                if(!tmpL.isEmpty()){                                        // controlla se ci sono carte leader attive
                    for(int i=0; i< tmpL.size() && tmpM.contains(Marble.NOTHING); i++){
                        if(tmpL.get(i).getSpecialAbility().getEffect().equals("marketWhiteChange") && tmpM.contains(Marble.NOTHING)){  // se ci sono carte leader attive di tipo marketWhiteChenge converte la marble
                            tmpM.remove(Marble.NOTHING);
                            tmpM.add(Marble.valueOf(tmpL.get(i).getSpecialAbility().getMaterialType().toString()));
                        }
                    }
                }
            } catch (LeaderCardException e) {
                System.out.println(players[currentPlayerIndex].getName() + e.getMessage());
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
        connectionsToClient.get(currentPlayerIndex).sendReorganizeDeposit(tmpR, players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getDepositState());
    }

    public boolean saveNewDepositState(ArrayList<Resource> newDepositState, int discardResources, int currentPlayerIndex){
        System.out.println(" riscorse scartate " +discardResources);
        boolean tmp = false;
        int savePlayer = -1;
        // deve fare il check del nuovo stato del deposito se non va bene rimanda l'evento di riorganizzare il deposito
        try{
            players[currentPlayerIndex].getPlayerBoard().getResourceHandler().newDepositState(newDepositState);
            for(int i=0; i<discardResources; i++){
                for( int j=0; j<players.length; j++){
                    if(j!=currentPlayerIndex){
                        if(game.getPlayersFaithTrack().forwardPos(j)){
                            tmp = true;
                            savePlayer = j;
                        }
                    }
                }
                if(tmp && savePlayer != -1){
                    controlPlayerPath(savePlayer);
                }

            }
            return true;
        } catch (ResourceException e) {
            connectionsToClient.get(currentPlayerIndex).sendNotify(e.getMessage());
            connectionsToClient.get(currentPlayerIndex).sendReorganizeDeposit(tmpMarketReturn, players[currentPlayerIndex].getPlayerBoard().getResourceHandler().getDepositState());
            return false;
        }

    }

    /**
     *
     * @param numPlayer player who call the PopeSpace
     *
     */
    public void controlPlayerPath (int numPlayer){

        int section = game.getPlayersFaithTrack().getSection(numPlayer);
        int sectionToCheck;

        for(int i=0; i<players.length; i++){
            if(i!=numPlayer){ //it is an other Player
                sectionToCheck = game.getPlayersFaithTrack().getSection(i); //control players' path
                if (sectionToCheck!=section){ //devo rimuovergli il tagliando
                   players[i].getPlayerBoard().removePopeFavorTiles(section);
                }

            }
        }

    }
}
