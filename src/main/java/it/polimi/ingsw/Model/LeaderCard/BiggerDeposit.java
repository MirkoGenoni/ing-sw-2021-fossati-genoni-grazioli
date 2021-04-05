package it.polimi.ingsw.Model.LeaderCard;

import java.util.ArrayList;

/**
 * This class represents the special ability of leader cards of increment capacity of the deposit.
 *
 * @author Stefano Fossati
 */

public class BiggerDeposit implements SpecialAbility{
    private String effect;
    private int victoryPoints;
    private String materialRequired1;
    private int quantityRequired1;
    private String materialBiggerDeposit;

    @Override
    public String getEffect() {
        return effect;
    }

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    @Override
    public ArrayList<String> getRequirements() {
        ArrayList<String> requirementsBiggerDeposit = new ArrayList<>();
        requirementsBiggerDeposit.add(materialRequired1);
        return requirementsBiggerDeposit;
    }

    @Override
    public String getMaterialType() {
        return materialBiggerDeposit;
    }
}
