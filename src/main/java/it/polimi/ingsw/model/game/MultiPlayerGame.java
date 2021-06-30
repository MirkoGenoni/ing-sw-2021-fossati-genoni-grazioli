package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.StartGameException;
import it.polimi.ingsw.model.leadercard.LeaderCard;

import java.util.ArrayList;

/**
 * This class represents the multiplayer.
 *
 * @author Stefano Fossati
 */
public class MultiPlayerGame extends Game{
    private Player players[];


    public MultiPlayerGame(int numPlayer) {
        super(numPlayer);
        players = new Player[numPlayer];
        // forse shuflle

    }

    public void addPlayer(Player player) throws StartGameException {
        int i=0;
        while(i!= players.length && players[i]!=null){
            i++;
        }
        if(i<players.length){
            players[i] = player;
        }else{
            throw new StartGameException("players all initialized");
        }

    }

    public void startGame() throws StartGameException {
        super.startGame();

        // this part set the four initial leader cards for each player
        for(int i=0; i<players.length; i++){
            ArrayList<LeaderCard> tmp = new ArrayList<>();
            for(int j=0; j<4; j++){
                tmp.add(allLeaderCards.remove(0));
            }
            players[i].createGameBoard(tmp);
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    @Override
    public ArrayList[][] getDevelopmentCards() {
        return super.developmentCards;
    }

    @Override
    public DevelopmentCard[][] getDevelopmentCardsAvailable() {
        return super.getDevelopmentCardsAvailable();
    }

    @Override
    public DevelopmentCard buyDevelopmentCard(int color, int level) throws StartGameException {
        return super.buyDevelopmentCard(color, level);
    }
}
