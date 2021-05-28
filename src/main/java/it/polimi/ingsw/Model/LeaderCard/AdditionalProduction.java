package it.polimi.ingsw.Model.LeaderCard;


import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

/**
 * This class represents the special ability of leader cards of additional production.
 *
 * @author Stefano Fossati
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


    public String getCardRequired() {
        return cardRequired1;
    }

    public int getQuantityRequired(){
        return quantityRequired1;
    }

    public int getLevelRequired() {
        return levelRequired1;
    }
}
