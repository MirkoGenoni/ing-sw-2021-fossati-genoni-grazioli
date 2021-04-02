package it.polimi.ingsw.Model.LeaderCard;


/**
 * This class represents the special ability of leader cards of decrement the purchase cost of the development cards
 *
 * @author Stefano Fossati
 */

public class CostLess implements SpecialAbility {
    private String effect;
    private int victoryPoints;
    private String cardRequired1;
    private int quantityRequired1;
    private String cardRequired2;
    private int quantityRequired2;
    private String materialCostLess;

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
