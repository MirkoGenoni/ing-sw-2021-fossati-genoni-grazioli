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

    /**
     * constructor of the class
     * @param numPlayer how many player take part in the match
     */
    public MultiPlayerGame(int numPlayer) {
        super(numPlayer);
        players = new Player[numPlayer];
        // forse shuflle

    }

    /**
     * add the player to the player's array
     * @param player the player to add
     * @throws StartGameException if all the players have already join
     */
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

    /**
     * prepare the game to start
     * @throws StartGameException if there's any problem in initialize the game
     */
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

    /**
     * getter of the array of the players
     * @return the array of the players
     */
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
