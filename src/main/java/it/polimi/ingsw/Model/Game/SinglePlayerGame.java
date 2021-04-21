package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Lorenzo.LorenzoIlMagnifico;

import java.util.ArrayList;

public class SinglePlayerGame extends Game{
    Player player;
    LorenzoIlMagnifico lorenzoIlMagnifico;

    public SinglePlayerGame(Player player) {
        super(1);
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
    }
}
