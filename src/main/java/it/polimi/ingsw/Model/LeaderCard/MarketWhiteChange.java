package it.polimi.ingsw.Model.LeaderCard;


import java.util.ArrayList;

/**
 * This class represents the special ability of leader cards of change a white marble of the market with another type of marble.
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
        ArrayList<String> requirementsMarketWhiteChange = new ArrayList<>();
        for( int i=0; i<quantityRequired1; i++){
            requirementsMarketWhiteChange.add(cardRequired1);
        }
        for( int i=0; i<quantityRequired2; i++){
            requirementsMarketWhiteChange.add(cardRequired2);
        }
        return requirementsMarketWhiteChange;
    }


    @Override
    public String getMaterialType() {
        return materialWhiteChangeTo;
    }


}
