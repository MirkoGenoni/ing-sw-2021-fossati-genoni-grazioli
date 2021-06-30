package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.StartGameException;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.lorenzo.LorenzoIlMagnifico;

import java.util.ArrayList;

/**
 * Class for the singlePlayer game
 * @author davide grazioli, Stefano Fossati
 */
public class SinglePlayerGame extends Game{
    Player player;
    LorenzoIlMagnifico lorenzoIlMagnifico;

    /**
     * constructor of the class
     * @param player the player in the single player game
     */
    public SinglePlayerGame(Player player) {
        super(2); //provide 2 faithTracks
        this.lorenzoIlMagnifico = new LorenzoIlMagnifico();
        this.player = player;
    }

    /**
     * getter of the player
     * @return the player in the single player game
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * getter of lorenzo il magnifico
     * @return lorenzo il magnifico for the single player game
     */
    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    @Override
    public ArrayList[][] getDevelopmentCards() {
        return super.getDevelopmentCards();
    }

    @Override
    public DevelopmentCard[][] getDevelopmentCardsAvailable() {
        return super.getDevelopmentCardsAvailable();
    }

    @Override
    public DevelopmentCard buyDevelopmentCard(int color, int level) throws StartGameException {
        return super.buyDevelopmentCard(color, level);
    }

    @Override
    public void startGame() throws StartGameException {
        super.startGame();

        ArrayList<LeaderCard> tmp = new ArrayList<>();
        for(int j=0; j<4; j++){
            tmp.add(allLeaderCards.remove(0));
        }
        player.createGameBoard(tmp);

    }
}
