package it.polimi.ingsw.Model.LeaderCard;


import it.polimi.ingsw.Model.Resource.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the special ability of leader cards of increment capacity of the deposit
 *
 * @author Stefano Fossati
 */

public class BiggerDeposit implements SpecialAbility{
    private String effect;
    private int victoryPoints;
    private String materialRequired1;
    private int quantityRequired1;
    private String materialBiggerDeposit;


    //TODO methods

    public Resource getMaterialBiggerDeposit() {
        return Resource.valueOf(materialBiggerDeposit);
    }

    @Override
    public String getEffect() {
        return effect;
    }

    @Override
    public int getVictoryPoint() {
        return victoryPoints;
    }
}
