package it.polimi.ingsw.model.leadercard;


import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This class represents the special ability of leader cards of additional production.
 *
 * @author Stefano Fossati, davide grazioli
 */

public class AdditionalProduction implements SpecialAbility{
    private String effect;
    private int victoryPoints;
    private String cardRequired1;
    private int quantityRequired1;
    private int levelRequired1;
    private Resource materialAdditionalProduction;

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
        ArrayList<String> requirementsAdditionalProduction = new ArrayList<>();
        for( int i=0; i<quantityRequired1; i++){
            requirementsAdditionalProduction.add(cardRequired1);
        }
        return requirementsAdditionalProduction;
    }

    @Override
    public Resource getMaterialType() {
        return materialAdditionalProduction;
    }

    /**
     * getter of the color of the required card to activate the leader
     * @return the color of the required card
     */
    public String getCardRequired() {
        return cardRequired1;
    }

    /**
     * getter of the quantity of the required card
     * @return the quantity of the required card
     */
    public int getQuantityRequired(){
        return quantityRequired1;
    }

    /**
     * getter of the level of the required card
     * @return the level of the required card
     */
    public int getLevelRequired() {
        return levelRequired1;
    }
}
