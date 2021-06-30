package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This class represents the special ability of leader cards of decrement the purchase cost of the development cards
 *
 * @author Stefano Fossati, davide grazioli
 */

public class CostLess implements SpecialAbility {
    private String effect;
    private int victoryPoints;
    private String cardRequired1;
    private int quantityRequired1;
    private String cardRequired2;
    private int quantityRequired2;
    private Resource materialCostLess;


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
        ArrayList<String> requirementsCostLess = new ArrayList<>();
        for( int i=0; i<quantityRequired1; i++){
            requirementsCostLess.add(cardRequired1);
        }
        for( int i=0; i<quantityRequired2; i++){
            requirementsCostLess.add(cardRequired2);
        }
        return requirementsCostLess;
    }

    @Override
    public Resource getMaterialType() {
        return materialCostLess;
    }

    /**
     * getter of the first color of the required cards to activate the leader
     * @return the first color of the required cards to activate the leader
     */
    public String getCardRequired1() {
        return cardRequired1;
    }

    /**
     * getter of the quantity of the first required card
     * @return the quantity of the first required card
     */
    public int getQuantityRequired1() {
        return quantityRequired1;
    }

    /**
     * getter of the second color of the required cards to activate the leader
     * @return the color of the required cards to activate the second leader
     */
    public String getCardRequired2() {
        return cardRequired2;
    }

    /**
     * getter of the quantity of the second required card
     * @return the quantity of the second required card
     */
    public int getQuantityRequired2() {
        return quantityRequired2;
    }

}
