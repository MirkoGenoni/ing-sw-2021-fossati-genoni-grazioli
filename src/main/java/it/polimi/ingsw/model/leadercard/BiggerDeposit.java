package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This class represents the special ability of leader cards of increment capacity of the deposit.
 *
 * @author Stefano Fossati, davide grazioli
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

    /**
     * getter of the resource type required to activate the leader
     * @return the resource type required to activate the leader
     */
    public String getMaterialRequired() {
        return materialRequired1;
    }

    /**
     * getter of the quantity of the resource type to activate the leader
     * @return the quantity of the resource type to activate the leader
     */
    public int getQuantityRequired() {
        return quantityRequired1;
    }
}
