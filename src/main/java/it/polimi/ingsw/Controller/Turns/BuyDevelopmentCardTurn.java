package it.polimi.ingsw.Controller.Turns;

import it.polimi.ingsw.Events.ServerToClient.BuyDevelopmentCardTurnToClient.SendDevelopmentCardToClient;
import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.Exceptions.DevelopmentCardException;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.Model.Game.MultiPlayerGame;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.SinglePlayerGame;
import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.Resource.Resource;
import it.polimi.ingsw.Server.ConnectionToClient;

import java.util.ArrayList;
import java.util.Map;

public class BuyDevelopmentCardTurn {
    private Player[] players;
    private final ArrayList<ConnectionToClient> connectionsToClient;
    private MultiPlayerGame multiGame;
    private SinglePlayerGame singleGame;
    private Game game;

    // tmp attributes
    private int color;
    private int level;

    public BuyDevelopmentCardTurn(Player[] players, ArrayList<ConnectionToClient> connectionsToClient, MultiPlayerGame multiGame) {
        this.players = players;
        this.connectionsToClient = connectionsToClient;
        this.multiGame = multiGame;
        this.game = multiGame;
    }

    public BuyDevelopmentCardTurn(Player[] players, ArrayList<ConnectionToClient> connectionsToClient, SinglePlayerGame singleGame) {
        this.players = players;
        this.connectionsToClient = connectionsToClient;
        this.singleGame = singleGame;
        this.game = singleGame;
    }

    public void developmentCardTurn(int currentPlayerIndex){
        System.out.println("mando array delle carte disponibili");
        DevelopmentCard[][] devCards = game.getDevelopmentCardsAvailable();
        SendDevelopmentCardToClient[][] availableToSend = new SendDevelopmentCardToClient[4][3];
        DevelopmentCard cardToCopy;

        for(int i=0; i<devCards.length; i++){
            for(int j=0; j<devCards[i].length; j++){
                cardToCopy = devCards[i][j];
                availableToSend[i][j] = new SendDevelopmentCardToClient(cardToCopy.getColor().name(), cardToCopy.getLevel(),
                        cardToCopy.getCost(), cardToCopy.getVictoryPoint(), cardToCopy.getMaterialRequired(), cardToCopy.getProductionResult());
            }
        }
        connectionsToClient.get(currentPlayerIndex).sendDevelopmentCards(availableToSend);

    }

    public void buyDevelopmentCard(int color, int level, int currentPlayerIndex){
        DevelopmentCard buyDevelopmentCard = game.getDevelopmentCardsAvailable()[color][level];
        System.out.println(buyDevelopmentCard.getColor() + buyDevelopmentCard.getCost().toString());
        this.color = color;
        this.level = level; // questo livello è la posizione delle development card nel doppio array del game

        System.out.println("faccio il check delle risorse e degli spazi development card");
        // qui ci sarebbe da rimettere ccomunque il controllo sulle leadercard cost less
        if (players[currentPlayerIndex].getPlayerBoard().getResourceHandler().checkMaterials(buyDevelopmentCard.getCost())
                && players[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().checkBoughtable(level+1).contains(true)) {
            connectionsToClient.get(currentPlayerIndex).sendDevelopmentCardSpace(players[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().checkBoughtable(level+1));
        } else {
            connectionsToClient.get(currentPlayerIndex).sendNotify("You can't bought the selected card, please select an other card");
            developmentCardTurn(currentPlayerIndex);
        }


    }

    public boolean spaceDevelopmentCard(int space, int currentPlayerIndex){
        DevelopmentCard buyDevelopmentCard = game.getDevelopmentCardsAvailable()[color][level];
        Map<Resource, Integer> requirements = buyDevelopmentCard.getCost();
        ArrayList<LeaderCard> leaderCardActive;

        try{
            leaderCardActive = players[currentPlayerIndex].getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive();
        } catch (LeaderCardException e) {
            leaderCardActive = null;
        }

        if(leaderCardActive!=null){
            for (LeaderCard card : leaderCardActive){          //control if there's an active LeaderCard costless
                if (card.getSpecialAbility().getEffect().equals("costLess")) {
                    Resource discount = card.getSpecialAbility().getMaterialType();
                    if(requirements.containsKey(discount)){
                        requirements.put(discount, requirements.get(discount)-1);
                    }
                }
            }
        }

        try{
            players[currentPlayerIndex].getPlayerBoard().getResourceHandler().takeMaterials(requirements);
            players[currentPlayerIndex].getPlayerBoard().getDevelopmentCardHandler().setActiveDevelopmentCard(game.buyDevelopmentCard(color, level), space);
        } catch (ResourceException | StartGameException | DevelopmentCardException e) {
            e.printStackTrace();
        }
        connectionsToClient.get(currentPlayerIndex).sendNotify("Card correctly Activated");
        return true;

    }
}
