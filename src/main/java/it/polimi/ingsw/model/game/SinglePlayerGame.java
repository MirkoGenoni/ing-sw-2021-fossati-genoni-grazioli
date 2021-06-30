package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.StartGameException;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.lorenzo.LorenzoIlMagnifico;

import java.util.ArrayList;

public class SinglePlayerGame extends Game{
    Player player;
    LorenzoIlMagnifico lorenzoIlMagnifico;

    public SinglePlayerGame(Player player) {
        super(2); //provide 2 faithTracks
        this.lorenzoIlMagnifico = new LorenzoIlMagnifico();
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

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
