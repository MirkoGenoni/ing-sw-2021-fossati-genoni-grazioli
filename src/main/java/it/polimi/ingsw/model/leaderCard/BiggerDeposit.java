package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.resource.Resource;

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
    private Resource materialBiggerDeposit;

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
    public Resource getMaterialType() {
        return materialBiggerDeposit;
    }

    public String getMaterialRequired() {
        return materialRequired1;
    }

    public int getQuantityRequired() {
        return quantityRequired1;
    }
}
