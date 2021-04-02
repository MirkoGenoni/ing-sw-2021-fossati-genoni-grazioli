package it.polimi.ingsw.Model.LeaderCard;

import java.util.Map;

/**
 * This class represents the special ability of leader cards of additional production
 *
 * @author Stefano Fossati
 */

public class AdditionalProduction implements SpecialAbility{
    private String effect;
    private int victoryPoints;
    private String cardRequired;
    private int quantityRequired;
    private int levelRequired;
    private int materialAdditionalProduction;



    //TODO methods

    @Override
    public String getEffect() {
        return effect;
    }

    @Override
    public int getVictoryPoint() {
        return victoryPoints;
    }
}
