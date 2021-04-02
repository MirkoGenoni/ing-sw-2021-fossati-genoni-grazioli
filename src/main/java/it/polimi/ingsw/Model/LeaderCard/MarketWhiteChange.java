package it.polimi.ingsw.Model.LeaderCard;

import it.polimi.ingsw.Model.Resource.Resource;

/**
 * This class represents the special ability of leader cards of change a white marble of the market with another type of marble
 *
 * @author Stefano Fossati
 */

public class MarketWhiteChange implements SpecialAbility{
    private String effect;
    private int victoryPoints;
    private String cardRequired1;
    private int quantityRequired1;
    private String cardRequired2;
    private int quantityRequired2;
    private String materialWhiteChangeTo;


    //TODO methods

    @Override
    public String getEffect() {
        return effect;
    }

    @Override
    public int getVictoryPoint() {
        return victoryPoints;
    }

    public Resource getMaterialWhiteChangeTo(){
        return Resource.valueOf(materialWhiteChangeTo);
    }
}
